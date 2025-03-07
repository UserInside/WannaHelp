package com.example.wannahelp.searchScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wannahelp.databinding.FragmentSearchByNKOBinding

class SearchByNKOFragment : Fragment() {
    private lateinit var binding: FragmentSearchByNKOBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchByNKOBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val searchResultList = arguments?.getStringArrayList(SEARCH_RESULT)
        val recyclerViewAdapter = searchResultList?.let { SearchRecyclerViewAdapter(it) }
        val recyclerView = binding.recyclerViewSearchResults
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {
        const val SEARCH_RESULT = "search result"

        fun newInstance(searchResult: ArrayList<String>): SearchByNKOFragment {
            return SearchByNKOFragment().apply {
                arguments =
                    Bundle().apply {
                        putStringArrayList(SEARCH_RESULT, searchResult)
                    }
            }
        }
    }
}
