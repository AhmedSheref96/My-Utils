package com.el3asas.utils.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class RecyclerPagedDataAdapterBinding<R : ViewDataBinding, T : Any>(
    private val itemClickListener: ItemClickListener? = null,
    repoComparator: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, RecyclerPagedDataAdapterBinding.MainViewHolder<R>>(repoComparator) {
    abstract val bindingInflater: (LayoutInflater) -> ViewBinding

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

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder<R> {
        return MainViewHolder(
            itemClickListener,
            bindingInflater(LayoutInflater.from(parent.context)) as R
        )
    }

    interface ItemClickListener {
        fun onItemClickListener(v: View, pos: Int)
    }
}