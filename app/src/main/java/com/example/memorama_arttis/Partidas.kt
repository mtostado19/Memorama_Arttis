package com.example.memorama_arttis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PartysAdapter(val tostadoCum: ArrayList<PartyClass>) :
    RecyclerView.Adapter<PartysAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var txtPartidas: TextView
        private var txtdificultad: TextView
        private var iduser : TextView

        private lateinit var cumpleTost: PartyClass

        init {
            txtPartidas = view.findViewById(R.id.partida)
            txtdificultad = view.findViewById(R.id.id_dificultad)
            iduser = view.findViewById(R.id.id_user)

        }
        fun bind(cumpleTost : PartyClass) {
            txtPartidas.text = "Activo"
            txtdificultad.text = "Dificultad: ${cumpleTost.difficult.toString()}"
            iduser.text = cumpleTost.host
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_recyclerview, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tostadoCum[position])
    }

    override fun getItemCount() = tostadoCum.size
}

class Partidas : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partidas)

        rv = findViewById(R.id.rvPartidas)
        rv.layoutManager = LinearLayoutManager(this)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("party")


        val partysss = arrayListOf<PartyClass>()

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                partysss.clear()
                dataSnapshot.getChildren().forEach {
                    println("----------------------------- jeje funciono")
                    val product = it.getValue(PartyClass::class.java)
                    partysss.add(product!!)
                }

                println("xdxdxdxdxdxdxdxdxdxdxdxdxdxdxdxdxdxdxd")
                println(partysss)

                rv.adapter = PartysAdapter(partysss)



            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
               println("xd error :3")
            }
        }
        myRef.addValueEventListener(postListener)


        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

    }
}