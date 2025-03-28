package com.example.wannahelp.searchScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.SearchView
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wannahelp.R
import com.example.wannahelp.common.ToolbarFragment
import com.example.wannahelp.databinding.FragmentSearchScreenBinding
import com.example.wannahelp.searchScreen.searchByEvent.SearchByEventFragment
import com.example.wannahelp.searchScreen.searchByNKO.SearchByNKOFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.jakewharton.rxbinding4.widget.queryTextChanges
import io.reactivex.rxjava3.core.Scheduler

class SearchScreenFragment : ToolbarFragment(R.layout.fragment_search_screen) {
    private lateinit var binding: FragmentSearchScreenBinding
    private lateinit var viewModel: SearchScreenViewModel
    private lateinit var vpAdapter: ViewPagerAdapter

//    val viewModel: SearchScreenViewModel by viewModels() //move to companion ?


    override fun setupToolbar(
        toolbar: androidx.appcompat.widget.Toolbar,
        actionButton: ImageButton
    ) {
        toolbar.title = getString(R.string.search)
        actionButton.apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.icon_search_24)
            setOnClickListener {
                visibility = View.GONE
//                binding.searchViewField.visibility = View.VISIBLE
                toolbar.removeAllViews()
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.search_toolbar, toolbar, true)
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchScreenBinding.bind(content ?: view)
        viewModel = ViewModelProvider(requireActivity())[SearchScreenViewModel::class]
        val searchView = view.findViewById<SearchView>(R.id.search_view)
        if (savedInstanceState != null) {
//            searchView.setQuery(viewModel.searchFieldText, true)
            //остановился тут. падает при смене экрана и затем назад на этот.

        }

//        searchView.queryTextChanges().map {
//            viewModel.searchFieldText.onNext(it.toString())
//        }

        val viewPagerFragmentsList =
            listOf<Fragment>(
                SearchByEventFragment.newInstance(),
                SearchByNKOFragment.newInstance(),
            )

        vpAdapter = ViewPagerAdapter(this, viewPagerFragmentsList)
        binding.pager.adapter = vpAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text =
                when (position) {
                    0 -> getString(R.string.by_events)
                    1 -> getString(R.string.by_organization)
                    else -> null
                }
        }.attach()
    }
}


//todo сделать все отписки от потоков