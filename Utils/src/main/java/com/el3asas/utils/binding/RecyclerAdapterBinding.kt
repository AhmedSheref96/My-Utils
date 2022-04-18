package com.el3asas.utils.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class RecyclerAdapterBinding<R : ViewDataBinding>(
    private val itemClickListener: ItemClickListener? = null
) : RecyclerView.Adapter<RecyclerAdapterBinding.MainViewHolder<R>>() {
    abstract val bindingInflater: (LayoutInflater) -> ViewBinding

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder<R> {
        return MainViewHolder(
            itemClickListener,
            bindingInflater(LayoutInflater.from(parent.context)) as R
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