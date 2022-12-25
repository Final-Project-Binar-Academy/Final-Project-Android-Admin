package com.example.final_project_android_admin.ui.airplane

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.adapter.ListCompanyAdapter
import com.example.final_project_android_admin.data.api.response.company.DataCompany
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.databinding.FragmentEditAirplaneBinding
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.AirplaneViewModel
import com.example.final_project_android_admin.viewmodel.CompanyViewModel
import com.example.final_project_android_admin.viewmodel.factory.AirplaneViewModelFactory
import com.example.final_project_android_admin.viewmodel.factory.CompanyViewModelFactory
import com.google.android.material.snackbar.Snackbar

class EditAirplaneFragment : Fragment() {
    private var _binding: FragmentEditAirplaneBinding? = null
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

        _binding = FragmentEditAirplaneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = arguments?.getInt("id")

        if (id != null) {
            airplaneViewModel.getAirplaneDetail(id)
        }
        airplaneViewModel.airplaneDetail.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    txtAirplane.setText(it.data?.airplaneName.toString())
                    txtAirplaneCode.setText(it.data?.airplaneCode.toString())
                    it.data?.company?.id?.let { it1 -> companyViewModel.getCompanyDetail(it1) }
                    companyViewModel.companyDetail.observe(viewLifecycleOwner){
                        binding.actvCompany.hint = it?.data?.companyName
                    }
                }
            }
        }

        companyViewModel.getAirplaneCompany()
        companyViewModel.LiveDataAirplaneCompany.observe(viewLifecycleOwner){
            if (it != null) {
                val adapter = ListCompanyAdapter(requireContext(), it as ArrayList<DataCompany>)
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

        binding.btnEdit.setOnClickListener {
            val _airplaneName = binding.txtAirplane.text.toString()
            val _airplaneCode = binding.txtAirplaneCode.text.toString()
            airplaneViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                if (id != null) {
                    airplaneViewModel.updateAirplane(_airplaneName, _airplaneCode, _companyId, "Bearer $it", id)
                    Snackbar.make(binding.root, "Airplane Berhasil Diupdate", Snackbar.LENGTH_SHORT)
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
            findNavController().navigate(R.id.action_editAirplaneFragment_to_airplaneFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}