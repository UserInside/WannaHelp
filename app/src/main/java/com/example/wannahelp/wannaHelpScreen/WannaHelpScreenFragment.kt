package com.example.wannahelp.wannaHelpScreen

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wannahelp.R
import com.example.wannahelp.common.ToolbarFragment
import com.example.wannahelp.common.extentions.JsonParser

private const val CATEGORIES_FILE_NAME = "categories.json"

class WannaHelpScreenFragment : ToolbarFragment(R.layout.fragment_wanna_help_screen) {
    override fun setupToolbar(toolbar: Toolbar) {
        toolbar.title = getString(R.string.wanna_help)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesItemList =
            JsonParser(requireContext(), CATEGORIES_FILE_NAME).parseToList<CategoryItem>()

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_categories)

        val adapter = CategoriesRecyclerViewAdapter(categoriesItemList)
        val spanCount = 2
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)

        val spacing = resources.getDimensionPixelSize(R.dimen.spacing_xs)
        val includeEdge = true
        recyclerView.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount,
                spacing,
                includeEdge,
            ),
        )

        recyclerView.adapter = adapter
    }
}
