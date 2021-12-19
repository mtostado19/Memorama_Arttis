package com.example.memorama_arttis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class PartysAdapter() :
    RecyclerView.Adapter<PartysAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var txtPartidas: TextView
        private var txtdificultad: TextView
        private var iduser : TextView

        init {
            txtPartidas = view.findViewById(R.id.partida)
            txtdificultad = view.findViewById(R.id.id_dificultad)
            iduser = view.findViewById(R.id.id_user)

        }
        fun bind() {
            txtPartidas.text = "si"
            txtdificultad.text = "si"
            iduser.text = "si"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_recyclerview, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 2
}

class Partidas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partidas)
    }
}