package com.example.wannahelp.newsScreen.newsFilterScreen

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
import com.example.wannahelp.databinding.FragmentNewsFilterBinding
import kotlinx.coroutines.launch

class NewsFilterFragment : ToolbarFragment(R.layout.fragment_news_filter, showBackButton = true) {
    private lateinit var binding: FragmentNewsFilterBinding
    private lateinit var viewModel: NewsFilterViewModel

    override fun setupToolbar(
        toolbar: Toolbar,
        actionButton: ImageButton,
    ) {
        toolbar.apply {
            title = getString(R.string.filter)
            setNavigationOnClickListener {
                NavHostFragment.findNavController(this@NewsFilterFragment)
                    .popBackStack()
            }
        }
        actionButton.apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.icon_check_24)
            setOnClickListener {
                lifecycleScope.launch {
                    viewModel.saveChosenCategories()

                    NavHostFragment.findNavController(this@NewsFilterFragment)
                        .popBackStack()
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsFilterBinding.bind(content ?: view)
        viewModel = ViewModelProvider(this@NewsFilterFragment)[NewsFilterViewModel::class]

        val rvAdapter =
            NewsFilterRecyclerViewAdapter { category, isChecked ->
                if (isChecked) {
                    Log.i("DSTORE", "add ${category.toString()} ")
                    viewModel.addNewsItemToFilter(category.toString())
                } else {
                    Log.i("DSTORE", "remove ${category.toString()}")

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
