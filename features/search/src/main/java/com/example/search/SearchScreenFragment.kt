package com.example.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.common.ToolbarFragment
import com.example.search.databinding.FragmentSearchScreenBinding
import com.example.search.searchByEvent.SearchByEventFragment
import com.example.search.searchByNKO.SearchByNKOFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayoutMediator
import com.example.common.R as commonR

class SearchScreenFragment : ToolbarFragment(R.layout.fragment_search_screen) {
    private lateinit var binding: FragmentSearchScreenBinding
    private lateinit var viewModel: SearchScreenViewModel
    private lateinit var vpAdapter: ViewPagerAdapter

    override fun setupToolbar(
        toolbar: Toolbar,
        actionButton: ImageButton,
    ) {
        toolbar.title = getString(commonR.string.search)
        actionButton.apply {
            visibility = View.VISIBLE
            setImageResource(commonR.drawable.icon_search_24_white)
            actionButton.setOnClickListener {
                toolbar.visibility = View.GONE
                requireView().findViewById<MaterialToolbar>(R.id.search_toolbar).visibility =
                    View.VISIBLE
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

        binding.searchView.apply {
            SearchView.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean = false

                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewModel.updateSearchResult(newText.toString())
                        return true
                    }
                },
            )
            SearchView.setOnCloseListener {
                binding.searchToolbar.visibility = View.GONE
//                requireView().findViewById<MaterialToolbar>(R.id.main_toolbar).visibility =
//                    View.VISIBLE
                true
            }
        }

        val viewPagerFragmentsList =
            listOf<Fragment>(
                SearchByEventFragment.newInstance(),
                SearchByNKOFragment.Companion.newInstance(),
            )

        vpAdapter = ViewPagerAdapter(this, viewPagerFragmentsList)
        binding.pager.adapter = vpAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text =
                when (position) {
                    0 -> getString(commonR.string.by_events)
                    1 -> getString(commonR.string.by_organization)
                    else -> null
                }
        }.attach()
    }
}
