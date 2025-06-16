package com.example.eventdetails

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.common.ToolbarFragment
import com.example.eventdetails.databinding.FragmentEventDetailsScreenBinding
import com.example.eventdetails.di.EventDetailsComponent
import com.example.eventdetails.di.EventDetailsComponentViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.common.R as commonR

class EventDetailsScreenFragment :
    ToolbarFragment(R.layout.fragment_event_details_screen, showBackButton = true) {

    private lateinit var viewModel: EventDetailsViewModel

    @Inject
    lateinit var eventDetailsComponent: EventDetailsComponent

    override fun setupToolbar(
        toolbar: Toolbar,
        actionBtn: ImageButton,
    ) {
        toolbar.apply {
            title = getString(commonR.string.event_details)
            setNavigationOnClickListener {
                NavHostFragment.findNavController(this@EventDetailsScreenFragment).popBackStack()
            }
        }
        actionBtn.apply {
            visibility = View.VISIBLE
            setImageResource(commonR.drawable.icon_share_24)
            setOnClickListener {
                // переход на след экран "Поделиться"
            }
        }
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this)[EventDetailsComponentViewModel::class].eventDetailsComponent.inject(
            this
        )
        super.onAttach(context)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentEventDetailsScreenBinding.bind(content ?: view)
        val eventId =
            arguments?.getInt(EVENT_ID) ?: throw IllegalStateException("Event ID required")

        viewModel = ViewModelProvider(
            this, EventDetailsViewModelFactory(eventDetailsComponent, eventId)
        )[EventDetailsViewModel::class]

        binding.apply {
            lifecycleScope.launch {
                viewModel.eventDetails.collect {
                    tvTitleEventDetails.text = viewModel.eventDetails.value.name
                }
            }
            coins.setOnClickListener {
                showDonationDialog()
            }
        }
    }

    private fun showDonationDialog() {
        val dialog = DonationFragment.newInstance(
            viewModel.eventDetails.value.id,
            viewModel.eventDetails.value.name,
        )
        dialog.show(parentFragmentManager, DONATION_DIALOG)
    }

    companion object {
        const val DONATION_DIALOG = "donation_dialog_fragment"
        const val EVENT_ID = "event_id"
        const val EVENT_NAME = "event_name"
    }
}