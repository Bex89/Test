package com.questmonitor.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.chip.ChipGroup
import com.questmonitor.R
import com.questmonitor.ui.adapters.AppsAdapter
import com.questmonitor.viewmodels.AppsViewModel
import com.questmonitor.viewmodels.FilterType

/**
 * Fragment for displaying running and installed apps
 */
class AppsFragment : Fragment() {

    private lateinit var viewModel: AppsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var chipGroup: ChipGroup
    private lateinit var adapter: AppsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_apps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[AppsViewModel::class.java]

        initViews(view)
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recycler_apps)
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        chipGroup = view.findViewById(R.id.chip_group_filter)
    }

    private fun setupRecyclerView() {
        adapter = AppsAdapter { appInfo ->
            viewModel.stopApp(appInfo.packageName)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.apps.observe(viewLifecycleOwner) { apps ->
            adapter.submitList(apps)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            swipeRefresh.isRefreshing = isLoading
        }
    }

    private fun setupListeners() {
        swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            when (checkedIds.firstOrNull()) {
                R.id.chip_all -> viewModel.setFilter(FilterType.ALL)
                R.id.chip_running -> viewModel.setFilter(FilterType.RUNNING)
                R.id.chip_user -> viewModel.setFilter(FilterType.USER)
                R.id.chip_system -> viewModel.setFilter(FilterType.SYSTEM)
            }
        }
    }
}
