package com.example.wannahelp.newsScreen.newsFilterScreen

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wannahelp.MainActivity
import com.example.wannahelp.R
import com.example.wannahelp.common.Category

private const val CHOSEN_CATEGORIES = "chosenCategories"

class NewsFilterFragment : Fragment() {
    private lateinit var setOfChosenCategoriesToSave: MutableSet<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_new_filter, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = requireContext().getSharedPreferences(CHOSEN_CATEGORIES, MODE_PRIVATE)
        val sharedPrefEditor = sharedPref.edit()

        requireActivity().apply {
            title = getString(R.string.filter)
            (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

                            android.R.id.home -> {
                                NavHostFragment.findNavController(this@NewsFilterFragment)
                                    .popBackStack()
                                true
                            }

                            else -> false
                        }
                    }
                },
                viewLifecycleOwner,
                Lifecycle.State.STARTED,
            )
        }

        val filterCategoriesList =
            mutableListOf(
                FilterCategoryCard(resources.getString(R.string.tv_cat_kids), Category.KIDS, true),
                FilterCategoryCard(
                    resources.getString(R.string.tv_cat_adults),
                    Category.ADULTS,
                    true
                ),
                FilterCategoryCard(resources.getString(R.string.tv_cat_aged), Category.AGED, true),
                FilterCategoryCard(
                    resources.getString(R.string.tv_cat_animals),
                    Category.ANIMALS,
                    true
                ),
                FilterCategoryCard(
                    resources.getString(R.string.tv_cat_events),
                    Category.EVENTS,
                    true
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
