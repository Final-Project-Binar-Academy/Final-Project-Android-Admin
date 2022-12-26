package com.example.final_project_android_admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.response.flight.DataFlight
import com.example.final_project_android_admin.databinding.DropdownItemBinding

class ListCodeTicketAdapter(context: Context, ticket: ArrayList<DataFlight>) :
    ArrayAdapter<DataFlight>(context, 0, ticket) {

    private val filter = TicketFilter(ticket)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val ticket = getItem(position)
        val view = LayoutInflater.from(context).inflate(R.layout.dropdown_item, parent, false)
        val binding = DropdownItemBinding.bind(view)
        binding.textView.text = ticket?.code

        return view
    }

    override fun getFilter() = filter

    inner class TicketFilter(private val originalList: List<DataFlight>) : Filter() {

        private val ticket: ArrayList<DataFlight> = ArrayList()

        init {
            synchronized (this) {
                ticket.addAll(originalList)
            }
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            if (constraint == null) return FilterResults()

            val result = FilterResults()

            if (constraint.isNotEmpty()) {

                ticket.filterTo(ticket) { isWithinConstraint(it, constraint) }

                result.count = ticket.size
                result.values = ticket

            } else {
                synchronized(this) {
                    result.values = ticket
                    result.count = ticket.size
                }

            }
            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            if (results.values == null) return

            @Suppress("UNCHECKED_CAST")
            val filtered = results.values as ArrayList<DataFlight>

            if (results.count > 0) {
                clear()
                addAll(filtered)
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }


        override fun convertResultToString(resultValue: Any?): CharSequence {
            return (resultValue as DataFlight).code.toString()
        }

        private fun isWithinConstraint(ticket: DataFlight, constraint: CharSequence): Boolean {
            return ticket.code?.toLowerCase()?.contains(constraint, true)!!
        }

    }
}