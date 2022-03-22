package com.el3asas.utils.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerPagedDataAdapterBinding<R : ViewDataBinding, T : Any>(
    private val itemClickListener: ItemClickListener? = null,
    private val layout: Int,
    repoComparator: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, RecyclerPagedDataAdapterBinding.MainViewHolder<R>>(repoComparator) {

    open class MainViewHolder<R : ViewDataBinding>(
        private val itemClickListener: ItemClickListener?,
        val binding: R
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindListener() {
            binding.root.setOnClickListener {
                itemClickListener?.onItemClickListener(binding.root, absoluteAdapterPosition)
            }
        }
    }

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

    interface ItemClickListener {
        fun onItemClickListener(v: View, pos: Int)
    }
}