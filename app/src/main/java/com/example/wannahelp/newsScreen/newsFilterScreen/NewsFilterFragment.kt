package com.example.wannahelp.newsScreen.newsFilterScreen

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wannahelp.R
import com.example.wannahelp.common.Category
import com.example.wannahelp.common.ToolbarFragment

class NewsFilterFragment : ToolbarFragment(R.layout.fragment_new_filter, showBackButton = true) {
    private lateinit var setOfChosenCategoriesToSave: MutableSet<String>
    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefEditor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        sharedPref = requireContext().getSharedPreferences(CHOSEN_CATEGORIES, MODE_PRIVATE)
        sharedPrefEditor = sharedPref.edit()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

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
                sharedPrefEditor.apply {
                    putStringSet(CHOSEN_CATEGORIES, setOfChosenCategoriesToSave)
                    apply()
                }
                NavHostFragment.findNavController(this@NewsFilterFragment)
                    .popBackStack()
            }
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val filterCategoriesList =
            mutableListOf(
                FilterCategoryCard(resources.getString(R.string.tv_cat_kids), Category.KIDS),
                FilterCategoryCard(resources.getString(R.string.tv_cat_adults), Category.ADULTS),
                FilterCategoryCard(resources.getString(R.string.tv_cat_aged), Category.AGED),
                FilterCategoryCard(resources.getString(R.string.tv_cat_animals), Category.ANIMALS),
                FilterCategoryCard(resources.getString(R.string.tv_cat_events), Category.EVENTS),
            )

        val setOfChosenCategories = sharedPref.getStringSet(CHOSEN_CATEGORIES, null)

        val listToShow =
            filterCategoriesList.map { categoryCard ->
                val isChecked =
                    setOfChosenCategories?.contains(categoryCard.category.toString()) == true
                categoryCard.copy(isChecked = isChecked)
            }

        setOfChosenCategoriesToSave =
            setOfChosenCategories?.toMutableSet()
                ?: filterCategoriesList.map { it.category.toString() }.toMutableSet()

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_filter_categories)
        val adapter =
            NewsFilterRecyclerViewAdapter(listToShow) { category, isChecked ->
                if (isChecked) {
                    setOfChosenCategoriesToSave.add(category.toString())
                } else {
                    setOfChosenCategoriesToSave.remove(category.toString())
                }
            }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {
        internal const val CHOSEN_CATEGORIES = "chosenCategories"
    }
}
