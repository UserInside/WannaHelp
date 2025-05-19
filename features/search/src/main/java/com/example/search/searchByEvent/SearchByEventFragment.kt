package com.example.search.searchByEvent

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.search.recycler.SearchRecyclerViewAdapter
import com.example.search.SearchResult
import com.example.search.SearchScreenViewModel
import com.example.search.databinding.FragmentSearchByEventBinding
import kotlinx.coroutines.launch

class SearchByEventFragment : Fragment() {
    private lateinit var viewModel: SearchScreenViewModel
    private lateinit var rvAdapter: SearchRecyclerViewAdapter
    private lateinit var binding: FragmentSearchByEventBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchByEventBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SearchScreenViewModel::class]

        rvAdapter =
            SearchRecyclerViewAdapter()

        binding.searchByEventRecyclerview.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        lifecycleScope.launch {
            viewModel.searchResultStateFlow.collect { result ->
                when (result) {
                    is SearchResult.NoInputMade -> {
                        binding.searchPlaceholderGroup.visibility = View.VISIBLE
                        binding.searchByEventRecyclerview.visibility = View.GONE
                    }

                    is SearchResult.ResultToShow -> {
                        binding.searchPlaceholderGroup.visibility = View.GONE
                        binding.searchByEventRecyclerview.visibility = View.VISIBLE
                        rvAdapter.submitList(result.listToShow)
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(): SearchByEventFragment {
            return SearchByEventFragment()
        }
    }
}
