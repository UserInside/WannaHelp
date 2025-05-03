package com.example.wannahelp.presentation.searchScreen.searchByNKO

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wannahelp.databinding.FragmentSearchByNkoBinding
import com.example.wannahelp.presentation.searchScreen.SearchRecyclerViewAdapter

class SearchByNKOFragment : Fragment() {
    private lateinit var binding: FragmentSearchByNkoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchByNkoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val searchResultList = arguments?.getStringArrayList(SEARCH_RESULT)
        val recyclerViewAdapter = searchResultList?.let { SearchRecyclerViewAdapter() }
        val recyclerView = binding.recyclerViewSearchResults
        recyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    companion object {
        const val SEARCH_RESULT = "search result"

        fun newInstance(): SearchByNKOFragment {
            return SearchByNKOFragment()
        }
    }
}
