package com.example.wannahelp.wannaHelpScreen

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.wannahelp.R
import com.example.wannahelp.databinding.FragmentWannaHelpScreenBinding

class WannaHelpScreenFragment : Fragment() {
    private lateinit var binding: FragmentWannaHelpScreenBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWannaHelpScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesList = listOf(
            CategoryCard(R.drawable.icon_cat_kids, resources.getString(R.string.tv_cat_kids)),
            CategoryCard(R.drawable.icon_cat_adults, resources.getString(R.string.tv_cat_adults)),
            CategoryCard(R.drawable.icon_cat_aged, resources.getString(R.string.tv_cat_aged)),
            CategoryCard(R.drawable.icon_cat_animals, resources.getString(R.string.tv_cat_animals)),
            CategoryCard(R.drawable.icon_cat_events, resources.getString(R.string.tv_cat_events)),
        )

        val recyclerView = binding.recyclerViewCategories

        val adapter = CategoriesRecyclerViewAdapter(categoriesList)
        val spanCount = 2
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), spanCount)

        // Добавление отступов
        val spacing = resources.getDimensionPixelSize(R.dimen.spacing_xs) // 16dp
        val includeEdge = true // Включать отступы по краям
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))


        recyclerView.adapter = adapter

        val bottomBar = binding.bottomNavView.bottomNavigationView
        bottomBar.setupWithNavController(findNavController())

        bottomBar.setOnClickListener {
            when (it.id) {
                R.id.action_profile -> NavHostFragment.findNavController(this)
                    .navigate(R.id.navigateToProfileScreen)
            }
        }


    }

    companion object {
        fun newInstance() = WannaHelpScreenFragment()
    }
}

class GridSpacingItemDecoration(
    private val spanCount: Int, // Количество колонок
    private val spacing: Int,   // Расстояние между ячейками (в пикселях)
    private val includeEdge: Boolean // Включать ли отступы по краям
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // Позиция элемента
        val column = position % spanCount // Номер колонки

        if (includeEdge) {
            // Отступы по краям
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount) { // Верхний ряд
                outRect.top = spacing
            }
            outRect.bottom = spacing // Отступ снизу
        } else {
            // Без отступов по краям
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing
            }
        }
    }
}