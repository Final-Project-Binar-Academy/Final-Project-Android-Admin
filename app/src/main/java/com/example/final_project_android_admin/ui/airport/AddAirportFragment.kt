package com.example.final_project_android_admin.ui.airport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.response.BaseResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportResponse
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.databinding.FragmentAddAirportBinding
import com.example.final_project_android_admin.viewmodel.AirportViewModel
import com.example.final_project_android_admin.viewmodel.AirportViewModelFactory
import com.google.android.material.snackbar.Snackbar

class AddAirportFragment : Fragment() {
    private var _binding: FragmentAddAirportBinding? = null
    private val binding get() = _binding!!

    private lateinit var airportViewModel: AirportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        airportViewModel = ViewModelProvider(
            this, AirportViewModelFactory(ApiHelper(ApiClient.instance))
        )[AirportViewModel::class.java]

        _binding = FragmentAddAirportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        airportViewModel.airportResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Success -> {
                    processCreate(it.data)
                }
                is BaseResponse.Error -> {
                    processError(it.msg)
                }
                else -> {}
            }
        }

        binding.btnSave.setOnClickListener{
            val airportName = binding.txtAirport.text.toString()
            val city = binding.txtCity.text.toString()
            val cityCode = binding.txtCityCode.text.toString()
            airportViewModel.createAirport(airportName, city, cityCode)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun processCreate(data: AirportResponse?) {
        Toast.makeText(
            requireContext(),
            "Success:" + data?.message,
            Toast.LENGTH_SHORT
        ).show()
        findNavController().navigate(R.id.action_addAirportFragment_to_airportFragment)
    }

    private fun processError(msg: String?) {
//        Toast.makeText(
//            requireContext(),
//            "Error:$msg",
//            Toast.LENGTH_SHORT
//        ).show()
        Snackbar.make(binding.root, "$msg", Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(requireContext(),
                R.color.white
            ))
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            .show()
    }
}