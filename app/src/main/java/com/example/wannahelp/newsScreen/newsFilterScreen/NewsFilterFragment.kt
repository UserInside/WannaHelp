package com.example.wannahelp.newsScreen.newsFilterScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wannahelp.R
import com.example.wannahelp.wannaHelpScreen.CategoryCard

class NewsFilterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().apply {
            title = getString(R.string.filter)
            //todo добавить кнопку назад
            addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_toolbar_edit_profile, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.action_confirm -> {
                            NavHostFragment.findNavController(this@NewsFilterFragment)
                                .navigate(R.id.navigateToNewsScreen)
                            true
                        }

                        else -> false
                    }
                }
            }, viewLifecycleOwner, Lifecycle.State.STARTED)
        }

        val filterCategoriesList =
            listOf(
                FilterCategoryCard(resources.getString(R.string.tv_cat_kids), true),
                FilterCategoryCard(resources.getString(R.string.tv_cat_adults), true),
                FilterCategoryCard(resources.getString(R.string.tv_cat_aged), false),
                FilterCategoryCard(resources.getString(R.string.tv_cat_animals), false),
                FilterCategoryCard(resources.getString(R.string.tv_cat_events), true),
            )

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_filter_categories)
        val adapter = NewsFilterRecyclerViewAdapter(filterCategoriesList) {position, isChecked ->
            Log.e("SWITCH FR", "pos $position, isChecked $isChecked")
            sendCheckedChangedItemId(position)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())



    }

    companion object {
        fun newInstance() = NewsFilterFragment()
    }

    private fun sendCheckedChangedItemId(id: Int) {
        val bundle = Bundle()
        bundle.putInt("changedItemId", id)
        parentFragmentManager.setFragmentResult("changedItemId", bundle)
    }
}