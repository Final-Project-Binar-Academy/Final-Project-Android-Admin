package com.example.final_project_android_admin.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.response.flight.DataFlight
import com.example.final_project_android_admin.databinding.ListFlightBinding
import java.text.SimpleDateFormat
import java.util.*

class FlightAdapter (private var itemClick: FlightAdapter.ListFlightInterface) : RecyclerView.Adapter<FlightAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<DataFlight>(){
        override fun areItemsTheSame(
            oldItem: DataFlight,
            newItem: DataFlight
        ): Boolean {
            return oldItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataFlight,
            newItem: DataFlight
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding: ListFlightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataFlight) {
            with(item) {
                binding.dataBinding = item

                var simpleDateFormat = SimpleDateFormat("E, dd LLL")
                var departure : Date? = item.departureDate
                var departureDate = simpleDateFormat.format(departure?.time).toString()

                var arrival : Date? = item.arrivalDate
                var arrivalDate = simpleDateFormat.format(arrival?.time).toString()

                binding.departureDate.text = departureDate
                binding.departureTime.text = item.departureTime.toString()
                binding.arrivalDate.text = arrivalDate
                binding.arrivalTime.text = item.arrivalTime.toString()
                binding.kelas.text = item.classX.toString()
                binding.btnKelas.text = item.classX.toString()
                binding.price.text = item.price.toString()

                binding.card.setOnClickListener{
                    item.id?.let { it1 -> itemClick.edit(it1) }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListFlightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flight = differ.currentList[position]
        holder.bind(flight)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data : List<DataFlight>){
        differ.submitList(data)
    }

    interface ListFlightInterface {
        fun edit(id: Int)
    }
}