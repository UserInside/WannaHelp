package com.example.wannahelp.searchScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wannahelp.R
import com.example.wannahelp.common.ToolbarFragment
import com.example.wannahelp.databinding.FragmentSearchScreenBinding
import com.example.wannahelp.searchScreen.searchByEvent.SearchByEventFragment
import com.example.wannahelp.searchScreen.searchByNKO.SearchByNKOFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayoutMediator
import com.jakewharton.rxbinding4.widget.queryTextChanges

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
            actionButton.setOnClickListener {
                toolbar.visibility = View.GONE
                requireView().findViewById<MaterialToolbar>(R.id.search_toolbar).visibility = View.VISIBLE
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

        binding.searchView.queryTextChanges().apply {
            Log.e("WOW", "it changes - > ${this}")
        }.map {

                viewModel.searchFieldText.onNext(it.toString())
            } //todo продолжить тут . или починить или использовать код ниже


//
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean = false
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                Log.e("WOW", "it changes - > ${newText}")
//
//                viewModel.searchFieldText.onNext(newText.toString())
//                return true
//            }
//        })


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