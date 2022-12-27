package com.example.final_project_android_admin.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.response.airplane.DataAirplane
import com.example.final_project_android_admin.databinding.ListAirplaneBinding

class AirplaneAdapter (private val itemClick: AirplaneAdapter.ListAirplaneInterface) : RecyclerView.Adapter<AirplaneAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<DataAirplane>(){
        override fun areItemsTheSame(
            oldItem: DataAirplane,
            newItem: DataAirplane
        ): Boolean {
            return oldItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataAirplane,
            newItem: DataAirplane
        ): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding: ListAirplaneBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataAirplane) {
            with(item) {
                binding.dataBinding = item

                binding.imgFilm.load(item.company?.companyImage) {
                    crossfade(true)
                    placeholder(R.drawable.img_placeholder)
                }

                binding.btnEdit.setOnClickListener{
                    item.id?.let { it1 -> itemClick.edit(it1) }
                }

                binding.btnDelete.setOnClickListener{
                    item.id?.let { it1 -> itemClick.delete(it1) }
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListAirplaneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val airplane = differ.currentList[position]
        holder.bind(airplane)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data : List<DataAirplane>){
        differ.submitList(data)
    }

    interface ListAirplaneInterface {
        fun edit(id: Int)
        fun delete(id:Int)
    }
}