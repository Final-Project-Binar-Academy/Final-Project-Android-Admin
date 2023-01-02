package com.example.final_project_android_admin.ui.flight

import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.adapter.ListAirplaneAdapter
import com.example.final_project_android_admin.adapter.ListCityAdapter
import com.example.final_project_android_admin.data.api.response.airplane.DataAirplane
import com.example.final_project_android_admin.data.api.response.airport.DataAirport
import com.example.final_project_android_admin.databinding.FragmentAddFlightBinding
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.AirplaneViewModel
import com.example.final_project_android_admin.viewmodel.AirportViewModel
import com.example.final_project_android_admin.viewmodel.FlightViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFlightFragment : Fragment() {
    private var _binding: FragmentAddFlightBinding? = null
    private val binding get() = _binding!!
    private lateinit var airportViewModel: AirportViewModel
    private lateinit var airplaneViewModel: AirplaneViewModel
    private lateinit var pref: UserDataStoreManager
    private lateinit var flightViewModel: FlightViewModel
    private var _flightFrom: Int = 0
    private var _flightTo: Int = 0
    private var _airplaneId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        pref = UserDataStoreManager(requireContext())
        airportViewModel = ViewModelProvider(this)[AirportViewModel::class.java]
        flightViewModel = ViewModelProvider(this)[FlightViewModel::class.java]
        airplaneViewModel = ViewModelProvider(this)[AirplaneViewModel::class.java]

        _binding = FragmentAddFlightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        departureDate()
        destinationDate()
        departureHour()
        destinationHour()
        flight()
        getKelas()
        airplane()

        binding.btnSave.setOnClickListener{
            val arrivalDate = binding.destinationDate.text.toString()
            val arrivalTime = binding.destinationHour.text.toString()
            val capacity = binding.capacity.text.toString().toInt()
            val _class = binding.actvClass.text.toString()
            val code = binding.code.text.toString()
            val departureDate = binding.departureDate.text.toString()
            val departureTime = binding.departureHour.text.toString()
            val price = binding.price.text.toString().toInt()
            flightViewModel.getDataStoreToken().observe(viewLifecycleOwner){
                if (it != null){
                    flightViewModel.createFlight(_airplaneId, arrivalDate,
                        arrivalTime, capacity, _class, code, departureDate, departureTime,
                        _flightFrom, _flightTo, price, "Bearer $it")
                    Snackbar.make(binding.root, "Ticket Berhasil Ditambahkan", Snackbar.LENGTH_SHORT)
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

            findNavController().navigate(R.id.action_addFlightFragment_to_flightFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun airplane() {
        airplaneViewModel.getListAirplane()
        airplaneViewModel.LiveDataListAirplane.observe(viewLifecycleOwner){
            if (it != null){
                val listAirplane: ArrayList<DataAirplane> = it as ArrayList<DataAirplane>
                val airplaneAdapter = ListAirplaneAdapter(requireContext(), listAirplane)
                binding.apply {
                    actvAirplane.threshold = 0
                    actvAirplane.setAdapter(airplaneAdapter)
                    actvAirplane.setOnItemClickListener { _, _, position, _ ->
                        val data = airplaneAdapter.getItem(position)
                        binding.actvAirplane.setText("${data?.airplaneName}")
                        _airplaneId = data?.id!!
                    }
                }
            }
        }
    }

    private fun getKelas() {
        val kelas = resources.getStringArray(R.array.kelas)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, kelas)
        binding.actvClass.setAdapter(arrayAdapter)
    }

    private fun flight() {
        airportViewModel.getCityAirport()
        airportViewModel.LiveDataCityAirport.observe(viewLifecycleOwner){
            if (it != null) {
                val listAirport: ArrayList<DataAirport> = it as ArrayList<DataAirport>
                val cityAdapter = ListCityAdapter(requireContext(), listAirport)
                binding.apply {
                    actvOrigin.threshold = 0
                    actvOrigin.setAdapter(cityAdapter)
                    actvOrigin.setOnItemClickListener { _, _, position, _ ->
                        val data = cityAdapter.getItem(position)
                        binding.actvOrigin.setText("${data?.city} (${data?.cityCode})")
                        _flightFrom = data?.id!!
                    }
                    actvDestination.threshold = 0
                    actvDestination.setAdapter(cityAdapter)
                    actvDestination.setOnItemClickListener { _, _, position, _ ->
                        val data = cityAdapter.getItem(position)
                        binding.actvDestination.setText("${data?.city} (${data?.cityCode})")
                        _flightTo = data?.id!!
                    }
                }
            }
        }
    }

    private fun destinationHour() {
        val materialTimePickerBuilder: MaterialTimePicker.Builder =
            MaterialTimePicker.Builder()

        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        materialTimePickerBuilder
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(10)
            .setTitleText("Select Appoinment Time")

        val materialTimePicker = materialTimePickerBuilder.build()

        binding.destinationHour.setOnClickListener(
            View.OnClickListener {
                materialTimePicker.show(parentFragmentManager, "MATERIAL_DATE_PICKER")
            })
        materialTimePicker.addOnPositiveButtonClickListener {
            binding.destinationHour.text = "${materialTimePicker.hour}:${materialTimePicker.minute}:00"
        }
    }

    private fun departureHour() {
        val materialTimePickerBuilder: MaterialTimePicker.Builder =
            MaterialTimePicker.Builder()

        val isSystem24Hour = is24HourFormat(requireContext())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        materialTimePickerBuilder
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(10)
            .setTitleText("Select Appoinment Time")

        val materialTimePicker = materialTimePickerBuilder.build()

        binding.departureHour.setOnClickListener(
            View.OnClickListener {
                materialTimePicker.show(parentFragmentManager, "MATERIAL_DATE_PICKER")
            })
        materialTimePicker.addOnPositiveButtonClickListener {
            binding.departureHour.text = "${materialTimePicker.hour}:${materialTimePicker.minute}:00"
        }
    }

    private fun destinationDate() {
        val materialDateBuilder: MaterialDatePicker.Builder<*> =
            MaterialDatePicker.Builder.datePicker()

        materialDateBuilder.setTitleText("SELECT A DATE")
        val materialDatePicker = materialDateBuilder.build()
        binding.departureDate.setOnClickListener(
            View.OnClickListener {
                materialDatePicker.show(parentFragmentManager, "MATERIAL_DATE_PICKER")
            })
        materialDatePicker.addOnPositiveButtonClickListener {
            binding.departureDate.setText(materialDatePicker.headerText)
        }
    }

    private fun departureDate() {
        val materialDateBuilder: MaterialDatePicker.Builder<*> =
            MaterialDatePicker.Builder.datePicker()

        materialDateBuilder.setTitleText("SELECT A DATE")
        val materialDatePicker = materialDateBuilder.build()
        binding.destinationDate.setOnClickListener(
            View.OnClickListener {
                materialDatePicker.show(parentFragmentManager, "MATERIAL_DATE_PICKER")
            })
        materialDatePicker.addOnPositiveButtonClickListener {
            binding.destinationDate.setText(materialDatePicker.headerText)
        }
    }
}