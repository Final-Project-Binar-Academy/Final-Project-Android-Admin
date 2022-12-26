package com.example.final_project_android_admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.response.airplane.DataAirplane
import com.example.final_project_android_admin.databinding.CustomListViewBinding

class ListAirplaneAdapter(context: Context, airplanes: ArrayList<DataAirplane>) :
    ArrayAdapter<DataAirplane>(context, 0, airplanes) {

    private val filter = AirplaneFilter(airplanes)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val airplane = getItem(position)
        val view = LayoutInflater.from(context).inflate(R.layout.custom_list_view, parent, false)
        val binding = CustomListViewBinding.bind(view)
        binding.txt1.text = airplane?.companyeName
        binding.txt2.text = airplane?.airplaneName
        binding.txt3.text = airplane?.airplaneCode

        return view
    }

    override fun getFilter() = filter

    inner class AirplaneFilter(private val originalList: List<DataAirplane>) : Filter() {

        private val airplane: ArrayList<DataAirplane> = ArrayList()

        init {
            synchronized (this) {
                airplane.addAll(originalList)
            }
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            if (constraint == null) return FilterResults()

            val result = FilterResults()

            if (constraint.isNotEmpty()) {

                airplane.filterTo(airplane) { isWithinConstraint(it, constraint) }

                result.count = airplane.size
                result.values = airplane

            } else {
                synchronized(this) {
                    result.values = airplane
                    result.count = airplane.size
                }

            }
            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            if (results.values == null) return

            @Suppress("UNCHECKED_CAST")
            val filtered = results.values as ArrayList<DataAirplane>

            if (results.count > 0) {
                clear()
                addAll(filtered)
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }


        override fun convertResultToString(resultValue: Any?): CharSequence {
            return (resultValue as DataAirplane).airplaneName.toString()
        }

        private fun isWithinConstraint(airplane: DataAirplane, constraint: CharSequence): Boolean {
            return airplane.airplaneName?.toLowerCase()?.contains(constraint, true)!!
        }

    }
}