package com.example.final_project_android_admin.ui.airplane

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.adapter.ListAirplaneAdapter
import com.example.final_project_android_admin.adapter.ListCompanyAdapter
import com.example.final_project_android_admin.data.api.response.airplane.DataAirplane
import com.example.final_project_android_admin.data.api.response.company.DataCompany
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.databinding.FragmentAddAirplaneBinding
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.AirplaneViewModel
import com.example.final_project_android_admin.viewmodel.CompanyViewModel
import com.example.final_project_android_admin.viewmodel.factory.AirplaneViewModelFactory
import com.example.final_project_android_admin.viewmodel.factory.CompanyViewModelFactory
import com.google.android.material.snackbar.Snackbar

class AddAirplaneFragment : Fragment() {
    private var _binding: FragmentAddAirplaneBinding? = null
    private val binding get() = _binding!!
    private lateinit var airplaneViewModel: AirplaneViewModel
    private lateinit var pref: UserDataStoreManager

    private lateinit var companyViewModel: CompanyViewModel
    private var _companyId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        pref = UserDataStoreManager(requireContext())
        airplaneViewModel = ViewModelProvider(
            this, AirplaneViewModelFactory(ApiHelper(ApiClient.instance), pref)
        )[AirplaneViewModel::class.java]
        companyViewModel = ViewModelProvider(
            this, CompanyViewModelFactory(ApiHelper(ApiClient.instance), pref)
        )[CompanyViewModel::class.java]

        _binding = FragmentAddAirplaneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        companyViewModel.getAirplaneCompany()
        companyViewModel.LiveDataAirplaneCompany.observe(viewLifecycleOwner){
            if (it != null) {
                val listCompany: ArrayList<DataCompany> = it as ArrayList<DataCompany>
                val adapter = ListCompanyAdapter(requireContext(), listCompany)
                binding.apply {
                    actvCompany.threshold = 0
                    actvCompany.setAdapter(adapter)
                    actvCompany.setOnItemClickListener { _, _, position, _ ->
                        val data = adapter.getItem(position)
                        binding.actvCompany.setText(data?.companyName)
                        _companyId = data?.id!!
                    }
                }
            }
        }
        airplaneViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            "Bearer $it"
        }

        binding.btnSave.setOnClickListener {
            val _airplaneName = binding.txtAirplane.text.toString()
            val _airplaneCode = binding.txtAirplaneCode.text.toString()
            airplaneViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                if (it != null) {
                    airplaneViewModel.createAirplane(_airplaneName, _airplaneCode, _companyId, "Bearer $it")
                    Snackbar.make(binding.root, "Airplane Berhasil Ditambahkan", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.basic
                            )
                        )
                        .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        .show()
                }
            }
            findNavController().navigate(R.id.action_addAirplaneFragment_to_airplaneFragment)
        }

        super.onViewCreated(view, savedInstanceState)
    }
}