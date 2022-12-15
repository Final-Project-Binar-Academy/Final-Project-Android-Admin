package com.example.final_project_android_admin.ui.airplane

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.databinding.FragmentAddFlightBinding
import com.example.final_project_android_admin.databinding.FragmentEditAirplaneBinding
import com.example.final_project_android_admin.databinding.FragmentEditFlightBinding

class EditAirplaneFragment : Fragment() {
    private var _binding: FragmentEditAirplaneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditAirplaneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnEdit.setOnClickListener{
            findNavController().navigate(R.id.action_addAirplaneFragment_to_airplaneFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}