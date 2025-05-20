package com.example.categories

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.common.R as commonR
import com.example.common.ToolbarFragment
import com.example.data.di.DataModule
import com.example.domain.di.DomainModule
import com.example.categories.di.DaggerCategoriesComponent
import com.example.categories.di.CategoriesComponent
import com.example.categories.recycler.CategoriesRecyclerViewAdapter
import com.example.categories.recycler.GridSpacingItemDecoration
import kotlinx.coroutines.launch

class CategoriesScreenFragment : ToolbarFragment(R.layout.fragment_categories_screen) {
    private lateinit var viewModel: CategoriesViewModel

    private val vmFactory: CategoriesViewModelFactory by lazy {
        CategoriesViewModelFactory(component)
    }

    private val component: CategoriesComponent by lazy {
        DaggerCategoriesComponent.builder()
            .dataModule(DataModule(requireContext()))
            .domainModule(DomainModule())
            .build()
    }

    override fun setupToolbar(
        toolbar: Toolbar,
        actionButton: ImageButton,
    ) {
        toolbar.title = getString(commonR.string.wanna_help)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_categories)
        val progressBar = view.findViewById<ProgressBar>(R.id.categories_progressBar)

        viewModel = ViewModelProvider(this, vmFactory)[CategoriesViewModel::class]

        lifecycleScope.launch {
            viewModel.screenState.collect { state ->
                when (state) {
                    is CategoriesScreenState.Progress -> {
                        progressBar.visibility = View.VISIBLE
                    }

                    is CategoriesScreenState.Done -> {
                        progressBar.visibility = View.GONE
                        val rvAdapter = CategoriesRecyclerViewAdapter(state.categoryList)
                        recyclerView.apply {
                            layoutManager = GridLayoutManager(requireContext(), COLUMNS_AMOUNT)
                            addItemDecoration(
                                GridSpacingItemDecoration(
                                    spanCount = COLUMNS_AMOUNT,
                                    spacing = resources.getDimensionPixelSize(commonR.dimen.spacing_xs),
                                    includeEdge = true,
                                ),
                            )
                            adapter = rvAdapter
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val COLUMNS_AMOUNT = 2
    }
}
