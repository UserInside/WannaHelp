package com.example.wannahelp.newsScreen

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wannahelp.R
import com.example.wannahelp.backgroundWork.service.ReadNewsFileService
import com.example.wannahelp.common.ToolbarFragment
import com.example.wannahelp.databinding.FragmentNewsScreenBinding
import com.example.wannahelp.newsScreen.newsFilterScreen.NewsFilterFragment

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

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNewsScreenBinding.bind(content ?: view)

        if (savedInstanceState == null) {
            startReadNewsFileService()
        }

        val sharedPref = requireContext().getSharedPreferences(
            NewsFilterFragment.CHOSEN_CATEGORIES,
            MODE_PRIVATE
        )
        val setOfChosenCategories =
            sharedPref?.getStringSet(NewsFilterFragment.CHOSEN_CATEGORIES, null)

        var newsItemList: List<NewsItem>? = null

        ReadNewsFileService.resultLiveData.observe(viewLifecycleOwner) {

            binding.newsProgressBar.visibility = View.GONE
            newsItemList = it

            val listToShow =
                newsItemList?.filter { setOfChosenCategories?.contains(it.category.toString()) == true }

            val recyclerView = binding.recyclerViewNews
            val rvAdapter =
                NewsRecyclerViewAdapter { position ->
                    val action =
                        NewsScreenFragmentDirections.navigateToEventDetailsScreen(
                            listToShow?.get(
                                position
                            ) ?: NewsItem()
                        )
                    NavHostFragment.findNavController(this@NewsScreenFragment).navigate(action)
                }.apply {
                    submitList(listToShow)
                }

            recyclerView.apply {
                adapter = rvAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun startReadNewsFileService() {
        val progressBar = view?.findViewById<ProgressBar>(R.id.news_progressBar)
        progressBar?.visibility = View.VISIBLE
        val intent = Intent(requireContext(), ReadNewsFileService::class.java)
        intent.putExtra(NEWS_FILE_NAME_KEY, NEWS_FILE_NAME)
        requireContext().startService(intent)
    }

    companion object {
        const val NEWS_FILE_NAME_KEY = "fileName"
        private const val NEWS_FILE_NAME = "news.json"
    }
}
