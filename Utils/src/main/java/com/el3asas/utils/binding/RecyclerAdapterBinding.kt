package com.el3asas.utils.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerAdapterBinding<R : ViewDataBinding>(
    private val itemClickListener: ItemClickListener? = null,
    private val layout: Int
) : RecyclerView.Adapter<RecyclerAdapterBinding.MainViewHolder<R>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder<R> {
        return MainViewHolder(
            itemClickListener,
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layout,
                parent,
                false
            )
        )
    }

    open class MainViewHolder<R : ViewDataBinding>(
        private val itemClickListener: ItemClickListener?,
        val binding: R
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindListener() {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClickListener(binding.root, absoluteAdapterPosition)
            }
        }
    }

    /****
     * handle item click listener
     * @param pos "will return the position of clicked item"
     */
    interface ItemClickListener {
        fun onItemClickListener(v: View, pos: Int)
    }
}