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
import java.lang.Exception

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
                        intent.putExtra("difficult", partysss[position].difficult.toString())
                        intent.putExtra("HOST", partysss[position].host)
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

        try {
            rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        } catch (error: Exception){
            println("ERROR")
        }

    }

    fun MyUndoListener(hostName: String?) {
        val currentGame = database.getReference("party")
        currentGame.get().addOnSuccessListener {
            val value: HashMap<String, String>? = it.value as HashMap<String, String>?
            if (value != null) {
                for (element in value) {
                    val host = value[element.key].toString().split("host=")[1].split(",")[0]
                    if (host == hostName) {
                        currentGame.child(element.key).removeValue()
                        break
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

    fun createGame(hostName: String?, difficult: Int?) {
        val newGame = database.getReference("game")

        newGame.push().setValue(
            Juego(hostName, "guest", difficult, gameData = setDataList())
        )
    }
}