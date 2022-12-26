package com.example.final_project_android_admin.ui.company

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.databinding.FragmentEditCompanyBinding
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.CompanyViewModel
import com.example.final_project_android_admin.viewmodel.factory.CompanyViewModelFactory
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class EditCompanyFragment : Fragment() {
    private var _binding: FragmentEditCompanyBinding? = null
    private val binding get() = _binding!!
    private lateinit var companyViewModel: CompanyViewModel
    private lateinit var pref: UserDataStoreManager

    private var image_uri: Uri? = null
    private var imageFile: File? = null
    private var imageMultiPart: MultipartBody.Part? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        pref = UserDataStoreManager(requireContext())
        companyViewModel = ViewModelProvider(
            this, CompanyViewModelFactory(ApiHelper(ApiClient.instance), pref)
        )[CompanyViewModel::class.java]
        _binding = FragmentEditCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = arguments?.getInt("id")

        if (id != null) {
            companyViewModel.getCompanyDetail(id)
        }
        companyViewModel.companyDetail.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    Glide.with(requireContext())
                        .load(it.data?.companyImage)
                        .circleCrop()
                        .into(binding.imgFilm)
                    txtCompany.setText(it.data?.companyName.toString())
                }
            }
        }

        binding.btnSave.setOnClickListener {
            val companyName = binding.txtCompany.text.toString().trim()
                .toRequestBody("multipart/form-data".toMediaType())
            companyViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                if (id != null) {
                    companyViewModel.updateCompany(companyName = companyName, image = imageMultiPart!!, "Bearer $it", id)
                    Snackbar.make(binding.root, "Airport Berhasil Diupdate", Snackbar.LENGTH_SHORT)
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
            findNavController().navigate(R.id.action_editCompanyFragment_to_companyFragment)
        }
        openGallery()
        super.onViewCreated(view, savedInstanceState)
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
                binding.imgFilm.setImageURI(it)
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