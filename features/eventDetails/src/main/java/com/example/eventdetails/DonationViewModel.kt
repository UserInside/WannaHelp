package com.example.eventdetails

import android.widget.Button
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.Boolean

class DonationViewModel : ViewModel() {


    val button1State = MutableStateFlow(ButtonState.Enabled())
    val button2State = MutableStateFlow(ButtonState.Disabled())
    val button3State = MutableStateFlow(ButtonState.Disabled())
    val button4State = MutableStateFlow(ButtonState.Disabled())

    val btnsList: List<MutableStateFlow<ButtonState>> =
        listOf(
            button1State,
            button2State,
            button3State,
            button4State
        ) as List<MutableStateFlow<ButtonState>>

    fun onButtonClicked(btn: Button) {


    }
}


sealed class ButtonState {
    data class Enabled(
        val btnState: BtnState = BtnState(
            isEnabled = true,
            background = 1,
            textColor = 1,
        )
    ) : ButtonState()

    data class Disabled(
        val btnState: BtnState = BtnState(
            isEnabled = false,
            background = 0,
            textColor = 0,
        )
    ) : ButtonState()
}

data class BtnState(
    val isEnabled: Boolean,
    val background: Int,
    val textColor: Int,
)