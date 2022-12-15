package com.example.final_project_android_admin.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.final_project_android_admin.utils.SessionManager
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : Fragment() {
    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSplashScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = SessionManager.getToken(requireContext())
        Handler(Looper.myLooper()!!).postDelayed({
            if (!token.isNullOrBlank()) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
            else
                findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
        },1000)

    }
}