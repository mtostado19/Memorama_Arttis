package com.example.memorama_arttis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_crear = findViewById(R.id.btn_crearPartidas)
        btn_buscar = findViewById(R.id.btn_buscarPartidas)
        textInput = findViewById(R.id.editUsuario)
        sliderDificultad = findViewById(R.id.sliderDificultad)

        // Instancia base de datos
        val database = FirebaseDatabase.getInstance()
        btn_crear.setOnClickListener {
            val myRef = database.getReference("party")
            val newParty = PartyClass(textInput.text.toString(), sliderDificultad.value.toInt());
            myRef.push().setValue(newParty)
            val mySnackbar = Snackbar.make(it,
                "Esperando...", 30000)
            mySnackbar.setAction("Cancelar") { MyUndoListener() }
            mySnackbar.show()
        }
        btn_buscar.setOnClickListener {
            val intento1 = Intent(this, Partidas::class.java)
            startActivity(intento1)
        }
    }
    fun MyUndoListener() {

        // obtener el jodido key del jodido objeto y eliminarlo

    }
}