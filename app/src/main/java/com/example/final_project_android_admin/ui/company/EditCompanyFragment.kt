package com.example.final_project_android_admin.ui.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.databinding.FragmentEditCompanyBinding
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.CompanyViewModel
import com.example.final_project_android_admin.viewmodel.factory.CompanyViewModelFactory
import com.google.android.material.snackbar.Snackbar

class EditCompanyFragment : Fragment() {
    private var _binding: FragmentEditCompanyBinding? = null
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
        _binding = FragmentEditCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = arguments?.getInt("id")

        if (id != null) {
            companyViewModel.getCompanyDetail(id)
        }
        companyViewModel.companyDetail.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    txtCompany.setText(it.data?.companyName.toString())
                }
            }
        }

        companyViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            "Bearer $it"
        }

        binding.btnEdit.setOnClickListener {
            val _companyName = binding.txtCompany.text.toString()
            val _companyImage = ""
            companyViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                if (id != null) {
                    companyViewModel.updateCompany(_companyImage, _companyName, "Bearer $it", id)
                    Snackbar.make(binding.root, "Airport Berhasil Diupdate", Snackbar.LENGTH_SHORT)
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
            findNavController().navigate(R.id.action_editCompanyFragment_to_companyFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}