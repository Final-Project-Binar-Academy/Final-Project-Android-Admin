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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.adapter.AirportAdapter
import com.example.final_project_android_admin.data.api.response.airplane.DataAirplane
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
        binding.btnBack.setOnClickListener(){
            findNavController().navigate(R.id.homeFragment)
        }

        val adapter = AirportAdapter(this)

        binding.apply {
            airportViewModel.getDataAirport()
            airportViewModel.getLiveDataAirport().observe(viewLifecycleOwner){
                if (it != null){
                    adapter.setData(it.data as List<DataAirport>)
                    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                        override fun onMove(
                            recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder
                        ): Boolean {
                            return false
                        }

                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                            val deletedCourse: DataAirport = it.data!!.get(viewHolder.adapterPosition)

                            airportViewModel.getDataStoreToken().observe(viewLifecycleOwner){
                                builder.setTitle("Warning!")
                                    .setMessage("Ingin menghapus Airport ini?")
                                    .setCancelable(true)
                                    .setPositiveButton("Ya"){ _, _ ->
                                        deletedCourse.id?.let { it1 ->
                                            airportViewModel.deleteAirport("Bearer $it",
                                                it1
                                            )
                                        }
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
                            adapter.notifyItemRemoved(viewHolder.adapterPosition)
                            adapter.notifyItemChanged(viewHolder.adapterPosition)
                        }
                    }).attachToRecyclerView(binding.rvPost)

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

        binding.btnAdd.setOnClickListener{
            findNavController().navigate(R.id.action_airportFragment_to_addAirportFragment)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun edit(id: Int) {
        val bund = Bundle()
        bund.putInt("id", id)
        findNavController().navigate(R.id.action_airportFragment_to_editAirportFragment, bund)
    }
}