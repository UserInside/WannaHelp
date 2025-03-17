package com.example.wannahelp.eventDetailsScreen

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.example.wannahelp.R
import com.example.wannahelp.common.ToolbarFragment
import com.example.wannahelp.databinding.FragmentEventDetailsScreenBinding

class EventDetailsScreenFragment : ToolbarFragment(R.layout.fragment_event_details_screen) {
    val args: EventDetailsScreenFragmentArgs by navArgs()

    override fun setupToolbar(toolbar: Toolbar) {
        toolbar.apply {
            title = getString(R.string.event_details)
            addMenuProvider(
                object : MenuProvider {
                    override fun onCreateMenu(
                        menu: Menu,
                        menuInflater: MenuInflater,
                    ) {
                        menuInflater.inflate(R.menu.menu_toolbar_event_details, menu)
                    }

                    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                        return when (menuItem.itemId) {
                            R.id.action_share -> {
//                                Заглушка
//                                NavHostFragment.findNavController(this@EventDetailsScreenFragment)
//                                    .navigate(R.id.navigateToNewsFilterScreen)
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
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentEventDetailsScreenBinding.bind(content!!)
        val newsItem = args.clickedNewsItem

        binding.apply {
            tvTitleEventDetails.text = newsItem.title
        }
    }
}
