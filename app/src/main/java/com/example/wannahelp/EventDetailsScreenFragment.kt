package com.example.wannahelp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class EventDetailsScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_event_details_screen, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().apply {
            title = getString(R.string.event_details)
            actionBar?.setDisplayShowHomeEnabled(true)
        }
    }

    companion object {
        fun newInstance() = EventDetailsScreenFragment()
    }
}
