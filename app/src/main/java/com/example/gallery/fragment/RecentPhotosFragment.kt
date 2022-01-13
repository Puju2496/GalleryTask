package com.example.gallery.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.gallery.databinding.FragmentRecentPhotosBinding
import com.example.gallery.extension.isConnected
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecentPhotosFragment : Fragment(R.layout.fragment_recent_photos) {

    private val viewModel: HomeViewModel by activityViewModels()

    private val galleryAdapter: GalleryAdapter = GalleryAdapter().apply {

    }

    private lateinit var binding: FragmentRecentPhotosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_recent_photos, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layout.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext()))
            adapter = galleryAdapter
        }

        binding.layout.retry.setOnClickListener {
            fetchRecentPhotos()
        }

        fetchRecentPhotos()
        loadStateListener()
    }

    private fun fetchRecentPhotos() {
        lifecycleScope.launchWhenCreated {
            if (requireContext().isConnected) {
                viewModel.getRecentPhotos().collectLatest {
                    binding.layout.retry.visibility = View.GONE
                    galleryAdapter.submitData(it)
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
            galleryAdapter.loadStateFlow.collectLatest {
                when(val state = it.source.refresh) {
                    is LoadState.NotLoading -> {
                        binding.layout.progressBar.visibility = View.GONE
                    }
                    is LoadState.Loading -> {
                        if (galleryAdapter.itemCount == 0) {
                            binding.layout.progressBar.visibility = View.VISIBLE
                        }
                    }
                    is LoadState.Error -> {
                        Snackbar.make(view!!, state.error.message.toString(), Snackbar.LENGTH_LONG)
                            .setAction("RETRY") {
                                fetchRecentPhotos()
                            }.show()
                        binding.layout.progressBar.visibility = View.GONE
                    }
                    else -> {

                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = RecentPhotosFragment()
    }
}