package com.example.wannahelp.wannaHelpScreen

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.wannahelp.R
import com.example.wannahelp.backgroundWork.workmanager.ReadCategoryFileWorker
import com.example.wannahelp.common.ToolbarFragment

class WannaHelpScreenFragment : ToolbarFragment(R.layout.fragment_wanna_help_screen) {
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
        if (savedInstanceState == null) startReadFileWorkManager()

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_categories)
        val progressBar = view.findViewById<ProgressBar>(R.id.wannahelp_progressBar)

        ReadCategoryFileWorker.apply {
            progress.observe(viewLifecycleOwner) {
                progressBar.progress = it
            }
            resultLiveData.observe(viewLifecycleOwner) {
                progressBar.visibility = View.GONE
                val rvAdapter = CategoriesRecyclerViewAdapter(it)
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

    private fun startReadFileWorkManager() {
        val progressBar = view?.findViewById<ProgressBar>(R.id.wannahelp_progressBar)
        progressBar?.visibility = View.VISIBLE
        val parseFileWorkRequest =
            OneTimeWorkRequestBuilder<ReadCategoryFileWorker>().setInputData(
                Data.Builder().putString(CATEGORIES_FILE_NAME_KEY, CATEGORIES_FILE_NAME).build(),
            ).build()

        WorkManager.getInstance(requireContext()).enqueue(parseFileWorkRequest)
    }

    companion object {
        private const val CATEGORIES_FILE_NAME = "categories.json"
        const val CATEGORIES_FILE_NAME_KEY = "categoryFileName"
        private const val COLUMNS_AMOUNT = 2
    }
}
