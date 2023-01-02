package com.example.final_project_android_admin.ui.profile

import android.content.ContentResolver
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.databinding.FragmentEditProfileBinding
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class EditProfileFragment : DialogFragment() {
    private var image_uri: Uri? = null
    private var imageFile: File? = null
    private var imageMultiPart: MultipartBody.Part? = null
    private lateinit var viewModel: ProfileViewModel
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var pref: UserDataStoreManager


    override fun onStart() {
        super.onStart()
        dialog!!.window
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        pref = UserDataStoreManager(requireContext())
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDataStoreToken().observe(viewLifecycleOwner) {
            viewModel.getUserProfile("Bearer $it")
        }
        binding.btnUpdate.setOnClickListener {

            val username = binding.etFirstName.text.toString()
            val fName = binding.etFirstName.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            val lName = binding.etLastName.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            val address = binding.etAddress.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            val phone = binding.etPhone.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            viewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                viewModel.updateUser(fName, lName, address,phone, imageMultiPart!!,"Bearer $it")
            }
//            viewModel.saveImage(image_uri.toString())

            viewModel.saveUsername(username)
            Toast.makeText(requireContext(), "Update Success", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
        }
        viewModel.user.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    etFirstName.setText(it.data?.firstName)
                    etLastName.setText(it.data?.lastName)
                    etAddress.setText(it.data?.address)
                    etPhone.setText(it.data?.phoneNumber)
                }
            }
        }
        openGallery()
    }


    fun openGallery() {
        binding.btnSelectImage.setOnClickListener {
            getContent.launch("image/*")
        }
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val contentResolver: ContentResolver = requireContext().contentResolver
                val type = contentResolver.getType(it)
                image_uri = it

                val fileNameimg = "${System.currentTimeMillis()}.png"
                binding.ivEditImage.setImageURI(it)
                Toast.makeText(requireContext(), "$image_uri", Toast.LENGTH_SHORT).show()

                val tempFile = File.createTempFile("and1-", fileNameimg, null)
                imageFile = tempFile
                val inputstream = contentResolver.openInputStream(uri)
                tempFile.outputStream().use { result ->
                    inputstream?.copyTo(result)
                }
                val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
                imageMultiPart =
                    MultipartBody.Part.createFormData("image", tempFile.name, requestBody)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}