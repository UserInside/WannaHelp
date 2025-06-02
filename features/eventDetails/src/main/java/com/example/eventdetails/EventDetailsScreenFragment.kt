package com.example.eventdetails

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import com.example.common.ToolbarFragment
import com.example.eventdetails.databinding.FragmentEventDetailsScreenBinding
import com.example.common.models.NewsUiModel
import com.example.common.R as commonR

class EventDetailsScreenFragment :
    ToolbarFragment(R.layout.fragment_event_details_screen, showBackButton = true) {

    private lateinit var newsItem: NewsUiModel

    override fun setupToolbar(
        toolbar: Toolbar,
        actionBtn: ImageButton,
    ) {
        toolbar.apply {
            title = getString(commonR.string.event_details)
            setNavigationOnClickListener {
                NavHostFragment.findNavController(this@EventDetailsScreenFragment)
                    .popBackStack()
            }
        }
        actionBtn.apply {
            visibility = View.VISIBLE
            setImageResource(commonR.drawable.icon_share_24)
            setOnClickListener {
                // переход на след экран "Поделиться"
                showDonationDialog()
            }
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentEventDetailsScreenBinding.bind(content ?: view)
        newsItem = arguments?.getSerializable("event") as NewsUiModel

        binding.apply {
            tvTitleEventDetails.text = newsItem.name
        }
    }

    private fun showDonationDialog() {
        val dialog = DonationFragment.newInstance(
            newsItem.id,
            newsItem.name,
        )
        dialog.show(parentFragmentManager, DONATION_DIALOG)
    }

    companion object {
        const val DONATION_DIALOG = "DonationDialogFragment"
        const val EVENT_ID = "eventId"
        const val EVENT_NAME = "eventName"
    }
}