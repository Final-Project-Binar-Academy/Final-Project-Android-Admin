package com.example.final_project_android_admin.ui.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.databinding.FragmentAddCompanyBinding
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.CompanyViewModel
import com.example.final_project_android_admin.viewmodel.factory.CompanyViewModelFactory

class AddCompanyFragment : Fragment() {
    private var _binding: FragmentAddCompanyBinding? = null
    private val binding get() = _binding!!
    private lateinit var companyViewModel: CompanyViewModel
    private lateinit var pref: UserDataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        pref = UserDataStoreManager(requireContext())
        companyViewModel = ViewModelProvider(
            this, CompanyViewModelFactory(ApiHelper(ApiClient.instance), pref)
        )[CompanyViewModel::class.java]

        _binding = FragmentAddCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnSave.setOnClickListener{
            findNavController().navigate(R.id.action_addAirplaneFragment_to_airplaneFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}