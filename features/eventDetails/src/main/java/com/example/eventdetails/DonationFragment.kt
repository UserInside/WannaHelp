package com.example.eventdetails

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.eventdetails.databinding.FragmentDonationBinding
import com.example.eventdetails.notification.NotificationWorker
import timber.log.Timber

class DonationFragment : DialogFragment() {
    private lateinit var binding: FragmentDonationBinding

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
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
            }

            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                Timber.e("WOW RATIONALE")
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }

            else -> {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun setupDonationNotification() {
        val amountToPay = if (customAmountToPay == 0) fixedAmountToPay else customAmountToPay
        val inputData = workDataOf(
            EVENT_ID to arguments?.getInt(EventDetailsScreenFragment.EVENT_ID),
            EVENT_NAME to arguments?.getString(EventDetailsScreenFragment.EVENT_NAME),
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
            etDonationInput.inputType = InputType.TYPE_CLASS_NUMBER
            etDonationInput.addTextChangedListener(
                SimpleNumberWatcher(etDonationInput) { newAmount ->
                    customAmountToPay = newAmount
                })

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

class SimpleNumberWatcher(
    private val editText: EditText,
    private val onAmountChanged: (Int) -> Unit
) : TextWatcher {

    private var isFormatting = false
    private var lastText = ""

    override fun afterTextChanged(s: Editable) {
        if (isFormatting) return

        val current = s.toString()
        if (current == lastText) return

        isFormatting = true

        val clean = current.replace("\\s".toRegex(), "")
        val formatted = if (clean.isEmpty()) "" else {
            clean.replace(Regex("(\\d)(?=(\\d{3})+$)"), "$1 ")
        }

        if (current != formatted) {
            editText.setText(formatted)
            editText.setSelection(formatted.length)
            lastText = formatted
        } else {
            lastText = current
        }

        val amount = clean.toIntOrNull() ?: 0
        onAmountChanged(amount)

        isFormatting = false
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
}
