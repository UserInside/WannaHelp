package com.example.wannahelp.wannaHelpScreen

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wannahelp.R
import com.example.wannahelp.common.ToolbarFragment
import com.example.wannahelp.common.extentions.JsonParser

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

        val categoriesItemList =
            JsonParser(requireContext(), CATEGORIES_FILE_NAME).parseToList<CategoryItem>()

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_categories)

        val adapter = CategoriesRecyclerViewAdapter(categoriesItemList)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), COLUMNS_AMOUNT)

        val spacing = resources.getDimensionPixelSize(R.dimen.spacing_xs)
        recyclerView.addItemDecoration(
            GridSpacingItemDecoration(COLUMNS_AMOUNT, spacing, true),
        )

        recyclerView.adapter = adapter
    }

    private companion object {
        const val CATEGORIES_FILE_NAME = "categories.json"
        const val COLUMNS_AMOUNT = 2
    }
}
