package com.example.wannahelp.newsScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wannahelp.R
import com.example.wannahelp.common.ToolbarFragment
import com.example.wannahelp.databinding.FragmentNewsScreenBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class NewsScreenFragment : ToolbarFragment(R.layout.fragment_news_screen) {
    private lateinit var viewModel: NewsViewModel
    private lateinit var rvAdapter: NewsRecyclerViewAdapter

    override fun setupToolbar(
        toolbar: Toolbar,
        actionButton: ImageButton
    ) {
        toolbar.title = getString(R.string.news)
        actionButton.apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.icon_filter)
            setOnClickListener {
                NavHostFragment.findNavController(this@NewsScreenFragment)
                    .navigate(R.id.navigateToNewsFilterScreen)
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNewsScreenBinding.bind(content ?: view)
        viewModel = ViewModelProvider(requireActivity())[NewsViewModel::class]
        lifecycleScope.launch {
            viewModel.updateListToShow()
        }

        rvAdapter =
            NewsRecyclerViewAdapter { newsItem ->
                viewModel.addReadItemToSet(newsItem)
                val action = NewsScreenFragmentDirections.navigateToEventDetailsScreen(newsItem)
                NavHostFragment.findNavController(this@NewsScreenFragment).navigate(action)
            }

        binding.recyclerViewNews.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        val exHandler =
            CoroutineExceptionHandler { _, exception ->
                Log.e(TAG, "exception caught by handler -> $exception")
            }

        val scope = lifecycleScope.plus(SupervisorJob() + Dispatchers.Main + exHandler)
        scope.launch {
            viewModel.screenStateFlow.collect { state ->
                when (state) {
                    is NewsState.Progress -> {
                        binding.newsProgressBar.visibility = View.VISIBLE
                    }

                    is NewsState.Done -> {
                        binding.newsProgressBar.visibility = View.GONE
                    }
                }
            }
        }
        scope.launch {
            viewModel.listToShowStateFlow.collect {
                rvAdapter.submitList(it)
            }
        }
    }




    companion object {
        private const val TAG = "NewsScreenFragment"
    }
}
