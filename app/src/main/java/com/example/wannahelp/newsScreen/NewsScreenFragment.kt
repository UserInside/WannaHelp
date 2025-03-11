package com.example.wannahelp.newsScreen

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
import com.example.wannahelp.R
import com.example.wannahelp.databinding.FragmentNewsScreenBinding


class NewsScreenFragment : Fragment() {
    private lateinit var binding: FragmentNewsScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().apply {
            title = getString(R.string.news)
            addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
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
            }, viewLifecycleOwner, Lifecycle.State.STARTED)
        }

        val newsList = listOf<NewsItem>(NewsItem(), NewsItem(), NewsItem())
        val recyclerView = binding.recyclerViewNews
        val adapter = NewsRecyclerViewAdapter(newsList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {
        fun newInstance() = NewsScreenFragment()
    }
}
