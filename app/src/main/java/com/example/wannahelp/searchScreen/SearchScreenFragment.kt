package com.example.wannahelp.searchScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.wannahelp.R
import com.example.wannahelp.databinding.FragmentSearchScreenBinding
import com.google.android.material.tabs.TabLayoutMediator

class SearchScreenFragment : Fragment() {
    private lateinit var binding: FragmentSearchScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        (activity as AppCompatActivity).supportActionBar?.hide()

        binding = FragmentSearchScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val searchResultList =
            arrayListOf(
                "Фонд 1",
                "Фонд 2",
                "Фонд 3",
                "Фонд 4",
            )

        val viewPagerFragmentsList =
            listOf(
                SearchByEventFragment.newInstance(),
                SearchByNKOFragment.newInstance(searchResultList),
            )

        val tabLayout = binding.tabLayout
        val viewPager = binding.pager
        val viewPagerAdapter = ViewPagerAdapter(this, viewPagerFragmentsList)
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text =
                when (position) {
                    0 -> getString(R.string.by_events)
                    1 -> getString(R.string.by_organization)
                    else -> null
                }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}
