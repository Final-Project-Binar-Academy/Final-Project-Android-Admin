package com.example.final_project_android_admin.ui.flight

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.adapter.ListAirplaneAdapter
import com.example.final_project_android_admin.adapter.ListCityAdapter
import com.example.final_project_android_admin.data.api.response.airplane.DataAirplane
import com.example.final_project_android_admin.data.api.response.airport.DataAirport
import com.example.final_project_android_admin.databinding.FragmentEditFlightBinding
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.AirplaneViewModel
import com.example.final_project_android_admin.viewmodel.AirportViewModel
import com.example.final_project_android_admin.viewmodel.FlightViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditFlightFragment : Fragment() {
    private var _binding: FragmentEditFlightBinding? = null
    private val binding get() = _binding!!
    private lateinit var airportViewModel: AirportViewModel
    private lateinit var airplaneViewModel: AirplaneViewModel
    private lateinit var pref: UserDataStoreManager
    private lateinit var flightViewModel: FlightViewModel
    private var origin_id: Int = 0
    private var destination_id: Int = 0
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

        _binding = FragmentEditFlightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = arguments?.getInt("id")

        if (id != null) {
            flightViewModel.getFlightDetail(id)
        }
        departureDate()
        destinationDate()
        departureHour()
        destinationHour()
        flight()
        getKelas()
        airplane()
        origin()
        destination()
        flightViewModel.flightDetail.observe(viewLifecycleOwner){
            if (it != null){
                var simpleDateFormat = SimpleDateFormat("E, dd LLL")
                var departure : Date? = it.data?.departureDate
                var departureDate = simpleDateFormat.format(departure?.time).toString()

                var arrival : Date? = it.data?.arrivalDate
                var arrivalDate = simpleDateFormat.format(arrival?.time).toString()

                binding.code.setText(it.data?.code)
                binding.departureDate.text = departureDate
                binding.departureHour.text = it.data?.departureTime
                binding.destinationDate.text = arrivalDate
                binding.destinationHour.text = it.data?.arrivalTime
                binding.actvClass.hint = it.data?.classX
                binding.capacity.setText(it.data?.capacity?.toString())
                binding.price.setText(it.data?.price?.toString())

                _airplaneId = it.data?.airplaneId!!
                airplaneViewModel.getAirplaneDetail(_airplaneId)
                airplaneViewModel.airplaneDetail.observe(viewLifecycleOwner){
                    binding.actvAirplane.hint = it?.data?.airplaneName
                }
            }
        }
        binding.btnEdit.setOnClickListener{
            val arrivalDate = binding.destinationDate.text.toString()
            val arrivalTime = binding.destinationHour.text.toString()
            val capacity = binding.capacity.text.toString().toInt()
            val _class = binding.actvClass.text.toString()
            val code = binding.code.text.toString()
            val departureDate = binding.departureDate.text.toString()
            val departureTime = binding.departureHour.text.toString()
            val price = binding.price.text.toString().toInt()

            flightViewModel.getDataStoreToken().observe(viewLifecycleOwner){
                if (id != null){
                    flightViewModel.updateFlight(
                        _airplaneId, arrivalDate, arrivalTime, capacity,
                        _class, code, departureDate, departureTime,
                        origin_id, destination_id, price, "Bearer $it", id
                    )
                }
            }
            findNavController().navigate(R.id.action_editFlightFragment_to_flightFragment)
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
                        origin_id = data?.id!!
                    }
                    actvDestination.threshold = 0
                    actvDestination.setAdapter(cityAdapter)
                    actvDestination.setOnItemClickListener { _, _, position, _ ->
                        val data = cityAdapter.getItem(position)
                        binding.actvDestination.setText("${data?.city} (${data?.cityCode})")
                        destination_id = data?.id!!
                    }
                }
            }
        }
    }

    private fun destinationHour() {
        val materialTimePickerBuilder: MaterialTimePicker.Builder =
            MaterialTimePicker.Builder()

        val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
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

        val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
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

    private fun destination() {
        flightViewModel.flightDetail.observe(viewLifecycleOwner) {
            if (it != null) {
                destination_id = it.data?.flightTo!!
                Log.d(TAG, it.data?.flightTo.toString())
                airportViewModel.getAirportDetail(destination_id)
                airportViewModel.airportDetail.observe(viewLifecycleOwner) {
                    binding.actvDestination.setText(it?.data?.city)
                    Log.d(TAG, it?.data?.city.toString())
                }
            }
        }
    }

    private fun origin() {
        flightViewModel.flightDetail.observe(viewLifecycleOwner) {
            if (it != null) {
                origin_id = it.data?.flightFrom!!
                Log.d(TAG, it.data?.flightFrom.toString())
                airportViewModel.getAirportDetail(origin_id)
                airportViewModel.airportDetail.observe(viewLifecycleOwner) {
                    binding.actvOrigin.setText(it?.data?.city)
                    Log.d(TAG, it?.data?.city.toString())
                }
            }
        }
    }
}