package com.example.memorama_arttis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var btn_crear : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_crear = findViewById(R.id.btn_crearPartidas)

        // Instancia base de datos
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        btn_crear.setOnClickListener {
            myRef.setValue("Hello, World!");
            val mySnackbar = Snackbar.make(it,
                "hola", 30000)
            mySnackbar.setAction("regresar") { MyUndoListener() }
            mySnackbar.show()
        }
    }
    fun MyUndoListener() {
        print("f")
    }
}