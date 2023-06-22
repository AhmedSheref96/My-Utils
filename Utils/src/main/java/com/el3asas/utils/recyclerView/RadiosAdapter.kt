package com.el3asas.utils.recyclerView

import android.annotation.SuppressLint
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.res.ResourcesCompat
import androidx.viewbinding.ViewBinding
import com.el3asas.utils.R
import com.el3asas.utils.binding.RecyclerAdapterBinding
import com.el3asas.utils.databinding.ItemRadioButtonForRecyclerView2Binding

class RadiosAdapterFilterable<T>(
    private val funOnSelectedItem: ((Int, RadioItemDataModel<T>) -> Unit)? = null,
    private val backgroundRes: Int? = null,
    private val textColor: Int = R.color.radio_item_text_color,
    private val inVisibleBtn: Boolean = true,
    private val textGravity: Int = Gravity.CENTER_VERTICAL,
    private val isMustSelected: Boolean = false,
    override val bindingInflater: (LayoutInflater) -> ViewBinding = ItemRadioButtonForRecyclerView2Binding::inflate
) : RecyclerAdapterBinding<ItemRadioButtonForRecyclerView2Binding>(), Filterable {

    lateinit var data: ArrayList<RadioItemDataModel<T>>
    lateinit var fixedData: ArrayList<RadioItemDataModel<T>>
    var lastSelectedId = -1

    override fun getItemCount() = if (this::data.isInitialized) data.size else 0

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: MainViewHolder<ItemRadioButtonForRecyclerView2Binding>, position: Int
    ) {
        val item = data[position]
        holder.binding.apply {
            itemData = item

            if (backgroundRes != null) background = ResourcesCompat.getDrawable(
                root.resources, backgroundRes, null
            )

            if (item.img != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    radio.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        ResourcesCompat.getDrawable(
                            root.resources, item.img, null
                        ), null, null, null
                    )
                } else {
                    radio.setCompoundDrawablesWithIntrinsicBounds(
                        ResourcesCompat.getDrawable(
                            root.resources, item.img, null
                        ), null, null, null
                    )
                }
            }

            radio.setTextColor(ResourcesCompat.getColorStateList(root.resources, textColor, null))
            radio.gravity = textGravity
            if (inVisibleBtn) radio.buttonDrawable = null

            radio.isChecked = item.isChecked
            root.setOnClickListener {
                handleSelectNewItem(position)
                funOnSelectedItem?.let { it1 -> it1(lastSelectedId, item) }
            }
        }
    }

    private fun handleSelectNewItem(position: Int) {
        val selectedItem = data[position]
        when (selectedItem.id) {
            lastSelectedId -> {
                if (isMustSelected.not()) {
                    selectedItem.isChecked = selectedItem.isChecked.not()
                    lastSelectedId = -1
                    notifyItemChanged(position)
                }
            }

            -1 -> {
                data.firstOrNull { it.id == lastSelectedId }?.isChecked = false
                lastSelectedId = -1
                notifyItemChanged(position)
            }

            else -> {
                val lastSelectedItem = data.firstOrNull { it.id == lastSelectedId }
                lastSelectedItem?.apply {
                    isChecked = false
                }
                selectedItem.isChecked = true
                lastSelectedId = selectedItem.id
                notifyItemChanged(position)
                if (lastSelectedItem != null) notifyItemChanged(data.indexOf(lastSelectedItem))
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<RadioItemDataModel<T>>?) {
        list?.let { it ->
            fixedData = ArrayList(it)
            if (lastSelectedId != -1) {
                data = fixedData.map {
                    if (it.id == lastSelectedId) it.isChecked = true
                    it
                } as ArrayList<RadioItemDataModel<T>>
            } else {
                data = fixedData
            }
        }
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charString = p0?.toString() ?: ""
                data = if (charString.isEmpty()) fixedData
                else {
                    val filteredList: ArrayList<RadioItemDataModel<T>> = ArrayList()
                    fixedData.filter {
                        it.name.contains(p0!!)
                    }.forEach { it.let { it1 -> filteredList.add(it1) } }
                    filteredList
                }
                return FilterResults().apply { values = data }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                data = if (p1?.values == null) {
                    ArrayList()
                } else {
                    p1.values as ArrayList<RadioItemDataModel<T>>
                }
                notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearSelectedItem() {
        if (this::data.isInitialized && this::fixedData.isInitialized) {
            if (lastSelectedId != -1) {
                fixedData.first { it.id == lastSelectedId }.isChecked = false
                data.first { it.id == lastSelectedId }.isChecked = false
                lastSelectedId = -1
            }
            data = fixedData
            notifyDataSetChanged()
        }
    }

    fun getSelectedItem() = data.firstOrNull { it.id == lastSelectedId }

    @SuppressLint("NotifyDataSetChanged")
    fun clearDate() {
        data = ArrayList()
        fixedData = data
        notifyDataSetChanged()
    }

}