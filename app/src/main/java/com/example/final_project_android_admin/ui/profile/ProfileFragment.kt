package com.example.final_project_android_admin.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.databinding.FragmentProfileBinding
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.ProfileViewModel
import com.example.final_project_android_admin.viewmodel.factory.ProfileViewModelFactory

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var pref: UserDataStoreManager

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        pref = UserDataStoreManager(requireContext())
        viewModel = ViewModelProvider(
            this, ProfileViewModelFactory(pref)
        )[ProfileViewModel::class.java]
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
        sideBar()

        viewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            viewModel.getUserProfile("Bearer $it")
        }

        viewModel.user.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    username.text = it.data?.firstName.toString() + " " + it.data?.lastName.toString()
                    etFirstName.setText(it.data?.firstName.toString())
                    etLastName.setText(it.data?.lastName.toString())
                    etAddress.setText(it.data?.address.toString())
                    etPhone.setText(it.data?.phoneNumber.toString())
                    Glide.with(requireContext())
                        .load(it.data?.avatar)
                        .circleCrop()
                        .into(binding.profileImage)

                }
            }
        }

        binding.btnEdit.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        logout()
    }


    private fun logout() {
        binding.btnLogout.setOnClickListener {
            viewModel.removeIsLoginStatus()
            viewModel.removeId()
            viewModel.removeUsername()
            viewModel.removeToken()
            viewModel.getDataStoreIsLogin().observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                R.id.profile -> {
                    findNavController().navigate(R.id.profileFragment)
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


}