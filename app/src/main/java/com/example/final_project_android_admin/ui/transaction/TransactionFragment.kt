package com.example.final_project_android_admin.ui.transaction

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.example.final_project_android_admin.adapter.TransactionAdapter
import com.example.final_project_android_admin.data.api.response.transaction.DataTransaction
import com.example.final_project_android_admin.databinding.FragmentTransactionBinding
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.LoginViewModel
import com.example.final_project_android_admin.viewmodel.TransactionViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TransactionFragment : Fragment(), TransactionAdapter.ListTransactionInterface {
    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!

    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var viewModel: LoginViewModel
    private lateinit var pref: UserDataStoreManager
    private var isSuccess = false
    private var isPending = false
    private var isCanceled = false
    private lateinit var builder: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        pref = UserDataStoreManager(requireContext())
        transactionViewModel = ViewModelProvider(this)[TransactionViewModel::class.java]
        builder = AlertDialog.Builder(context)

        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnBack.setOnClickListener(){
            findNavController().navigate(R.id.homeFragment)
        }
        topBar()
        allTransaction()

        binding.btnSuccess.setOnClickListener {
            if (!isSuccess) {
                isSuccess = true
                rvPost("success")
                binding.btnSuccess.setBackgroundColor(resources.getColor(R.color.birutua))
                binding.btnSuccess.setTextColor(resources.getColor(R.color.text_blue))
                binding.btnCanceled.setBackgroundColor(Color.WHITE)
                binding.btnCanceled.setTextColor(Color.BLACK)
                binding.btnPending.setBackgroundColor(Color.WHITE)
                binding.btnPending.setTextColor(Color.BLACK)
            } else {
                isSuccess = false
                isPending = false
                isCanceled = false
                allTransaction()
                binding.btnSuccess.setBackgroundColor(Color.WHITE)
                binding.btnSuccess.setTextColor(Color.BLACK)
            }
        }

        binding.btnPending.setOnClickListener {
            if (!isPending) {
                isPending = true
                rvPost("pending")
                binding.btnPending.setBackgroundColor(resources.getColor(R.color.birutua))
                binding.btnPending.setTextColor(resources.getColor(R.color.text_blue))
                binding.btnCanceled.setBackgroundColor(Color.WHITE)
                binding.btnCanceled.setTextColor(Color.BLACK)
                binding.btnSuccess.setBackgroundColor(Color.WHITE)
                binding.btnSuccess.setTextColor(Color.BLACK)
            } else {
                isSuccess = false
                isPending = false
                isCanceled = false
                allTransaction()
                binding.btnPending.setBackgroundColor(Color.WHITE)
                binding.btnPending.setTextColor(Color.BLACK)
            }
        }

        binding.btnCanceled.setOnClickListener {
            if (!isCanceled) {
                isCanceled = true
                rvPost("canceled")
                binding.btnCanceled.setBackgroundColor(resources.getColor(R.color.birutua))
                binding.btnCanceled.setTextColor(resources.getColor(R.color.text_blue))
                binding.btnSuccess.setBackgroundColor(Color.WHITE)
                binding.btnSuccess.setTextColor(Color.BLACK)
                binding.btnPending.setBackgroundColor(Color.WHITE)
                binding.btnPending.setTextColor(Color.BLACK)
            } else {

                isSuccess = false
                isPending = false
                isCanceled = false
                allTransaction()
                binding.btnCanceled.setBackgroundColor(Color.WHITE)
                binding.btnCanceled.setTextColor(Color.BLACK)
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun rvPost(status: String) {
        val adapter = TransactionAdapter(this)
        binding.apply {
            transactionViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                transactionViewModel.getDataTransactionFilter("Bearer $it", status)
            }
            transactionViewModel.getTransactionFilter().observe(viewLifecycleOwner) {
                if (it != null) {
                    adapter.setData(it.data as List<DataTransaction>)
                } else {
                    Snackbar.make(binding.root, "Data Gagal Dimuat", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.button
                            )
                        )
                        .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        .show()
                }
            }
            rvPost.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvPost.adapter = adapter
        }
    }


    private fun allTransaction() {
        val adapter = TransactionAdapter(this)
        binding.apply {
            transactionViewModel.getDataStoreToken().observe(viewLifecycleOwner) {
                transactionViewModel.getDataTransaction("Bearer $it")
                val TAG = "token"
                Log.d(TAG, it)
            }
            transactionViewModel.getLiveDataTransaction().observe(viewLifecycleOwner) {
                if (it != null) {
                    adapter.setData(it.data as List<DataTransaction>)
                    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                        override fun onMove(
                            recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder
                        ): Boolean {
                            return false
                        }

                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                            val deletedCourse: DataTransaction = it.data!![viewHolder.adapterPosition]

                            transactionViewModel.getDataStoreToken().observe(viewLifecycleOwner){
                                builder.setTitle("Warning!")
                                    .setMessage("Ingin menghapus Transaction ini?")
                                    .setCancelable(true)
                                    .setPositiveButton("Ya"){ _, _ ->
                                        deletedCourse.id_transaction?.let { it1 ->
                                            transactionViewModel.deleteTransaction("Bearer $it",
                                                it1
                                            )
                                        }
                                        Snackbar.make(binding.root, "Transaction Berhasil Dihapus", Snackbar.LENGTH_SHORT)
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

                } else {
                    Snackbar.make(binding.root, "Data Gagal Dimuat", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.button
                            )
                        )
                        .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        .show()
                }
            }
            rvPost.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvPost.adapter = adapter
        }
    }

    private fun topBar() {

    }

    override fun edit(id: Int) {
        val bund = Bundle()
        bund.putInt("id", id)
        findNavController().navigate(R.id.action_transactionFragment_to_editTransactionFragment, bund)
    }

}