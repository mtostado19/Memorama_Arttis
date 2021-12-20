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
import java.text.FieldPosition

class Tarjetas : AppCompatActivity(), AdapterView.OnItemClickListener {

    private var gridView: GridView ?= null
    private var arrayList: ArrayList<MemoramaData> ?= null
    private var finalDataArray: ArrayList<MemoramaData> ?= null
    private var memoramaAdaptar: MemoramaAdaptar ?= null
    private lateinit var database: FirebaseDatabase
    private var firstSelectedItem = -1
    private var secondSelectedItem = -1
    private lateinit var currentGame: DatabaseReference
    private var currentKey: String? = ""
    private lateinit var currentGameData: Juego
    private lateinit var gson: Gson
    //private lateinit var textxd: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance()
        var host:String? = ""
        if (intent != null) {
            host = intent.getStringExtra("HOST")
            if (intent.getStringExtra("difficult") == "1"){
                setContentView(R.layout.activity_tarjetas)
            }else{
                setContentView(R.layout.activity_tarjetas2)
            }
        }

        println(host)

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
    private fun setDataList() : ArrayList<MemoramaData>{
        var arrayList: ArrayList<MemoramaData> = ArrayList()
        arrayList.add(MemoramaData(R.drawable.card,1,R.drawable.common_google_signin_btn_icon_dark, 0, R.drawable.card, false))
        arrayList.add(MemoramaData(R.drawable.card,1,R.drawable.common_google_signin_btn_icon_dark, 1, R.drawable.card, false))
        arrayList.add(MemoramaData(R.drawable.card,2,R.drawable.common_google_signin_btn_icon_dark, 2, R.drawable.card, false))
        arrayList.add(MemoramaData(R.drawable.card,2,R.drawable.common_google_signin_btn_icon_dark, 3, R.drawable.card, false))
        arrayList.add(MemoramaData(R.drawable.card,3,R.drawable.common_google_signin_btn_icon_dark, 4, R.drawable.card, false))
        arrayList.add(MemoramaData(R.drawable.card, 3,R.drawable.common_google_signin_btn_icon_dark, 5, R.drawable.card, false))
        arrayList.add(MemoramaData(R.drawable.card,4,R.drawable.common_google_signin_btn_icon_dark, 6, R.drawable.card, false))
        arrayList.add(MemoramaData(R.drawable.card, 4,R.drawable.common_google_signin_btn_icon_dark, 7, R.drawable.card, false))
        arrayList.add(MemoramaData(R.drawable.card,5,R.drawable.common_google_signin_btn_icon_dark, 8, R.drawable.card, false))
        arrayList.add(MemoramaData(R.drawable.card,5,R.drawable.common_google_signin_btn_icon_dark, 9, R.drawable.card, false))
        arrayList.add(MemoramaData(R.drawable.card,6,R.drawable.common_google_signin_btn_icon_dark, 10, R.drawable.card, false))
        arrayList.add(MemoramaData(R.drawable.card,6,R.drawable.common_google_signin_btn_icon_dark, 11, R.drawable.card, false))
        arrayList.add(MemoramaData(R.drawable.card,7,R.drawable.common_google_signin_btn_icon_dark, 12, R.drawable.card, false))
        arrayList.add(MemoramaData(R.drawable.card,7,R.drawable.common_google_signin_btn_icon_dark, 13, R.drawable.card, false))
        arrayList.add(MemoramaData(R.drawable.card,8,R.drawable.common_google_signin_btn_icon_dark, 14, R.drawable.card, false))
        arrayList.add(MemoramaData(R.drawable.card,8,R.drawable.common_google_signin_btn_icon_dark, 15, R.drawable.card, false))
//        arrayList.shuffle()
        return  arrayList
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var memo: MemoramaData = arrayList!!.get(p2)
        if (!memo.found && firstSelectedItem != p2) {
            var value = arrayList!![memoramaAdaptar!!.getImageIndex(memo.unique)]
            value.currentImage = value.TrueIcon

            if (firstSelectedItem == -1) {
                firstSelectedItem = value.unique
            } else if (secondSelectedItem == -1) {
                secondSelectedItem = value.unique
            }

            if (firstSelectedItem != -1 && secondSelectedItem != -1){
                if (arrayList!![firstSelectedItem].IdPar == arrayList!![secondSelectedItem].IdPar) {
                    arrayList!![firstSelectedItem].found = true
                    arrayList!![secondSelectedItem].found = true
                    arrayList!![firstSelectedItem].icons = arrayList!![firstSelectedItem].TrueIcon
                    arrayList!![secondSelectedItem].icons = arrayList!![secondSelectedItem].TrueIcon
                    firstSelectedItem = -1
                    secondSelectedItem = -1

                    currentGame.setValue(Juego(currentGameData.host,
                        currentGameData.guest,
                        currentGameData.difficulty,
                        currentGameData.currentPlayer,
                        currentGameData.hostScore,
                        currentGameData.guestScore,
                        arrayList!!
                    ))
                } else {
                    arrayList!![firstSelectedItem].currentImage = arrayList!![firstSelectedItem].icons
                    arrayList!![secondSelectedItem].currentImage = arrayList!![secondSelectedItem].icons
                    firstSelectedItem = -1
                    secondSelectedItem = -1

                    currentGame.setValue(Juego(currentGameData.host,
                        currentGameData.guest,
                        currentGameData.difficulty,
                        currentGameData.currentPlayer,
                        currentGameData.hostScore,
                        currentGameData.guestScore,
                        arrayList!!
                    ))

                }

            }
            memoramaAdaptar = MemoramaAdaptar(applicationContext,arrayList!!)
            gridView?.adapter = memoramaAdaptar

            gridView?.onItemClickListener = this
        }

    }

    fun getGame(hostName: String?): String? {
        val currentGame = database.getReference("game")
        var currentKey: String? = ""
        currentGame.get().addOnSuccessListener {
            val value: HashMap<String, String>? = it.value as HashMap<String, String>?
            if (value != null) {
                for (element in value) {
                    val host = value[element.key].toString().split("host=")[1].split(",")[0]
                    if (host == hostName) {
                        currentKey = value[element.key]
                        return@addOnSuccessListener
                    }
                }
            }
        }
        return currentKey

    }

    fun runCode(key: String?) {

        currentKey = key
        currentGame = database.getReference("game").child(key.toString())

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.value.toString()
                println("Valueeeeee")
                println(value)
                gson = Gson()
                if (value.contains("gameData=")) {
                    var text = "${value.split("gameData=")[1].split("}]")[0]}}]".split("},")

                    var data = ArrayList<String>()
                    text.forEach{
                        if(text.indexOf(it) == arrayList!!.size - 1){
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
                            jsonData.IdPar,
                            jsonData.TrueIcon,
                            jsonData.unique,
                            jsonData.currentImage,
                            jsonData.found
                        ))
                    }
                }


                currentGameData = gson.fromJson(value, Juego::class.java)
                currentGameData.gameData = finalDataArray
//                val currentGameData = dataSnapshot.value.toString()
                println(currentGameData)
                gridView = findViewById(R.id.gridView)
                if (currentGameData.gameData == null) {
                    arrayList = ArrayList()
                    arrayList = setDataList()
                } else {
                    arrayList = currentGameData.gameData
                }

                println("Verifying adapterdata 1")
                if (finalDataArray != null) {
                    println(gson.toJson(finalDataArray!![0]))
                    println(gson.toJson(finalDataArray!![1]))
                }
                memoramaAdaptar = MemoramaAdaptar(applicationContext,arrayList!!)

                gridView?.adapter = memoramaAdaptar

                gridView?.onItemClickListener = this@Tarjetas

            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                println("xd error :3")
            }
        }
        currentGame.addValueEventListener(postListener)





    }


}