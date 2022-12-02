package com.example.final_project_android_admin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.final_project_android_admin.ui.response.AuthResponse
import com.example.final_project_android_admin.ui.response.BaseResponse
import com.example.final_project_android_admin.ui.utils.SessionManager
import com.example.final_project_android_admin.ui.viewmodel.LoginViewModel
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = SessionManager.getToken(requireContext())
        if (!token.isNullOrBlank()) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
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

        binding.signUp.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    fun processLogin(data: AuthResponse?) {
        showToast("Success:" + data?.message)
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        data?.data?.token?.let { SessionManager.saveAuthToken(requireContext(), it) }

    }

    fun processError(msg: String?) {
        showToast("Error:" + msg)
    }

    fun showToast(msg: String) {
        Toast.makeText(
            requireContext(),
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

}