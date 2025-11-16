package com.questmonitor.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.questmonitor.R
import com.questmonitor.ui.views.CircularProgressView
import com.questmonitor.viewmodels.StorageViewModel

class StorageFragment : Fragment() {

    private lateinit var viewModel: StorageViewModel
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var storageProgress: CircularProgressView
    private lateinit var storageUsed: TextView
    private lateinit var storageFree: TextView
    private lateinit var storageTotal: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_storage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[StorageViewModel::class.java]

        initViews(view)
        setupObservers()
        setupListeners()
    }

    private fun initViews(view: View) {
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        storageProgress = view.findViewById(R.id.storage_progress)
        storageUsed = view.findViewById(R.id.storage_used)
        storageFree = view.findViewById(R.id.storage_free)
        storageTotal = view.findViewById(R.id.storage_total)
    }

    private fun setupObservers() {
        viewModel.storageInfo.observe(viewLifecycleOwner) { storage ->
            storageProgress.setProgress(storage.getUsagePercent(), storage.getUsageStatus())
            storageUsed.text = storage.getFormattedUsed()
            storageFree.text = storage.getFormattedFree()
            storageTotal.text = storage.getFormattedTotal()
        }

        viewModel.isRefreshing.observe(viewLifecycleOwner) { isRefreshing ->
            swipeRefresh.isRefreshing = isRefreshing
        }
    }

    private fun setupListeners() {
        swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }
}
