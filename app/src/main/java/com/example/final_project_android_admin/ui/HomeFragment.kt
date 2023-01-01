package com.example.final_project_android_admin.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.databinding.FragmentHomeBinding
import com.example.final_project_android_admin.utils.TotalDataManager
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.*
import com.example.final_project_android_admin.viewmodel.factory.*
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel
    private lateinit var profileViewModel : ProfileViewModel
    private lateinit var pref: UserDataStoreManager
    private lateinit var total: TotalDataManager
    private lateinit var flightViewModel: FlightViewModel
    private lateinit var companyViewModel: CompanyViewModel
    private lateinit var airportViewModel: AirportViewModel
    private lateinit var airplaneViewModel: AirplaneViewModel
    private lateinit var transactionViewModel: TransactionViewModel

    private lateinit var totalDataViewModel: TotalDataViewModel

    // on below line we are creating
    // a variable for bar data set
    lateinit var barDataSet1: BarDataSet

    // on below line we are creating array list for bar data
    lateinit var barEntriesList: ArrayList<BarEntry>

    // creating a string array for displaying days.
    var days = arrayOf("", "Airport", "Airplane", "Company", "Ticket", "Transaction")

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
        companyViewModel = ViewModelProvider(
            this, CompanyViewModelFactory(ApiHelper(ApiClient.instance), pref)
        )[CompanyViewModel::class.java]
        airportViewModel = ViewModelProvider(
            this, AirportViewModelFactory(ApiHelper(ApiClient.instance), pref)
        )[AirportViewModel::class.java]
        airplaneViewModel = ViewModelProvider(
            this, AirplaneViewModelFactory(ApiHelper(ApiClient.instance), pref)
        )[AirplaneViewModel::class.java]
        transactionViewModel = ViewModelProvider(
            this, TransactionViewModelFactory(ApiHelper(ApiClient.instance), pref)
        )[TransactionViewModel::class.java]
        total = TotalDataManager((requireContext()))
        totalDataViewModel = ViewModelProvider(
            this, TotalDataViewModelFactory(total)
        )[TotalDataViewModel::class.java]

        profileViewModel = ViewModelProvider(
            this, ProfileViewModelFactory(pref)
        )[ProfileViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        seeDetails()

        setUsername()
        setImageProfile()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUsername() {
        viewModel.getDataStoreUsername().observe(viewLifecycleOwner){
            binding.txtHi.text = "Hi, $it"
        }
    }

    private fun setImageProfile() {
        profileViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            profileViewModel.getUserProfile("Bearer $it")
        }
        profileViewModel.user.observe(viewLifecycleOwner){
            Glide.with(requireContext())
                .load(it?.data?.avatar)
                .circleCrop()
                .into(binding.profileImage)
        }
    }

    private fun seeDetails(){
        binding.seeAirplane.setOnClickListener(){
            findNavController().navigate(R.id.airplaneFragment)
        }
        binding.seeAirport.setOnClickListener(){
            findNavController().navigate(R.id.airportFragment)
        }
        binding.seeCompany.setOnClickListener(){
            findNavController().navigate(R.id.companyFragment)
        }
        binding.seeTicket.setOnClickListener(){
            findNavController().navigate(R.id.flightFragment)
        }
        binding.seeTransaction.setOnClickListener(){
            findNavController().navigate(R.id.transactionFragment)
        }
        binding.profile.setOnClickListener(){
            findNavController().navigate(R.id.profileFragment)
        }
        binding.seeDashboard.setOnClickListener(){
            findNavController().navigate(R.id.homeFragment)
        }

    }

//    private fun barchart() {
//
//        // on below line we are creating a new bar data set
//        barDataSet1 = BarDataSet(getBarChartDataForSet1(), "C++")
//        barDataSet1.setColor(resources.getColor(R.color.purple_200))
//
//        // on below line we are adding bar data set to bar data
//        var data = BarData(barDataSet1)
//
//        // on below line we are setting data to our chart
//        binding.idBarChart.data = data
//
//        // on below line we are setting description enabled.
//        binding.idBarChart.description.isEnabled = false
//
//        // on below line setting x axis
//        var xAxis = binding.idBarChart.xAxis
//
//        // below line is to set value formatter to our x-axis and
//        // we are adding our days to our x axis.
//        xAxis.valueFormatter = IndexAxisValueFormatter(days)
//
//        // below line is to set center axis
//        // labels to our bar chart.
////        xAxis.setCenterAxisLabels(true)
//
//        // below line is to set position
//        // to our x-axis to bottom.
//        xAxis.position = XAxis.XAxisPosition.BOTTOM
//
//        // below line is to set granularity
//        // to our x axis labels.
//        xAxis.setGranularity(1f)
//
//        // below line is to enable
//        // granularity to our x axis.
//        xAxis.setGranularityEnabled(true)
//
//        // below line is to make our
//        // bar chart as draggable.
//        binding.idBarChart.setDragEnabled(true)
//
//        // below line is to make visible
//        // range for our bar chart.
//        binding.idBarChart.setVisibleXRangeMaximum(5f)
//
//        // we are setting width of
//        // bar in below line.
//        data.barWidth = 0.5f
//
//        // below line is to set minimum
//        // axis to our chart.
//        binding.idBarChart.xAxis.axisMinimum = 0f
//
//        // below line is to
//        // animate our chart.
//        binding.idBarChart.animate()
//
//        // below line is to invalidate
//        // our bar chart.
//        binding.idBarChart.invalidate()
//
//    }

    private fun getBarChartDataForSet1(): ArrayList<BarEntry> {
        barEntriesList = ArrayList()
        companyViewModel.getLiveDataCompany().observe(viewLifecycleOwner){
            it?.totalData?.let { it1 -> totalDataViewModel.saveCompany(it1) }
        }
        // on below line we are adding
        // data to our bar entries list
//        var totalCompany by Delegates.notNull<Float>()
//        totalDataViewModel.getTotalCompany().observe(viewLifecycleOwner){
//            totalCompany = it.toFloat()
////            Log.d("company", total.toString())
//        }
//
//        Log.d("company", totalCompany.toString()))
        barEntriesList.add(BarEntry(1f, 1f))
        barEntriesList.add(BarEntry(2f, 2f))
        barEntriesList.add(BarEntry(3f, 3f))
        barEntriesList.add(BarEntry(4f, 4f))
        barEntriesList.add(BarEntry(5f, 5f))
        return barEntriesList
    }

//    private fun totalData() {
//        flightViewModel.getDataFlight()
//        flightViewModel.getLiveDataFlight().observe(viewLifecycleOwner){
//            binding.totalTicket.text = it?.totalData.toString()
//        }
//
//        companyViewModel.getDataCompany()
//        companyViewModel.getLiveDataCompany().observe(viewLifecycleOwner){
//            binding.totalCompany.text = it?.totalData.toString()
//        }
//
//        airportViewModel.getDataAirport()
//        airportViewModel.getLiveDataAirport().observe(viewLifecycleOwner){
//            binding.totalAirport.text = it?.totalData.toString()
//        }
//
//        airplaneViewModel.getDataAirplane()
//        airplaneViewModel.getLiveDataAirplane().observe(viewLifecycleOwner){
//            binding.totalAirplane.text = it?.totalData.toString()
//        }
//
//        transactionViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
//            transactionViewModel.getDataTransaction("Bearer $it")
//            transactionViewModel.getLiveDataTransaction().observe(viewLifecycleOwner) {
//                binding.totalTransaction.text = it?.totalData.toString()
//            }
//        }
//
//    }

//    private fun sideBar(){
//        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
//            // Handle menu item selected
//            menuItem.isChecked = true
//
//            when (menuItem.itemId) {
//                R.id.dashboard -> {
//                    findNavController().navigate(R.id.homeFragment)
//                    true
//                }
//                R.id.profile -> {
//                    findNavController().navigate(R.id.profileFragment)
//                    true
//                }
//                R.id.logout -> {
//                    viewModel.removeIsLoginStatus()
//                    viewModel.removeId()
//                    viewModel.removeUsername()
//                    viewModel.removeToken()
//                    viewModel.getDataStoreIsLogin().observe(viewLifecycleOwner) {
//                        findNavController().navigate(R.id.loginFragment)
//                    }
//                }
//                else -> false
//            }
//
//            binding.drawerLayout.close()
//            true
//        }
//
//    }

}