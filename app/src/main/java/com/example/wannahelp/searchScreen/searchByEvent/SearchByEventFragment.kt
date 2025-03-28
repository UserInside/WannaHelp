package com.example.wannahelp.searchScreen.searchByEvent

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wannahelp.R
import com.example.wannahelp.searchScreen.SearchRecyclerViewAdapter
import com.example.wannahelp.searchScreen.SearchScreenViewModel
import com.example.wannahelp.searchScreen.searchByNKO.SearchByNKOFragment

class SearchByEventFragment : Fragment() {
    private lateinit var viewModel: SearchScreenViewModel
    private lateinit var rvAdapter: SearchRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[SearchScreenViewModel::class]
        val layout = R.layout.fragment_search_by_event
//            if (viewModel.searchFieldText == "") R.layout.fragment_search_by_event_placeholder
//            else R.layout.fragment_search_by_event

        return inflater.inflate(layout, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SearchScreenViewModel::class]

        val recyclerView = view.findViewById<RecyclerView>(R.id.search_by_event_recyclerview)

        rvAdapter = SearchRecyclerViewAdapter().apply { submitList(emptyList<String>()) }

        recyclerView.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.listToShow.subscribe {
            rvAdapter.submitList(it)
        }


    }

    companion object {
        const val SEARCH_RESULT = "search result"

        //        fun newInstance(searchResult: ArrayList<String>): SearchByEventFragment {
//            return SearchByEventFragment().apply {
//                arguments =
//                    Bundle().apply {
//                        putStringArrayList(SEARCH_RESULT, searchResult)
//                    }
//            }
//        }
        fun newInstance(): SearchByEventFragment {
            return SearchByEventFragment()
        }
    }
}