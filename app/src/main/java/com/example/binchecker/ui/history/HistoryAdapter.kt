package com.example.binchecker.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.binchecker.databinding.BinItemBinding
import com.example.binchecker.model.BinModel

class HistoryAdapter (private val listener : Listener) : ListAdapter<BinModel, HistoryAdapter.ViewHolder>(ItemCallback), View.OnClickListener {
    
    override fun onClick(v: View) {
        val bin = v.tag as BinModel
        listener.onItemClick(bin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BinItemBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bin = getItem(position)
        holder.bind(bin)
    }

    interface Listener{
        fun onItemClick(item: BinModel)
    }

    class ViewHolder(private val binding: BinItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: BinModel) {
            binding.apply {
                root.tag = item
                textBin.text = item.bin.toString()
                textScheme.text = item.scheme
                textType.text = item.type
                textCountry.text = item.country.alpha2
            }
        }
    }

    object ItemCallback : DiffUtil.ItemCallback<BinModel>() {
        override fun areItemsTheSame(oldItem: BinModel, newItem: BinModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BinModel, newItem: BinModel): Boolean {
            return oldItem == newItem
        }
    }
}