package com.example.final_project_android_admin.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project_android_admin.data.api.response.transaction.DataTransaction
import com.example.final_project_android_admin.databinding.ListTransactionBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter(private val itemClick: TransactionAdapter.ListTransactionInterface) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<DataTransaction>(){
        override fun areItemsTheSame(
            oldItem: DataTransaction,
            newItem: DataTransaction
        ): Boolean {
            return oldItem.id_transaction == oldItem.id_transaction
        }

        override fun areContentsTheSame(
            oldItem: DataTransaction,
            newItem: DataTransaction
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding: ListTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(item: DataTransaction) {
            with(item) {
                binding.dataBinding = item

//                var simpleDateFormat = SimpleDateFormat("E, dd LLL")
//                var departure : Date? = item.go?.departureDate
//                var date1 = simpleDateFormat.format(departure?.time).toString()

                var departureDateOrigin: String? = item.go?.departureDate
                var date1 = departureDateOrigin?.subSequence(0, 9).toString()

                var destinationDateOrigin: String? = item.back?.departureDate
                var date2 = destinationDateOrigin?.subSequence(0, 9).toString()
//                var arrival : Date? = item.back?.departureDate
//                var date2 = simpleDateFormat.format(arrival?.time).toString()

                binding.date.text = date1
                binding.date2.text = date2

                binding.card.setOnClickListener{
                    item.id_transaction?.let { it1 -> itemClick.edit(it1) }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.N)
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
        fun edit(id: Int)
    }
}