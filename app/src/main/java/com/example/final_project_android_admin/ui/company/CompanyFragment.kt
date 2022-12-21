package com.example.final_project_android_admin.ui.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.adapter.CompanyAdapter
import com.example.final_project_android_admin.data.api.response.company.DataCompany
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.databinding.FragmentCompanyBinding
import com.example.final_project_android_admin.viewmodel.CompanyViewModel
import com.example.final_project_android_admin.viewmodel.factory.CompanyViewModelFactory
import com.google.android.material.snackbar.Snackbar

class CompanyFragment : Fragment(), CompanyAdapter.ListCompanyInterface {
    private var _binding: FragmentCompanyBinding? = null
    private val binding get() = _binding!!

    private lateinit var companyViewModel: CompanyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        companyViewModel = ViewModelProvider(
            this, CompanyViewModelFactory(ApiHelper(ApiClient.instance))
        )[CompanyViewModel::class.java]

        _binding = FragmentCompanyBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        sideBar()
        add()

        val adapter: CompanyAdapter by lazy {
            CompanyAdapter {

            }
        }

        binding.apply {
            companyViewModel.getDataCompany()
            companyViewModel.getLiveDataCompany().observe(viewLifecycleOwner){
                if (it != null){
                    adapter.setData(it.data as List<DataCompany>)
                }else{
                    Snackbar.make(binding.root, "Data Gagal Dimuat", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(
                            ContextCompat.getColor(requireContext(),
                                R.color.button
                            ))
                        .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        .show()
                }
            }
            rvPost.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvPost.adapter = adapter
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun sideBar(){
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            menuItem.isChecked = true

            when (menuItem.itemId) {
                R.id.dashboard -> {
                    findNavController().navigate(R.id.homeFragment)
                    true
                }
                R.id.flight -> {
                    findNavController().navigate(R.id.flightFragment)
                    true
                }
                R.id.airplane -> {
                    findNavController().navigate(R.id.airplaneFragment)
                    true
                }
                R.id.airport -> {
                    findNavController().navigate(R.id.airportFragment)
                    true
                }
                R.id.company -> {
                    findNavController().navigate(R.id.companyFragment)
                    true
                }
                R.id.transaction -> {
                    findNavController().navigate(R.id.transactionFragment)
                    true
                }
                else -> false
            }

            binding.drawerLayout.close()
            true
        }
    }

    private fun add(){
        binding.btnAdd.setOnClickListener{
            findNavController().navigate(R.id.action_companyFragment_to_addCompanyFragment)
        }
    }

    override fun onItemClick(CompanyDetail: DataCompany) {
        TODO("Not yet implemented")
    }
}