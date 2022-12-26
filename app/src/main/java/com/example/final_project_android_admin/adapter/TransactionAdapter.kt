package com.example.final_project_android_admin.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.response.transaction.DataTransaction
import com.example.final_project_android_admin.databinding.ListTransactionBinding
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter(private val itemClick: (DataTransaction) -> Unit) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<DataTransaction>(){
        override fun areItemsTheSame(
            oldItem: DataTransaction,
            newItem: DataTransaction
        ): Boolean {
            return oldItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataTransaction,
            newItem: DataTransaction
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)

    class ViewHolder(private val binding: ListTransactionBinding, val itemClick: (DataTransaction) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataTransaction) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.dataBinding = item

                binding.ticketCode.text = item.from?.code.toString()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flight = differ.currentList[position]
        holder.bind(flight)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data : List<DataTransaction>){
        differ.submitList(data)
    }

    interface ListTransactionInterface {
        fun onItemClick(TransactionDetail: DataTransaction)
    }
}