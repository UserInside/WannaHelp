package com.example.eventdetails

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.eventdetails.databinding.FragmentDonationBinding

class DonationFragment : DialogFragment() {
    private lateinit var binding: FragmentDonationBinding
    private val viewModel: DonationViewModel by lazy {
        ViewModelProvider(this)[DonationViewModel::class]
    }

    private var fixedAmountToPay: Int = 500
    private var customAmountToPay: Int = 0

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
            }

            shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS) -> {
                Log.e("HS", "1 РАШАНАЛЬ")
                notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }

            else -> {
                notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun setupDonationNotification() {
        val amountToPay = if (customAmountToPay == 0) fixedAmountToPay else customAmountToPay
        val inputData = workDataOf(
            EVENT_ID to arguments?.getInt(EventDetailsScreenFragment.EVENT_ID),
            EVENT_NAME to arguments?.getInt(EventDetailsScreenFragment.EVENT_NAME),
            DONATION_AMOUNT to amountToPay.toString(),
        )

        val workerRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(inputData)
            .setConstraints(Constraints(requiresCharging = true))
            .build()
        WorkManager.getInstance(requireContext()).enqueue(workerRequest)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDonationBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestNotificationPermission()

        binding.apply {
            donationBtnAmount1.setOnClickListener { fixedAmountToPay = 100 }
            donationBtnAmount2.setOnClickListener { fixedAmountToPay = 500 }
            donationBtnAmount3.setOnClickListener { fixedAmountToPay = 1000 }
            donationBtnAmount4.setOnClickListener { fixedAmountToPay = 2000 }
            etDonationInput.doOnTextChanged { newText, _, _, _ ->
                customAmountToPay = if (newText?.isEmpty() == true) {
                    0
                } else {
                    newText.toString().toInt()
                }
            }

            donationBtnTransfer.setOnClickListener {
                setupDonationNotification()
                dismiss()
            }
            donationBtnCancel.setOnClickListener { dismiss() }
        }
    }

    companion object {
        const val DONATION_AMOUNT = "donationAmount"
        const val EVENT_ID = "event_id"
        const val EVENT_NAME = "event_name"

        @JvmStatic
        fun newInstance(eventId: Int, eventName: String) =
            DonationFragment().apply {
                arguments = Bundle().apply {
                    putInt(EventDetailsScreenFragment.EVENT_ID, eventId)
                    putString(EventDetailsScreenFragment.EVENT_NAME, eventName)
                }
            }

    }
}