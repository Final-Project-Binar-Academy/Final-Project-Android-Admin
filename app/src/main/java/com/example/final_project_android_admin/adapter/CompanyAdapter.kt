package com.example.final_project_android_admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.response.company.DataCompany
import com.example.final_project_android_admin.databinding.ListCompanyBinding

class CompanyAdapter(private var itemClick : CompanyAdapter.ListCompanyInterface) : RecyclerView.Adapter<CompanyAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<DataCompany>(){
        override fun areItemsTheSame(
            oldItem: DataCompany,
            newItem: DataCompany
        ): Boolean {
            return oldItem.id == oldItem.id
        }

        override fun areContentsTheSame(
            oldItem: DataCompany,
            newItem: DataCompany
        ): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder (private val binding: ListCompanyBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: DataCompany) {
            with(item) {
                binding.dataBinding = item

                binding.imgFilm.load(item.companyImage) {
                    crossfade(true)
                    placeholder(R.drawable.img_placeholder)
                }

                binding.card.setOnClickListener{
                    item.id?.let { it1 -> itemClick.edit(it1) }
                }

            }

        }
    }

    interface ListCompanyInterface {
        fun edit(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListCompanyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    fun setData(data : List<DataCompany>){
        differ.submitList(data)
    }
}