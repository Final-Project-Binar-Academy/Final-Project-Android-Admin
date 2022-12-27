package com.example.final_project_android_admin.ui.airport

import android.app.AlertDialog
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
import com.example.final_project_android_admin.adapter.AirportAdapter
import com.example.final_project_android_admin.data.api.response.airport.DataAirport
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.databinding.FragmentAirportBinding
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.AirportViewModel
import com.example.final_project_android_admin.viewmodel.LoginViewModel
import com.example.final_project_android_admin.viewmodel.factory.AirportViewModelFactory
import com.example.final_project_android_admin.viewmodel.factory.UserViewModelFactory
import com.google.android.material.snackbar.Snackbar

class AirportFragment : Fragment(), AirportAdapter.ListAirportInterface {
    private var _binding: FragmentAirportBinding? = null
    private val binding get() = _binding!!

    private lateinit var airportViewModel: AirportViewModel
    private lateinit var viewModel: LoginViewModel
    private lateinit var pref: UserDataStoreManager
    private lateinit var builder : AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        pref = UserDataStoreManager(requireContext())
        viewModel = ViewModelProvider(
            this, UserViewModelFactory(ApiHelper(ApiClient.instance), pref)
        )[LoginViewModel::class.java]

        airportViewModel = ViewModelProvider(
            this, AirportViewModelFactory(ApiHelper(ApiClient.instance), pref)
        )[AirportViewModel::class.java]
        builder = AlertDialog.Builder(context)

        _binding = FragmentAirportBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        val adapter = AirportAdapter(this)

        binding.apply {
            airportViewModel.getDataAirport()
            airportViewModel.getLiveDataAirport().observe(viewLifecycleOwner){
                if (it != null){
                    adapter.setData(it.data as List<DataAirport>)
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

        sideBar()

        binding.btnAdd.setOnClickListener{
            findNavController().navigate(R.id.action_airportFragment_to_addAirportFragment)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
                R.id.logout -> {
                    viewModel.removeIsLoginStatus()
                    viewModel.removeId()
                    viewModel.removeUsername()
                    viewModel.removeToken()
                    viewModel.getDataStoreIsLogin().observe(viewLifecycleOwner) {
                        findNavController().navigate(R.id.loginFragment)
                    }
                }
                else -> false
            }

            binding.drawerLayout.close()
            true
        }
    }

    override fun edit(id: Int) {
        val bund = Bundle()
        bund.putInt("id", id)
        findNavController().navigate(R.id.action_companyFragment_to_editCompanyFragment, bund)
    }

    override fun delete(id: Int) {
        airportViewModel.getDataStoreToken().observe(viewLifecycleOwner){
            builder.setTitle("Warning!")
                .setMessage("Ingin menghapus airport ini?")
                .setCancelable(true)
                .setPositiveButton("Ya"){ _, _ ->
                    airportViewModel.deleteAirport("Bearer $it", id)
                    Snackbar.make(binding.root, "Airport Berhasil Dihapus", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.basic))
                        .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        .show()
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}