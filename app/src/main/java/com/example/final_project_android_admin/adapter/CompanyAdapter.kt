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
import com.example.final_project_android_admin.data.api.response.company.DataCompany
import com.example.final_project_android_admin.databinding.ListCompanyBinding

class CompanyAdapter (private val itemClick: (DataCompany) -> Unit) : RecyclerView.Adapter<CompanyAdapter.ViewHolder>(){

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

    class ViewHolder(private val binding: ListCompanyBinding, val itemClick: (DataCompany) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataCompany) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.dataBinding = item

                binding.imgFilm.load(item.companyImage) {
                    crossfade(true)
                    placeholder(R.drawable.img_placeholder)
                }

                binding.btnEdit.setOnClickListener{
                    var bund = Bundle()
                    item.id?.let { it1 -> bund.putInt("id", it1) }
                    Navigation.findNavController(it)
                        .navigate(R.id.action_companyFragment_to_editCompanyFragment, bund)
                }

                binding.btnDelete.setOnClickListener{
                    var bund = Bundle()
                    item.id?.let { it1 -> bund.putInt("id_delete", it1) }
                    Navigation.findNavController(it).navigate(R.id.companyFragment, bund)
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListCompanyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val company = differ.currentList[position]
        holder.bind(company)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data : List<DataCompany>){
        differ.submitList(data)
    }

    interface ListCompanyInterface {
        fun onItemClick(CompanyDetail: DataCompany)
    }
}