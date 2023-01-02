package com.example.final_project_android_admin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.response.AuthResponse
import com.example.final_project_android_admin.data.api.response.BaseResponse
import com.example.final_project_android_admin.databinding.FragmentLoginBinding
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.LoginViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private lateinit var analytics: FirebaseAnalytics
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel
    private lateinit var pref: UserDataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        pref = UserDataStoreManager(requireContext())
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        analytics = Firebase.analytics

        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val token = SessionManager.getToken(requireContext())
//        if (!token.isNullOrBlank()) {
//            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//        }
        binding.forgetPass.setOnClickListener {
            throw RuntimeException("App Crashed")
        }

        viewModel.loginResult.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResponse.Success -> {
                    processLogin(it.data)
                }
                is BaseResponse.Error -> {
                    processError(it.msg)
                }
                else -> {
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            val mail = binding.email.text.toString()
            val pwd = binding.password.text.toString()
            viewModel.loginUser(email = mail, pwd = pwd)
        }

    }

    private fun processLogin(data: AuthResponse?) {
        showToast("Success:" + data?.message)
        if (data?.data?.accessToken != null) {
            viewModel.saveIsLoginStatus(true)
            viewModel.saveToken(data?.data?.accessToken.toString())
            viewModel.saveUsername(data.data?.name.toString())
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

//        data?.data?.token?.let { SessionManager.saveAuthToken(requireContext(), it) }

    }
    override fun onStart() {
        super.onStart()
        viewModel.getDataStoreIsLogin().observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }

    private fun processError(msg: String?) {
        showToast("Error:$msg")
    }

    private fun showToast(msg: String) {
        Toast.makeText(
            requireContext(),
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

}