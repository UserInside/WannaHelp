package com.example.wannahelp.newsScreen.newsFilterScreen

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wannahelp.R
import com.example.wannahelp.common.Category
import com.example.wannahelp.common.ToolbarFragment

private const val CHOSEN_CATEGORIES = "chosenCategories"

class NewsFilterFragment : ToolbarFragment(R.layout.fragment_new_filter) {
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

    override fun setupToolbar(toolbar: Toolbar) {
        toolbar.apply {
            title = getString(R.string.filter)
            addMenuProvider(
                object : MenuProvider {
                    override fun onCreateMenu(
                        menu: Menu,
                        menuInflater: MenuInflater,
                    ) {
                        menuInflater.inflate(R.menu.menu_toolbar_edit_profile, menu)
                    }

                    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                        return when (menuItem.itemId) {
                            R.id.action_confirm -> {
                                sharedPrefEditor.apply {
                                    putStringSet(CHOSEN_CATEGORIES, setOfChosenCategoriesToSave)
                                    apply()
                                }
                                NavHostFragment.findNavController(this@NewsFilterFragment)
                                    .popBackStack()
                                true
                            }

//                            android.R.id.home -> {
//                                NavHostFragment.findNavController(this@NewsFilterFragment)
//                                    .popBackStack()
//                                true
//                            } //todo сделать кнопку назад

                            else -> false
                        }
                    }
                },
                viewLifecycleOwner,
                Lifecycle.State.STARTED,
            )
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val filterCategoriesList =
            mutableListOf(
                FilterCategoryCard(resources.getString(R.string.tv_cat_kids), Category.KIDS, true),
                FilterCategoryCard(
                    resources.getString(R.string.tv_cat_adults),
                    Category.ADULTS,
                    true,
                ),
                FilterCategoryCard(resources.getString(R.string.tv_cat_aged), Category.AGED, true),
                FilterCategoryCard(
                    resources.getString(R.string.tv_cat_animals),
                    Category.ANIMALS,
                    true,
                ),
                FilterCategoryCard(
                    resources.getString(R.string.tv_cat_events),
                    Category.EVENTS,
                    true,
                ),
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
}
