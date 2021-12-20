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



        setContentView(R.layout.activity_tarjetas)

        if (intent != null) {
            println("REEEEEEEEEE")
            println(intent.getStringExtra("difficult"))
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
        arrayList.add(MemoramaData(R.drawable.common_full_open_on_phone))
        arrayList.add(MemoramaData(R.drawable.common_full_open_on_phone))
        arrayList.add(MemoramaData(R.drawable.common_full_open_on_phone))
        arrayList.add(MemoramaData(R.drawable.common_full_open_on_phone))
        arrayList.add(MemoramaData(R.drawable.common_full_open_on_phone))
        arrayList.add(MemoramaData(R.drawable.common_full_open_on_phone))
        arrayList.add(MemoramaData(R.drawable.common_full_open_on_phone))
        arrayList.add(MemoramaData(R.drawable.common_full_open_on_phone))
        arrayList.add(MemoramaData(R.drawable.common_full_open_on_phone))
        arrayList.add(MemoramaData(R.drawable.common_full_open_on_phone))
        arrayList.add(MemoramaData(R.drawable.common_full_open_on_phone))
        arrayList.add(MemoramaData(R.drawable.common_full_open_on_phone))
        arrayList.add(MemoramaData(R.drawable.common_full_open_on_phone))
        arrayList.add(MemoramaData(R.drawable.common_full_open_on_phone))
        arrayList.add(MemoramaData(R.drawable.common_full_open_on_phone))
        arrayList.add(MemoramaData(R.drawable.common_full_open_on_phone))
        return  arrayList
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var memo: MemoramaData = arrayList!!.get(p2)
        println("xd")
    }

}