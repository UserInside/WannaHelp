package com.example.wannahelp.presentation.wannaHelpScreen

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wannahelp.R
import com.example.wannahelp.common.ToolbarFragment
import kotlinx.coroutines.launch

class WannaHelpScreenFragment : ToolbarFragment(R.layout.fragment_wanna_help_screen) {
    private val viewModel: WannaHelpViewModel by viewModels()

    override fun setupToolbar(
        toolbar: Toolbar,
        actionButton: ImageButton,
    ) {
        toolbar.title = getString(R.string.wanna_help)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_categories)
        val progressBar = view.findViewById<ProgressBar>(R.id.wannahelp_progressBar)

        lifecycleScope.launch {
            viewModel.screenState.collect { state ->
                when (state) {
                    is WannaHelpScreenState.Progress -> {
                        progressBar.visibility = View.VISIBLE
                    }

                    is WannaHelpScreenState.Done -> {
                        progressBar.visibility = View.GONE
                        val rvAdapter = CategoriesRecyclerViewAdapter(state.categoryList)
                        recyclerView.apply {
                            layoutManager = GridLayoutManager(requireContext(), COLUMNS_AMOUNT)
                            addItemDecoration(
                                GridSpacingItemDecoration(
                                    spanCount = COLUMNS_AMOUNT,
                                    spacing = resources.getDimensionPixelSize(R.dimen.spacing_xs),
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
