package com.example.final_project_android_admin.ui.airport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.response.airport.DataAirport
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.databinding.FragmentEditAirportBinding
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.AirportViewModel
import com.example.final_project_android_admin.viewmodel.factory.AirportViewModelFactory
import com.google.android.material.snackbar.Snackbar

class EditAirportFragment : Fragment() {
    private var _binding: FragmentEditAirportBinding? = null
    private val binding get() = _binding!!
    private lateinit var airportViewModel: AirportViewModel
    private lateinit var pref: UserDataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        pref = UserDataStoreManager(requireContext())
        airportViewModel = ViewModelProvider(
            this, AirportViewModelFactory(ApiHelper(ApiClient.instance), pref)
        )[AirportViewModel::class.java]

        _binding = FragmentEditAirportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = arguments?.getInt("id")

        if (id != null) {
            airportViewModel.getAirportDetail(id)
        }
        airportViewModel.airportDetail.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    txtAirport.setText(it.data?.airportName.toString())
                    txtCity.setText(it.data?.city.toString())
                    txtCityCode.setText(it.data?.cityCode.toString())
                }
            }
        }

        binding.btnEdit.setOnClickListener {
            findNavController().navigate(R.id.action_editAirportFragment_to_airportFragment)
        }
        super.onViewCreated(view, savedInstanceState)

    }
}