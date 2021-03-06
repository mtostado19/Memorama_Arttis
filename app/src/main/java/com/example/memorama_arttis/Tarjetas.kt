package com.example.memorama_arttis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.FieldPosition

class Tarjetas : AppCompatActivity(), AdapterView.OnItemClickListener {

    private var gridView: GridView ?= null
    private var arrayList: ArrayList<MemoramaData> ?= null
    private var finalDataArrayMain: ArrayList<MemoramaData> ?= null
    private lateinit var txtUser: TextView
    private var memoramaAdaptar: MemoramaAdaptar ?= null
    private lateinit var database: FirebaseDatabase
    private var firstSelectedItem = -1
    private var secondSelectedItem = -1
    private lateinit var currentGame: DatabaseReference
    private var currentKey: String? = ""
    private lateinit var currentGameData: Juego
    private lateinit var gson: Gson
    private var firstTime: Boolean = true
    private var finished: Boolean = true
    private var currentUser: String? = null
    //private lateinit var textxd: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        database = FirebaseDatabase.getInstance()
        var host:String? = ""
        if (intent != null) {
            host = intent.getStringExtra("HOST")
            currentUser = intent.getStringExtra("CURRENT")
            if (intent.getStringExtra("difficult") == "1"){
                setContentView(R.layout.activity_tarjetas)
            }else{
                setContentView(R.layout.activity_tarjetas2)
            }
        }
        txtUser = findViewById(R.id.currentPlayerTXT)
        txtUser.text = "guest"


        val currentGame = database.getReference("game")
        var currentKey: String? = ""
        currentGame.get().addOnSuccessListener {
            val value: HashMap<String, String>? = it.value as HashMap<String, String>?
            if (value != null) {
                for (element in value) {
                    val hostFound = value[element.key].toString().split("host=")[1].split(",")[0]
                    if (hostFound == host) {
                        runCode(element.key)
                    }
                }
            }
        }

    }


    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (currentUser == currentGameData.currentPlayer) {
            println("EL JUGADOR CORRECTO ESTA JUGANDO")
            var memo: MemoramaData = arrayList!!.get(p2)
            CoroutineScope(IO).launch {
                async {

                    for(element in arrayList!!) {
                        if(!element.found) {
                            finished = false
                            break;
                        }
                    }
                    if (!memo.found && firstSelectedItem != p2) {
                        if (!finished) {
                            val valueIndex = memoramaAdaptar!!.getImageIndex(memo.unique)
                            var value = arrayList!![valueIndex]
                            value.currentImage = value.trueIcon

                            if (firstSelectedItem == -1) {
                                firstSelectedItem = valueIndex
                            } else if (secondSelectedItem == -1) {
                                secondSelectedItem = valueIndex
                            }

                            if (firstSelectedItem != -1 && secondSelectedItem != -1){
                                arrayList!![firstSelectedItem].currentImage = arrayList!![firstSelectedItem].trueIcon
                                arrayList!![secondSelectedItem].currentImage = arrayList!![secondSelectedItem].trueIcon

                                if (arrayList!![firstSelectedItem].idPar == arrayList!![secondSelectedItem].idPar) {
                                    arrayList!![firstSelectedItem].found = true
                                    arrayList!![secondSelectedItem].found = true
                                    firstSelectedItem = -1
                                    secondSelectedItem = -1
                                    if (currentGameData.currentPlayer == currentGameData.host) {
                                        currentGameData.currentPlayer = currentGameData.guest
                                        currentGameData.hostScore += 1

                                    } else {
                                        currentGameData.currentPlayer = currentGameData.host
                                        currentGameData.guestScore += 1
                                    }

                                } else {
                                    if (currentGameData.currentPlayer == currentGameData.host) {
                                        currentGameData.currentPlayer = currentGameData.guest

                                    } else {
                                        currentGameData.currentPlayer = currentGameData.host
                                    }
                                    arrayList!![firstSelectedItem].currentImage = arrayList!![firstSelectedItem].icons
                                    arrayList!![secondSelectedItem].currentImage = arrayList!![secondSelectedItem].icons
                                    firstSelectedItem = -1
                                    secondSelectedItem = -1
                                }

                            }

                            CoroutineScope(IO).launch {
                                async {
                                    currentGame.setValue(Juego(currentGameData.host,
                                        currentGameData.guest,
                                        currentGameData.difficulty,
                                        currentGameData.currentPlayer,
                                        currentGameData.hostScore,
                                        currentGameData.guestScore,
                                        arrayList!!
                                    ))
                                }.await()
                            }
                        }

                    } else if (finished) {
                        println("YA TERMINO EL JUEJO")
                    }
                }.await()
            }
        }
    }


    fun runCode(key: String?) {

        currentKey = key
        currentGame = database.getReference("game").child(key.toString())

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.value.toString()
                gson = Gson()
                CoroutineScope(IO).launch {
                    finalDataArrayMain = async {
                        var arraySize =  if (intent.getStringExtra("difficult") == "1") 16 else 36
                        getCurrentValue(value, arraySize)
                    }.await()
                    println("REEEEEEEEEEEEEE")
                    println(gson.toJson(finalDataArrayMain))


                }
                currentGameData = gson.fromJson(value, Juego::class.java)
                txtUser.text = currentGameData.currentPlayer
                currentGameData.gameData = finalDataArrayMain

                arrayList = currentGameData.gameData
                gridView = findViewById(R.id.gridView)

                memoramaAdaptar = MemoramaAdaptar(this@Tarjetas,arrayList!!)
                gridView?.adapter = memoramaAdaptar
                gridView?.onItemClickListener = this@Tarjetas

            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                println("xd error :3")
            }
        }
        CoroutineScope(IO).launch {
            currentGame.addValueEventListener(postListener)
        }






    }

    suspend fun getCurrentValue(value: String, size: Int): ArrayList<MemoramaData>? {
        if (value.contains("gameData=") && size != -1) {
            var text = "${value.split("gameData=")[1].split("}]")[0]}}]".split("},")

            var data = ArrayList<String>()
            text.forEach{
                if(text.indexOf(it) == size - 1){
                    data.add(it.split("]")[0])
                } else if(text.indexOf(it) == 0) {
                    data.add("${it.split("[")[1]}}")
                } else {
                    data.add("${it}}")
                }
            }

            var finalDataArray = ArrayList<MemoramaData>()


            data.forEach{
                val jsonData = gson.fromJson(it, MemoramaData::class.java)
                finalDataArray.add(MemoramaData(
                    jsonData.icons,
                    jsonData.idPar,
                    jsonData.trueIcon,
                    jsonData.unique,
                    jsonData.currentImage,
                    jsonData.found
                ))
            }

            return finalDataArray
        }
        return null
    }


}