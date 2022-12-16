package com.example.final_project_android_admin.ui.airport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.finalproject14.adapter.AirportAdapter
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.di.ApiClient
import com.example.final_project_android_admin.data.api.response.airport.DataAirport
import com.example.final_project_android_admin.data.api.service.ApiHelperImpl
import com.example.final_project_android_admin.databinding.FragmentAirportBinding
import com.example.final_project_android_admin.viewmodel.AirportViewModel
import com.example.final_project_android_admin.viewmodel.factory.AirportViewModelFactory
import com.google.android.material.snackbar.Snackbar

class AirportFragment : Fragment() {
    private var _binding: FragmentAirportBinding? = null
    private val binding get() = _binding!!

    private lateinit var airportViewModel: AirportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        airportViewModel = ViewModelProvider(
            this, AirportViewModelFactory(ApiHelperImpl(ApiClient.instance))
        )[AirportViewModel::class.java]

        _binding = FragmentAirportBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter: AirportAdapter by lazy {
            AirportAdapter {

            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        binding.apply {
            airportViewModel.getDataAirport()
            airportViewModel.getLiveDataAirport().observe(viewLifecycleOwner){
                if (it != null){
                    adapter.setData(it.data as List<DataAirport>)
                }else {
                    Snackbar.make(binding.root, "Data Gagal Dimuat", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(
                            ContextCompat.getColor(requireContext(),
                                R.color.basic
                            ))
                        .setTextColor(ContextCompat.getColor(requireContext(), R.color.basic))
                        .show()
                }
            }
            rvPost.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvPost.adapter = adapter
        }

        sideBar()
        add()

        super.onViewCreated(view, savedInstanceState)
    }

    fun sideBar(){
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

    fun add(){
        binding.btnAdd.setOnClickListener{
            findNavController().navigate(R.id.action_airplaneFragment_to_addAirplaneFragment)
        }
    }
}