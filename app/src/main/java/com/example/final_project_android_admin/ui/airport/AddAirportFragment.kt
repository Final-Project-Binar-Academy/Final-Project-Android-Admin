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
import com.example.final_project_android_admin.databinding.FragmentAddAirportBinding
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.AirportViewModel
import com.example.final_project_android_admin.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAirportFragment : Fragment() {
    private var _binding: FragmentAddAirportBinding? = null
    private val binding get() = _binding!!

    private lateinit var airportViewModel: AirportViewModel
    private lateinit var userViewModel: LoginViewModel
    private lateinit var pref: UserDataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        pref = UserDataStoreManager(requireContext())
        airportViewModel = ViewModelProvider(this)[AirportViewModel::class.java]


        pref = UserDataStoreManager(requireContext())
        userViewModel = ViewModelProvider(this)[LoginViewModel::class.java]


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

        binding.btnSave.setOnClickListener {
            val airportName = binding.txtAirport.text.toString()
            val city = binding.txtCity.text.toString()
            val cityCode = binding.txtCityCode.text.toString()
            airportViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                airportViewModel.createAirport(airportName, city, cityCode, "Bearer $it")
                Snackbar.make(binding.root, "Airport Berhasil Ditambahkan", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(ContextCompat.getColor(requireContext(),
                        R.color.basic
                    ))
                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    .show()
                findNavController().navigate(R.id.action_addAirportFragment_to_airportFragment)
            }
        }
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
            .setBackgroundTint(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            .show()
    }
}