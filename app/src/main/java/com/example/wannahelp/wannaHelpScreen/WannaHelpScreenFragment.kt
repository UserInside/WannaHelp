package com.example.wannahelp.wannaHelpScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wannahelp.R
import com.example.wannahelp.databinding.FragmentWannaHelpScreenBinding

class WannaHelpScreenFragment : Fragment() {
    private lateinit var binding: FragmentWannaHelpScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWannaHelpScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesList =
            listOf(
                CategoryCard(R.drawable.icon_cat_kids, resources.getString(R.string.tv_cat_kids)),
                CategoryCard(R.drawable.icon_cat_adults, resources.getString(R.string.tv_cat_adults)),
                CategoryCard(R.drawable.icon_cat_aged, resources.getString(R.string.tv_cat_aged)),
                CategoryCard(R.drawable.icon_cat_animals, resources.getString(R.string.tv_cat_animals)),
                CategoryCard(R.drawable.icon_cat_events, resources.getString(R.string.tv_cat_events)),
            )

        val recyclerView = binding.recyclerViewCategories

        val adapter = CategoriesRecyclerViewAdapter(categoriesList)
        val spanCount = 2
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)

        val spacing = resources.getDimensionPixelSize(R.dimen.spacing_xs)
        val includeEdge = true
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))

        recyclerView.adapter = adapter
    }
}
