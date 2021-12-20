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
            var currentKey = ""
            myRef.get().addOnSuccessListener {
                val value: HashMap<String, String> = it.value as HashMap<String, String>
                for (element in value) {
                    val host = value[element.key].toString().split("host=")[1].split(",")[0]
                    if (host == textInput.text.toString()) {
                        currentKey = element.key
                        println("------------- key ----------")
                        println(currentKey)
                        break
                    }
                }

                if (currentKey != "") {
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
    }

    fun MyUndoListener() {
        val currentGame = database.getReference("party")
        currentGame.get().addOnSuccessListener {
            val value: HashMap<String, String> = it.value as HashMap<String, String>
            for (element in value) {
                val host = value[element.key].toString().split("host=")[1].split(",")[0]
                if (host == textInput.text.toString()) {
                    currentGame.child(element.key).removeValue()
                    break
                }
            }
        }
        textInput.isEnabled = true
        btn_buscar.isEnabled = true
        btn_crear.isEnabled = true
        sliderDificultad.isEnabled = true

    }
}