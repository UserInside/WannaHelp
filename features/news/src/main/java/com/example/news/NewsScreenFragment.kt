package com.example.news

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.ToolbarFragment
import com.example.common.extensions.navigate
import com.example.news.databinding.FragmentNewsScreenBinding
import com.example.news.di.NewsComponent
import com.example.news.di.NewsComponentViewModel
import com.example.news.newsRecycler.NewsRecyclerViewAdapter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject
import kotlin.jvm.java
import com.example.common.R as commonR

class NewsScreenFragment : ToolbarFragment(R.layout.fragment_news_screen) {

    @Inject
    lateinit var navigator: NewsNavigator

    private val viewModel: NewsViewModel by activityViewModels()
    private lateinit var rvAdapter: NewsRecyclerViewAdapter

    override fun setupToolbar(
        toolbar: Toolbar,
        actionButton: ImageButton,
    ) {
        toolbar.title = getString(commonR.string.news)
        actionButton.apply {
            visibility = View.VISIBLE
            setImageResource(commonR.drawable.icon_filter)
            setOnClickListener {
//                   navigate(navigator.toSomeWhere)
            }
        }
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this)[NewsComponentViewModel::class.java].newsComponent.inject(this)
        super.onAttach(context)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
//        component =

        val binding = FragmentNewsScreenBinding.bind(content ?: view)
        lifecycleScope.launch {
//            viewModel.loadNewsFromDB()
        }

        rvAdapter =
            NewsRecyclerViewAdapter { newsItem ->
//                viewModel.markNewsItemAsRead(newsItem.id)
//                val action = NewsScreenFragmentDirections.navigateToEventDetailsScreen(newsItem)
//                NavHostFragment.findNavController(this@NewsScreenFragment).navigate(action)
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
