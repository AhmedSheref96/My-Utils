package com.el3asas.sample

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomAdapter(
    context: Context,
    private val resource: Int,
    private val list: MutableList<Item>
) : ArrayAdapter<Item>(context, resource, list) {
    private val TAG = "----------------------"

    @SuppressLint("ViewHolder", "ResourceAsColor")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        Log.d("--", "getView: ------------------------- hhhhhhhh ")
        val v = LayoutInflater.from(parent.context).inflate(R.layout.menu, parent, false)
        val item = list[position]
        v.findViewById<TextView>(R.id.title).text = item.title
        v.findViewById<ImageView>(R.id.img).setImageResource(item.icon)
        if (item.isChecked) {
            v.setBackgroundColor(R.color.teal_200)
        } else {
            v.setBackgroundColor(R.color.teal_700)
        }
        return v
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        Log.d(TAG, "getDropDownView: -------------------")
        return super.getDropDownView(position, convertView, parent)
    }

    override fun setAutofillOptions(vararg options: CharSequence?) {
        super.setAutofillOptions(*options)
        Log.d(TAG, "setAutofillOptions: ---------------- $options")
    }


    override fun getAutofillOptions(): Array<CharSequence>? {
        val d = super.getAutofillOptions()
        Log.d(TAG, "getAutofillOptions: -------------------- $d")
        return d
    }

    override fun setDropDownViewResource(resource: Int) {
        Log.d(TAG, "setDropDownViewResource: ---------------$resource")
        super.setDropDownViewResource(resource)
    }

    override fun getItem(position: Int): Item? {
        Log.d(TAG, "getItem: ------------------ $position")
        return super.getItem(position)
    }



}