package com.example.wannahelp.searchScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wannahelp.R
import com.example.wannahelp.common.ToolbarFragment
import com.example.wannahelp.databinding.FragmentSearchScreenBinding
import com.example.wannahelp.searchScreen.searchByEvent.SearchByEventFragment
import com.example.wannahelp.searchScreen.searchByNKO.SearchByNKOFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayoutMediator

class SearchScreenFragment : ToolbarFragment(R.layout.fragment_search_screen) {
    private lateinit var binding: FragmentSearchScreenBinding
    private lateinit var viewModel: SearchScreenViewModel
    private lateinit var vpAdapter: ViewPagerAdapter

    override fun setupToolbar(
        toolbar: androidx.appcompat.widget.Toolbar, actionButton: ImageButton
    ) {
        toolbar.title = getString(R.string.search)
        actionButton.apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.icon_search_24_white)
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
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.searchQuery.onNext(newText.toString())
                    return true
                }
            })
            setOnCloseListener(object : SearchView.OnCloseListener {
                override fun onClose(): Boolean {
                    binding.searchToolbar.visibility = View.GONE
                    requireView().findViewById<MaterialToolbar>(R.id.main_toolbar).visibility =
                        View.VISIBLE
                    return true
                }
            })
        }

        val viewPagerFragmentsList = listOf<Fragment>(
            SearchByEventFragment.newInstance(),
            SearchByNKOFragment.newInstance(),
        )

        vpAdapter = ViewPagerAdapter(this, viewPagerFragmentsList)
        binding.pager.adapter = vpAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.by_events)
                1 -> getString(R.string.by_organization)
                else -> null
            }
        }.attach()
    }
}


//todo сделать все отписки от потоков
//todo сделать сохранение при переворачивании