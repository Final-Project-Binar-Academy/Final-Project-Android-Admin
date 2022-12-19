package com.example.final_project_android_admin.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.response.airport.DataAirport
import com.example.final_project_android_admin.databinding.ListAirportBinding

class AirportAdapter (private val itemClick: (DataAirport) -> Unit) : RecyclerView.Adapter<AirportAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<DataAirport>(){
        override fun areItemsTheSame(
            oldItem: DataAirport,
            newItem: DataAirport
        ): Boolean {
            return oldItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataAirport,
            newItem: DataAirport
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)

    class ViewHolder(private val binding: ListAirportBinding, val itemClick: (DataAirport) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataAirport) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.dataBinding = item

                binding.btnEdit.setOnClickListener{
                    var bund = Bundle()
                    item.id?.let { it1 -> bund.putInt("id", it1) }
                    item.airportName?.let { it1 -> bund.putString("airportName", it1) }
                    item.city?.let { it1 -> bund.putString("city", it1) }
                    item.cityCode?.let { it1 -> bund.putString("citycode", it1) }
                    findNavController(it).navigate(R.id.action_airportFragment_to_editAirportFragment, bund)
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListAirportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = differ.currentList[position]
        holder.bind(film)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data : List<DataAirport>){
        differ.submitList(data)
    }

    interface ListAirportInterface {
        fun onItemClick(AirportDetail: DataAirport)
    }
}