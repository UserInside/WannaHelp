package com.example.news.newsFilterScreen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.ToolbarFragment
import com.example.news.R
import com.example.news.databinding.FragmentNewsFilterBinding
import com.example.news.di.NewsComponent
import com.example.news.di.NewsComponentViewModel
import com.example.news.newsFilterScreen.newsFilterRecycler.NewsFilterRecyclerViewAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.common.R as commonR

class NewsFilterFragment : ToolbarFragment(R.layout.fragment_news_filter, showBackButton = true) {
    private lateinit var binding: FragmentNewsFilterBinding
    private val viewModel: NewsFilterViewModel by activityViewModels {
        NewsFilterViewModelFactory(newsComponent)
    }

    @Inject
    lateinit var newsComponent: NewsComponent

    override fun setupToolbar(
        toolbar: Toolbar,
        actionButton: ImageButton,
    ) {
        toolbar.apply {
            title = getString(commonR.string.filter)
            setNavigationOnClickListener {
                NavHostFragment.findNavController(this@NewsFilterFragment)
                    .popBackStack()
            }
        }
        actionButton.apply {
            visibility = View.VISIBLE
            setImageResource(commonR.drawable.icon_check_24)
            setOnClickListener {
                lifecycleScope.launch {
                    viewModel.saveChosenCategories()
                    NavHostFragment.findNavController(this@NewsFilterFragment)
                        .popBackStack()
                }
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
        binding = FragmentNewsFilterBinding.bind(content ?: view)

        val rvAdapter =
            NewsFilterRecyclerViewAdapter { category, isChecked ->
                if (isChecked) {
                    viewModel.addNewsItemToFilter(category.toString())
                } else {
                    viewModel.removeNewsItemFromFilter(category.toString())
                }
            }

        binding.recyclerViewFilterCategories.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        lifecycleScope.launch {
            viewModel.listOfCategoryFiltersToShow.collect {
                rvAdapter.submitList(it)
            }
        }
    }
}
