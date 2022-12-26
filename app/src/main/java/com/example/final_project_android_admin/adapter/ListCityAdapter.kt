package com.example.final_project_android_admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.response.airport.DataAirport
import com.example.final_project_android_admin.databinding.CustomListViewBinding
import com.example.final_project_android_admin.databinding.DropdownItemBinding
import java.util.ArrayList

class ListCityAdapter(context: Context, airports: ArrayList<DataAirport>) :
    ArrayAdapter<DataAirport>(context, 0, airports) {

    private val filter = AirportFilter(airports)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val airport = getItem(position)
        val view = LayoutInflater.from(context).inflate(R.layout.custom_list_view, parent, false)
        val binding = CustomListViewBinding.bind(view)
        binding.txt1.text = airport?.city
        binding.txt2.text = airport?.airportName
        binding.txt3.text = airport?.cityCode

        return view
    }

    override fun getFilter() = filter

    inner class AirportFilter(private val originalList: List<DataAirport>) : Filter() {

        private val airport: ArrayList<DataAirport> = ArrayList()

        init {
            synchronized (this) {
                airport.addAll(originalList)
            }
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            if (constraint == null) return FilterResults()

            val result = FilterResults()
            airport.clear()

            if (constraint.isNotEmpty()) {

                airport.filterTo(airport) { isWithinConstraint(it, constraint) }

                result.count = airport.size
                result.values = airport

            } else {
                synchronized(this) {
                    result.values = airport
                    result.count = airport.size
                }

            }
            return result
        }


        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            if (results.values == null) return

            @Suppress("UNCHECKED_CAST")
            val filtered = results.values as ArrayList<DataAirport>

            if (results.count > 0) {
                clear()
                addAll(filtered)
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }


        override fun convertResultToString(resultValue: Any?): CharSequence {
            return (resultValue as DataAirport).city.toString()
        }

        private fun isWithinConstraint(airport: DataAirport, constraint: CharSequence): Boolean {
            return airport.city?.toLowerCase()?.contains(constraint, true)!!
        }

    }
}