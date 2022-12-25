package com.example.final_project_android_admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.annotation.Nullable
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.response.company.DataCompany
import com.example.final_project_android_admin.databinding.CustomListViewBinding
import com.example.final_project_android_admin.databinding.DropdownItemBinding

class ListCompanyAdapter(context: Context, company: ArrayList<DataCompany>) :
    ArrayAdapter<DataCompany>(context, 0, company) {

    private val filter = CompanyFilter(company)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val company = getItem(position)
        val view = LayoutInflater.from(context).inflate(R.layout.dropdown_item, parent, false)
        val binding = DropdownItemBinding.bind(view)
        binding.textView.text = company?.companyName

        return view
    }

    override fun getFilter() = filter

    inner class CompanyFilter(private val originalList: List<DataCompany>) : Filter() {

        private val company: ArrayList<DataCompany> = ArrayList()

        init {
            synchronized (this) {
                company.addAll(originalList)
            }
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            if (constraint == null) return FilterResults()

            val result = FilterResults()

            if (constraint.isNotEmpty()) {

                company.filterTo(company) { isWithinConstraint(it, constraint) }

                result.count = company.size
                result.values = company

            } else {
                synchronized(this) {
                    result.values = company
                    result.count = company.size
                }

            }
            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            if (results.values == null) return

            @Suppress("UNCHECKED_CAST")
            val filtered = results.values as ArrayList<DataCompany>

            if (results.count > 0) {
                clear()
                addAll(filtered)
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }


        override fun convertResultToString(resultValue: Any?): CharSequence {
            return (resultValue as DataCompany).companyName.toString()
        }

        private fun isWithinConstraint(company: DataCompany, constraint: CharSequence): Boolean {
            return company.companyName?.toLowerCase()?.contains(constraint, true)!!
        }

    }
}