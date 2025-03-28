package com.example.wannahelp.searchScreen.searchByEvent

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wannahelp.databinding.FragmentSearchByEventBinding
import com.example.wannahelp.newsScreen.NewsItem
import com.example.wannahelp.searchScreen.SearchRecyclerViewAdapter
import com.example.wannahelp.searchScreen.SearchScreenViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SearchScreenViewModel::class]

        binding.searchPlaceholderGroup.visibility = View.VISIBLE

        val recyclerView = binding.searchByEventRecyclerview.apply {
            visibility = View.GONE
        }


        rvAdapter = SearchRecyclerViewAdapter().apply { submitList(emptyList<NewsItem>()) }

        recyclerView.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.listToShow.observeOn(AndroidSchedulers.mainThread()).subscribe() {
            binding.searchPlaceholderGroup.visibility = View.GONE
            binding.searchByEventRecyclerview.visibility = View.VISIBLE
            rvAdapter.submitList(it)
        }
    }

    companion object {
        fun newInstance(): SearchByEventFragment {
            return SearchByEventFragment()
        }
    }
}