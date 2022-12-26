package com.example.final_project_android_admin.ui.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.adapter.FlightAdapter
import com.example.final_project_android_admin.adapter.TransactionAdapter
import com.example.final_project_android_admin.data.api.response.flight.DataFlight
import com.example.final_project_android_admin.data.api.response.transaction.DataTransaction
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.databinding.FragmentFlightBinding
import com.example.final_project_android_admin.databinding.FragmentTransactionBinding
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.FlightViewModel
import com.example.final_project_android_admin.viewmodel.LoginViewModel
import com.example.final_project_android_admin.viewmodel.TransactionViewModel
import com.example.final_project_android_admin.viewmodel.factory.FlightViewModelFactory
import com.example.final_project_android_admin.viewmodel.factory.TransactionViewModelFactory
import com.example.final_project_android_admin.viewmodel.factory.UserViewModelFactory
import com.google.android.material.snackbar.Snackbar


class TransactionFragment : Fragment(), TransactionAdapter.ListTransactionInterface {
    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!

    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var viewModel: LoginViewModel
    private lateinit var pref: UserDataStoreManager
    private var click: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        pref = UserDataStoreManager(requireContext())
        transactionViewModel = ViewModelProvider(
            this, TransactionViewModelFactory(ApiHelper(ApiClient.instance), pref)
        )[TransactionViewModel::class.java]

        _binding = FragmentTransactionBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
        sideBar()
        topBar()
        choice()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun choice(){
        val adapter: TransactionAdapter by lazy {
            TransactionAdapter {

            }
        }
        if (click == false){
            binding.apply {
                transactionViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                    transactionViewModel.getDataTransaction("Bearer $it")
                }
                transactionViewModel.getLiveDataTransaction().observe(viewLifecycleOwner){
                    if (it != null){
                        adapter.setData(it.data as List<DataTransaction>)
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
        }
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

    private fun topBar(){

    }

    override fun onItemClick(TransactionDetail: DataTransaction) {
        TODO("Not yet implemented")
    }
}