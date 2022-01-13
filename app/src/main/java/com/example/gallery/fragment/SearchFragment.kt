package com.example.gallery.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gallery.itemdecoration.DividerItemDecoration
import com.example.gallery.adapter.GalleryAdapter
import com.example.gallery.viewmodel.HomeViewModel
import com.example.gallery.R
import com.example.gallery.databinding.FragmentSearchBinding
import com.example.gallery.extension.isConnected
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val viewModel: HomeViewModel by activityViewModels()

    private val searchAdapter = GalleryAdapter()

    private var searchedText: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layout.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext()))
            adapter = searchAdapter
        }

        binding.layout.retry.setOnClickListener {
            fetchSearchResults(searchedText)
        }

        loadStateListener()

        binding.searchBar.setOnQueryTextListener(searchSubmitListener)
        binding.searchBar.requestFocus()
        showKeyBoard()
    }

    private fun fetchSearchResults(text: String) {
        lifecycleScope.launchWhenCreated {
            if (requireContext().isConnected) {
                viewModel.getSearchedPhotos(text).collectLatest {
                    binding.resultTitle.visibility = View.VISIBLE
                    binding.layout.retry.visibility = View.GONE
                    searchAdapter.submitData(it)
                }
            } else {
                Snackbar.make(view!!, "Connect to wifi or mobile data", Snackbar.LENGTH_INDEFINITE)
                    .setAction("CONNECT TO NETWORK") {
                        showDialog()
                    }.show()
            }
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Connect to wifi")
            .setPositiveButton("CONNECT TO WIFI") { _, _ ->
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                binding.layout.retry.visibility = View.VISIBLE
            }
            .create()
            .show()
    }

    private fun loadStateListener() {
        lifecycleScope.launch {
            searchAdapter.loadStateFlow.collectLatest {
                when(val state = it.source.refresh) {
                    is LoadState.NotLoading -> {
                        binding.layout.progressBar.visibility = View.GONE
                    }
                    is LoadState.Loading -> {
                        if (searchAdapter.itemCount == 0) {
                            binding.layout.progressBar.visibility = View.VISIBLE
                        }
                    }
                    is LoadState.Error -> {
                        Snackbar.make(view!!, state.error.message.toString(), Snackbar.LENGTH_LONG)
                            .setAction("RETRY") {
                                fetchSearchResults(searchedText)
                            }.show()
                        binding.layout.progressBar.visibility = View.GONE
                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun showKeyBoard() {
        val inputManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun hideKeyBoard() {
        val inputManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }

    private val searchSubmitListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(text: String?): Boolean {
            hideKeyBoard()

            text?.let {
                searchedText = text
                fetchSearchResults(it)
            }
            return true
        }

        override fun onQueryTextChange(text: String?): Boolean {
            return false
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}