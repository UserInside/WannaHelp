package com.example.wannahelp.newsScreen

import android.content.Context.MODE_PRIVATE
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wannahelp.R
import com.example.wannahelp.common.ToolbarFragment
import com.example.wannahelp.common.extentions.parseToList
import com.example.wannahelp.databinding.FragmentNewsScreenBinding
import com.example.wannahelp.newsScreen.newsFilterScreen.NewsFilterFragment
import kotlinx.serialization.json.Json

class NewsScreenFragment : ToolbarFragment(R.layout.fragment_news_screen) {
    override fun setupToolbar(
        toolbar: Toolbar,
        actionButton: ImageButton,
    ) {
        toolbar.title = getString(R.string.news)
        actionButton.apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.icon_filter)
            setOnClickListener {
                NavHostFragment.findNavController(this@NewsScreenFragment)
                    .navigate(R.id.navigateToNewsFilterScreen)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNewsScreenBinding.bind(content ?: view)

        val sharedPref = requireContext().getSharedPreferences(NewsFilterFragment.CHOSEN_CATEGORIES, MODE_PRIVATE)
        val setOfChosenCategories = sharedPref?.getStringSet(NewsFilterFragment.CHOSEN_CATEGORIES, null)

        val newsItemList = Json.parseToList<NewsItem>(requireContext(), NEWS_FILE_NAME)

        val listToShow =
            newsItemList.filter { setOfChosenCategories?.contains(it.category.toString()) == true }

        val recyclerView = binding.recyclerViewNews
        val rvAdapter =
            NewsRecyclerViewAdapter { position ->
                val action =
                    NewsScreenFragmentDirections.navigateToEventDetailsScreen(listToShow[position])
                NavHostFragment.findNavController(this@NewsScreenFragment).navigate(action)
            }.apply {
                submitList(listToShow)
            }

        recyclerView.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private companion object {
        private const val NEWS_FILE_NAME = "news.json"
    }
}
