package com.example.wannahelp.newsScreen

import android.content.Context.MODE_PRIVATE
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wannahelp.MainActivity
import com.example.wannahelp.R
import com.example.wannahelp.common.extentions.readFile
import com.example.wannahelp.databinding.FragmentNewsScreenBinding
import kotlinx.serialization.json.Json

private const val CHOSEN_CATEGORIES = "chosenCategories"
private const val NEWS_FILE_NAME = "news.json"

class NewsScreenFragment : Fragment() {
    private lateinit var binding: FragmentNewsScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = requireContext().getSharedPreferences(CHOSEN_CATEGORIES, MODE_PRIVATE)
        val setOfChosenCategories = sharedPref?.getStringSet(CHOSEN_CATEGORIES, null)

        requireActivity().apply {
            title = getString(R.string.news)
            (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
            addMenuProvider(
                object : MenuProvider {
                    override fun onCreateMenu(
                        menu: Menu,
                        menuInflater: MenuInflater,
                    ) {
                        menuInflater.inflate(R.menu.menu_toolbar_news, menu)
                    }

                    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                        return when (menuItem.itemId) {
                            R.id.action_filter -> {
                                NavHostFragment.findNavController(this@NewsScreenFragment)
                                    .navigate(R.id.navigateToNewsFilterScreen)
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

        val jsonString = requireContext().assets.readFile(NEWS_FILE_NAME)
        val newsItemList = Json.decodeFromString<List<NewsItem>>(jsonString)
        val listToShow =
            newsItemList.filter { setOfChosenCategories?.contains(it.category.toString()) == true }

        val recyclerView = binding.recyclerViewNews
        val adapter =
            NewsRecyclerViewAdapter().apply {
                submitList(listToShow)
            }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}
