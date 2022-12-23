package com.example.final_project_android_admin.ui.airplane

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.databinding.FragmentAddAirplaneBinding
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.AirplaneViewModel
import com.example.final_project_android_admin.viewmodel.factory.AirplaneViewModelFactory

class AddAirplaneFragment : Fragment() {
    private var _binding: FragmentAddAirplaneBinding? = null
    private val binding get() = _binding!!
    private lateinit var airplaneViewModel: AirplaneViewModel
    private lateinit var pref: UserDataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        pref = UserDataStoreManager(requireContext())
        airplaneViewModel = ViewModelProvider(
            this, AirplaneViewModelFactory(ApiHelper(ApiClient.instance), pref)
        )[AirplaneViewModel::class.java]

        _binding = FragmentAddAirplaneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        airplaneViewModel.airplaneResult.observe(viewLifecycleOwner){
//            val stringArray = arrayOf(it.company?.companyName)
//            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, stringArray)
//            binding.actvCompany.setAdapter(arrayAdapter)
//        }


        binding.btnSave.setOnClickListener{
            findNavController().navigate(R.id.action_addAirplaneFragment_to_airplaneFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}