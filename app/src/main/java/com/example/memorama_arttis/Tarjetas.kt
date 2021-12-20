package com.example.memorama_arttis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase

class Tarjetas : AppCompatActivity(), AdapterView.OnItemClickListener {

    private var gridView: GridView ?= null
    private var arrayList: ArrayList<MemoramaData> ?= null
    private  var memoramaAdaptar: MemoramaAdaptar ?= null
    //private lateinit var textxd: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent != null) {
            println("REEEEEEEEEE")
            println(intent.getStringExtra("difficult"))
            if (intent.getStringExtra("difficult") == "1"){
                setContentView(R.layout.activity_tarjetas)
            }else{
                setContentView(R.layout.activity_tarjetas2)
            }
        }

        //textxd.text = "xd"

        gridView = findViewById(R.id.gridView)
        arrayList = ArrayList()
        arrayList = setDataList()

        memoramaAdaptar = MemoramaAdaptar(applicationContext,arrayList!!)

        gridView?.adapter = memoramaAdaptar

        gridView?.onItemClickListener = this

    }

    private fun setDataList() : ArrayList<MemoramaData>{
        var arrayList: ArrayList<MemoramaData> = ArrayList()
        arrayList.add(MemoramaData(R.drawable.card,1,R.drawable.common_google_signin_btn_icon_dark))
        arrayList.add(MemoramaData(R.drawable.card,1,R.drawable.common_google_signin_btn_icon_dark))
        arrayList.add(MemoramaData(R.drawable.card,2,R.drawable.common_google_signin_btn_icon_dark))
        arrayList.add(MemoramaData(R.drawable.card,2,R.drawable.common_google_signin_btn_icon_dark))
        arrayList.add(MemoramaData(R.drawable.card,3,R.drawable.common_google_signin_btn_icon_dark))
        arrayList.add(MemoramaData(R.drawable.card, 3,R.drawable.common_google_signin_btn_icon_dark))
        arrayList.add(MemoramaData(R.drawable.card,4,R.drawable.common_google_signin_btn_icon_dark))
        arrayList.add(MemoramaData(R.drawable.card, 4,R.drawable.common_google_signin_btn_icon_dark))
        arrayList.add(MemoramaData(R.drawable.card,5,R.drawable.common_google_signin_btn_icon_dark))
        arrayList.add(MemoramaData(R.drawable.card,5,R.drawable.common_google_signin_btn_icon_dark))
        arrayList.add(MemoramaData(R.drawable.card,6,R.drawable.common_google_signin_btn_icon_dark))
        arrayList.add(MemoramaData(R.drawable.card,6,R.drawable.common_google_signin_btn_icon_dark))
        arrayList.add(MemoramaData(R.drawable.card,7,R.drawable.common_google_signin_btn_icon_dark))
        arrayList.add(MemoramaData(R.drawable.card,7,R.drawable.common_google_signin_btn_icon_dark))
        arrayList.add(MemoramaData(R.drawable.card,8,R.drawable.common_google_signin_btn_icon_dark))
        arrayList.add(MemoramaData(R.drawable.card,8,R.drawable.common_google_signin_btn_icon_dark))
        return  arrayList
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var memo: MemoramaData = arrayList!!.get(p2)
        println(memo.IdPar) // esta es la chingadera que sabe cuales son los pares cuando le das click
    }

}