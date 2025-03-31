package com.example.wannahelp.eventDetailsScreen

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.wannahelp.R
import com.example.wannahelp.common.ToolbarFragment
import com.example.wannahelp.databinding.FragmentEventDetailsScreenBinding

class EventDetailsScreenFragment :
    ToolbarFragment(R.layout.fragment_event_details_screen, showBackButton = true) {
    val args: EventDetailsScreenFragmentArgs by navArgs()

    override fun setupToolbar(
        toolbar: Toolbar,
        actionBtn: ImageButton,
    ) {
        toolbar.apply {
            title = getString(R.string.event_details)
            setNavigationOnClickListener {
                NavHostFragment.findNavController(this@EventDetailsScreenFragment)
                    .popBackStack()
            }
        }
        actionBtn.apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.icon_share_24)
            setOnClickListener {
                // переход на след экран "Поделиться"
            }
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentEventDetailsScreenBinding.bind(content ?: view)
        val newsItem = args.clickedNewsItem

        binding.apply {
            tvTitleEventDetails.text = newsItem.title
        }
    }
}
