package com.example.memorama_arttis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var btn_crear : Button
    private lateinit var btn_buscar : Button
    private lateinit var textInput : EditText
    private lateinit var sliderDificultad: com.google.android.material.slider.Slider
    private lateinit var database : FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_crear = findViewById(R.id.btn_crearPartidas)
        btn_buscar = findViewById(R.id.btn_buscarPartidas)
        textInput = findViewById(R.id.editUsuario)
        sliderDificultad = findViewById(R.id.sliderDificultad)

        // Instancia base de datos
        database = FirebaseDatabase.getInstance()
        btn_crear.setOnClickListener { it ->
            val view = it
            val myRef = database.getReference("party")
            var currentKey:String? = ""
            myRef.get().addOnSuccessListener {
                val value: HashMap<String, String>? = it.value as HashMap<String, String>?
                if (value != null) {
                    for (element in value) {
                        val host = value[element.key].toString().split("host=")[1].split(",")[0]
                        if (host == textInput.text.toString()) {
                            currentKey = element.key
                            println("------------- key ----------")
                            println(currentKey)
                            break
                        }
                    }
                }
                if (textInput.text.toString() == "") {
                    Toast.makeText(this, "Elija nombre de usuario", Toast.LENGTH_LONG).show()
                }
                else if (currentKey != "") {
                    Toast.makeText(this, "El nombre de usuario ya se encuentra en uso", Toast.LENGTH_LONG).show()
                } else {
                    textInput.isEnabled = false
                    btn_buscar.isEnabled = false
                    btn_crear.isEnabled = false
                    sliderDificultad.isEnabled = false

                    val newParty = PartyClass(textInput.text.toString(), sliderDificultad.value.toInt());
                    myRef.push().setValue(newParty)

                    val mySnackbar = Snackbar.make(view,
                        "Esperando...", Snackbar.LENGTH_INDEFINITE)
                    mySnackbar.setAction("Cancelar") { MyUndoListener() }
                    mySnackbar.show()
                }

            }




        }
        btn_buscar.setOnClickListener {
            val intento1 = Intent(this, Partidas::class.java)
            startActivity(intento1)
        }

        val myRef = database.getReference("game")
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                findHost(textInput.text.toString())

            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                println("xd error :3")
            }
        }
        myRef.addValueEventListener(postListener)
    }

    fun MyUndoListener() {
        val currentGame = database.getReference("party")
        currentGame.get().addOnSuccessListener {
            val value: HashMap<String, String>? = it.value as HashMap<String, String>?
            if (value != null) {
                for (element in value) {
                    val host = value[element.key].toString().split("host=")[1].split(",")[0]
                    if (host == textInput.text.toString()) {
                        try {
                            currentGame.child(element.key).removeValue()
                        } catch (e: Exception) {
                            println("Error")
                        }

                        break
                    }
                }
            }
        }
        textInput.isEnabled = true
        btn_buscar.isEnabled = true
        btn_crear.isEnabled = true
        sliderDificultad.isEnabled = true

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

    fun findHost(hostName: String?) {
        val currentGame = database.getReference("party")
        try {
            currentGame.get().addOnSuccessListener {
                var value = it.getValue()
                println(value)
                if (value.toString().contains("host=$hostName")) {
                    val intent = Intent(this, Tarjetas::class.java)
                    intent.putExtra("difficult", sliderDificultad.value.toInt().toString())
                    intent.putExtra("HOST", textInput.text.toString())
                    startActivity(intent)
                }
            }
        } catch (error: Exception) {
            println("ERROR")
        }

    }

    fun foundDeleted (hostName: String?) {
        println("Entrando a la función")
        val currentGame = database.getReference("party")
        var found = false

        currentGame.get().addOnSuccessListener {

            val value: HashMap<String, String>? = it.value as HashMap<String, String>?
            if (value != null) {
                CoroutineScope(IO).launch {
                    found = async {
                        getValueUser(value)
                    }.await()
                }
                if (found) {
                    println("User found inside: $found")
                    val intent = Intent(this, Tarjetas::class.java)
                    intent.putExtra("difficult", sliderDificultad.value.toInt().toString())
                    intent.putExtra("HOST", textInput.text.toString())
                    startActivity(intent)
                }
            }
        }
    }

    suspend fun getValueUser (value: HashMap<String, String>?): Boolean {

        if (value != null) {
            for(element in value) {
                val host = value[element.key].toString().split("host=")[1].split(",")[0]
                if (host == textInput.text.toString()) {
                    return true
                }
            }
            return false
        }
        return false
    }
}