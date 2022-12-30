package com.example.final_project_android_admin.ui.transaction

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.adapter.ListAirplaneAdapter
import com.example.final_project_android_admin.adapter.ListCodeTicketAdapter
import com.example.final_project_android_admin.data.api.response.airplane.DataAirplane
import com.example.final_project_android_admin.data.api.response.flight.DataFlight
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.databinding.FragmentEditFlightBinding
import com.example.final_project_android_admin.databinding.FragmentEditTransactionBinding
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.FlightViewModel
import com.example.final_project_android_admin.viewmodel.TransactionViewModel
import com.example.final_project_android_admin.viewmodel.factory.FlightViewModelFactory
import com.example.final_project_android_admin.viewmodel.factory.TransactionViewModelFactory
import java.util.ArrayList

class EditTransactionFragment : Fragment() {
    private var _binding: FragmentEditTransactionBinding? = null
    private val binding get() = _binding!!
    private lateinit var pref: UserDataStoreManager
    private lateinit var flightViewModel: FlightViewModel
    private lateinit var transactionViewModel: TransactionViewModel
    private var _ticketGo: Int = 0
    private var _ticketBack: Int = 0
    private var typeId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        pref = UserDataStoreManager(requireContext())
        flightViewModel = ViewModelProvider(
            this, FlightViewModelFactory(ApiHelper(ApiClient.instance), pref)
        )[FlightViewModel::class.java]
        transactionViewModel = ViewModelProvider(
            this, TransactionViewModelFactory(ApiHelper(ApiClient.instance), pref)
        )[TransactionViewModel::class.java]
        _binding = FragmentEditTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        display()
        type()
        ticketCodeGo()
        ticketCodeBack()
        val id = arguments?.getInt("id")
        Log.d("id", id.toString())

        binding.btnEdit.setOnClickListener(){
            val birthDate = binding.birthday.text.toString()
            val firstname = binding.firstname.text.toString()
            val lastname = binding.lastname.text.toString()
            val _nik = binding.nik.text.toString()

            if (binding.actvTipe.text.toString() == "Oneway") {
                typeId = 1
            }else {
                typeId = 2
            }

            transactionViewModel.getDataStoreToken().observe(viewLifecycleOwner){
                if (id != null){
                    transactionViewModel.updateTransaction(birthDate, firstname, lastname, _nik,
                        _ticketBack, _ticketGo, typeId, "Bearer $it", id)
                }
            }
            findNavController().navigate(R.id.action_editTransactionFragment_to_transactionFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun ticketCodeBack() {
        flightViewModel.getListFlight()
        flightViewModel.LiveDataListFlight.observe(viewLifecycleOwner){
            if (it != null){
                val listCode: ArrayList<DataFlight> = it as ArrayList<DataFlight>
                val codeAdapter = ListCodeTicketAdapter(requireContext(), listCode)
                binding.apply {
                    actvDestination.threshold = 0
                    actvDestination.setAdapter(codeAdapter)
                    actvDestination.setOnItemClickListener { _, _, position, _ ->
                        val data = codeAdapter.getItem(position)
                        binding.actvDestination.setText("${data?.code}")
                        _ticketBack = data?.id!!
                    }
                }
            }
        }
    }

    private fun display() {
        val id = arguments?.getInt("id")

        if (id != null) {
            transactionViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                transactionViewModel.getTransactionDetail("Bearer $it", id)
            }
            transactionViewModel.transactionDetail.observe(viewLifecycleOwner){
                if (it != null){
                    binding.firstname.setText(it.data?.passenger?.firstName)
                    binding.lastname.setText(it.data?.passenger?.lastName)
                    binding.nik.setText(it.data?.passenger?.nIK)
                    binding.birthday.text = it.data?.passenger?.brithDate
                    binding.actvTipe.hint = it.data?.typeTrip?.type
                }
            }
        }

    }

    private fun ticketCodeGo() {
        flightViewModel.getListFlight()
        flightViewModel.LiveDataListFlight.observe(viewLifecycleOwner){
            if (it != null){
                val listCode: ArrayList<DataFlight> = it as ArrayList<DataFlight>
                val codeAdapter = ListCodeTicketAdapter(requireContext(), listCode)
                binding.apply {
                    actvTicket.threshold = 0
                    actvTicket.setAdapter(codeAdapter)
                    actvTicket.setOnItemClickListener { _, _, position, _ ->
                        val data = codeAdapter.getItem(position)
                        binding.actvTicket.setText("${data?.code}")
                        _ticketGo = data?.id!!
                    }
                }
            }
        }
    }

    private fun type() {
        val tipe = resources.getStringArray(R.array.tipe)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, tipe)
        binding.actvTipe.setAdapter(arrayAdapter)
    }

}