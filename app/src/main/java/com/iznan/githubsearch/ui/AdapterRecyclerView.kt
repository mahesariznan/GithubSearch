package com.iznan.githubsearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iznan.githubsearch.R
import com.iznan.githubsearch.model.ItemsItem
import kotlinx.android.synthetic.main.item_recycler_view.view.*

class AdapterRecyclerView : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data = mutableListOf<ItemsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
        return ViewHolderItem(view)
    }

    override fun getItemCount(): Int {
        if (data.size != 0) {
            return data.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderItem) {
            if (data.size != 0) {
                holder.bindViewBook(data[position])
            }
        }
    }

    class ViewHolderItem(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindViewBook(item: ItemsItem) {
            view.textView_name.setText(item.login)
            Glide.with(itemView).load(item.avatarUrl).into(view.imageView_pp)
        }
    }

    fun swapData(newData: List<ItemsItem>) {
        data.clear()
        if (newData.isNotEmpty()) {
            data.addAll(newData)
        }
        notifyDataSetChanged()
    }
}