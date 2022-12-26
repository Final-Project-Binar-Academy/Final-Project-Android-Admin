package com.example.final_project_android_admin.ui.flight

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
import com.example.final_project_android_admin.adapter.FlightAdapter
import com.example.final_project_android_admin.data.api.response.flight.DataFlight
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.databinding.FragmentFlightBinding
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.FlightViewModel
import com.example.final_project_android_admin.viewmodel.LoginViewModel
import com.example.final_project_android_admin.viewmodel.factory.FlightViewModelFactory
import com.example.final_project_android_admin.viewmodel.factory.UserViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FlightFragment : Fragment(), FlightAdapter.ListFlightInterface {
    private var _binding: FragmentFlightBinding? = null
    private val binding get() = _binding!!

    private lateinit var flightViewModel: FlightViewModel
    private lateinit var viewModel: LoginViewModel
    private lateinit var pref: UserDataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        pref = UserDataStoreManager(requireContext())
        viewModel = ViewModelProvider(
            this, UserViewModelFactory(ApiHelper(ApiClient.instance), pref)
        )[LoginViewModel::class.java]

        flightViewModel = ViewModelProvider(
            this, FlightViewModelFactory(ApiHelper(ApiClient.instance), pref)
        )[FlightViewModel::class.java]

        _binding = FragmentFlightBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        sideBar()
        add()

        val delete = arguments?.getInt("id_delete")

        if (delete != null){
            flightViewModel.getDataStoreToken().observe(viewLifecycleOwner){
                flightViewModel.deleteFlight("Bearer $it", delete)
                Snackbar.make(binding.root, "Airport Berhasil Dihapus", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(ContextCompat.getColor(requireContext(),
                        R.color.basic
                    ))
                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    .show()
            }
        }

        val adapter: FlightAdapter by lazy {
            FlightAdapter {

            }
        }
        binding.apply {
            flightViewModel.getDataFlight()
            flightViewModel.getLiveDataFlight().observe(viewLifecycleOwner){
                if (it != null){
                    adapter.setData(it.data as List<DataFlight>)
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

    private fun add(){
        binding.btnAdd.setOnClickListener{
            findNavController().navigate(R.id.action_flightFragment_to_addFlightFragment)
        }
    }

    override fun onItemClick(FlightDetail: DataFlight) {
        TODO("Not yet implemented")
    }
}