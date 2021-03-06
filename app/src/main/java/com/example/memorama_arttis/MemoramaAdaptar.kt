package com.example.memorama_arttis

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class MemoramaAdaptar(var context: Context,var arrayList: ArrayList<MemoramaData>) : BaseAdapter() {

    override fun getItem(p0: Int): Any {
        return arrayList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view: View = View.inflate(context, R.layout.grid_items, null)

        var icons: ImageView = view.findViewById(R.id.imgGrid)

        var memoramaItems: MemoramaData = arrayList.get(p0)
        icons.setImageResource(memoramaItems.currentImage!!) //aqui le pone la imagen


        return  view
    }

    fun clear() {
        arrayList = ArrayList<MemoramaData>()
    }

    fun addAll(newList: ArrayList<MemoramaData>) {
        arrayList = newList

        arrayList.forEach{
            var view: View = View.inflate(context, R.layout.grid_items, null)
            var icons: ImageView = view.findViewById(R.id.imgGrid)
            icons.setImageResource(it.currentImage!!)
        }

    }

    fun getImageIndex(id: Int): Int {
        return arrayList.indexOf(arrayList.find { it.unique == id })
    }

    override fun getCount(): Int {
        return arrayList.size
    }
}