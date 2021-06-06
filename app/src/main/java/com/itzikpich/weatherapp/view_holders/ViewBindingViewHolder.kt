package com.itzikpich.weatherapp.view_holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class ViewBindingViewHolder(val binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {

    companion object {
        inline fun createVH(
            parent: ViewGroup,
            crossinline block: (inflater: LayoutInflater, container: ViewGroup, attach: Boolean) -> ViewBinding
        ) = ViewBindingViewHolder(block(LayoutInflater.from(parent.context), parent, false))
    }

}