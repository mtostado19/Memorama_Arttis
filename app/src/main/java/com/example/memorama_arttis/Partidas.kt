package com.example.memorama_arttis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PartysAdapter(val tostadoCum: ArrayList<PartyClass>) :
    RecyclerView.Adapter<PartysAdapter.ViewHolder>() {
    private lateinit var mListener: onItemClickListener
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    class ViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        private var txtPartidas: TextView
        private var txtdificultad: TextView
        private var iduser : TextView

        private lateinit var cumpleTost: PartyClass

        init {
            txtPartidas = view.findViewById(R.id.partida)
            txtdificultad = view.findViewById(R.id.id_dificultad)
            iduser = view.findViewById(R.id.id_user)
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

        }
        fun bind(cumpleTost : PartyClass) {
            txtPartidas.text = "Activo"
            txtdificultad.text = "Dificultad: ${cumpleTost.difficult.toString()}"
            iduser.text = cumpleTost.host
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_recyclerview, parent, false), mListener)


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tostadoCum[position])
    }

    override fun getItemCount() = tostadoCum.size
}
private lateinit var database: FirebaseDatabase
class Partidas : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partidas)

        rv = findViewById(R.id.rvPartidas)
        rv.layoutManager = LinearLayoutManager(this)

        database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("party")


        val partysss = arrayListOf<PartyClass>()

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                partysss.clear()
                dataSnapshot.getChildren().forEach {
                    val product = it.getValue(PartyClass::class.java)
                    partysss.add(product!!)
                }
                val adapter = PartysAdapter(partysss)
                rv.adapter = adapter

                adapter.setOnItemClickListener((object : PartysAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        val host = partysss[position].host
                        createGame(host, partysss[position].difficult)
                        MyUndoListener(host)
                        val intent = Intent(this@Partidas, Tarjetas::class.java)
                        intent.putExtra("HOST", host)
                        startActivity(intent)
                    }

                }))




            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
               println("xd error :3")
            }
        }
        myRef.addValueEventListener(postListener)


        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

    }

    fun MyUndoListener(hostName: String?) {
        val currentGame = database.getReference("party")
        currentGame.get().addOnSuccessListener {
            val value: HashMap<String, String> = it.value as HashMap<String, String>
            for (element in value) {
                val host = value[element.key].toString().split("host=")[1].split(",")[0]
                if (host == hostName) {
                    currentGame.child(element.key).removeValue()
                    break
                }
            }
        }

    }

    fun createGame(hostName: String?, difficult: Int?) {
        val newGame = database.getReference("game")

        newGame.push().setValue(
            Juego(hostName, "guest", difficult)
        )
    }
}