package com.el3asas.utils.recyclerView

import android.annotation.SuppressLint
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.viewbinding.ViewBinding
import com.el3asas.utils.R
import com.el3asas.utils.binding.RecyclerAdapterBinding
import com.el3asas.utils.databinding.ItemRadioButtonForRecyclerView2Binding
import timber.log.Timber

class RadiosAdapterNormal<T>(
    private val funOnSelectedItem: ((View, Int, RadioItemDataModel<T>) -> Unit)? = null,
    private val backgroundRes: Int? = null,
    private val textColor: Int = R.color.radio_item_text_color,
    private val inVisibleBtn: Boolean = true,
    private val textGravity: Int = Gravity.CENTER_VERTICAL,
    private val isMustSelected: Boolean = false,
    override val bindingInflater: (LayoutInflater) -> ViewBinding = ItemRadioButtonForRecyclerView2Binding::inflate
) : RecyclerAdapterBinding<ItemRadioButtonForRecyclerView2Binding>() {

    private val data: ArrayList<RadioItemDataModel<T>> = arrayListOf()
    var lastSelectedId = -1

    override fun getItemCount() = data.size

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
            root.setOnClickListener { v ->
                handleSelectNewItem(position)
                funOnSelectedItem?.let { it1 -> it1(v, lastSelectedId, item) }
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
                if (isMustSelected.not()) {
                    data.firstOrNull { it.id == lastSelectedId }?.isChecked = false
                    lastSelectedId = -1
                    notifyItemChanged(position)
                }
            }

            else -> {
                val lastSelectedItem = data.firstOrNull { it.id == lastSelectedId }
                lastSelectedItem?.apply {
                    isChecked = false
                }
                selectedItem.isChecked = true
                lastSelectedId = selectedItem.id
                notifyItemChanged(position)
                if (lastSelectedItem != null)
                    notifyItemChanged(data.indexOf(lastSelectedItem))
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<RadioItemDataModel<T>>?) {
        data.clear()
        list?.let {
            if (lastSelectedId != -1) {
                Timber.d(" lastSelectedId != -1")
                data.addAll(list.map {
                    it.isChecked = it.id == lastSelectedId
                    it
                })
            } else {
                lastSelectedId = list.firstOrNull { it.isChecked }?.id ?: -1
                data.addAll(list)
            }
            notifyDataSetChanged()
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun clearSelectedItem() {
        if (lastSelectedId != -1) {
            data.firstOrNull { it.id == lastSelectedId }?.isChecked = false
            lastSelectedId = -1
        }
        notifyDataSetChanged()
    }

    fun getSelectedItem() = data.firstOrNull { it.id == lastSelectedId }

    @SuppressLint("NotifyDataSetChanged")
    fun clearDate() {
        data.clear()
        notifyDataSetChanged()
    }

}