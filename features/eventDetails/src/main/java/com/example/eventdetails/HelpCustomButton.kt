package com.example.eventdetails

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.eventdetails.databinding.HelpCustomButtonViewBinding

class HelpCustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout (context, attrs, defStyleAttr) {

    private val binding: HelpCustomButtonViewBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = HelpCustomButtonViewBinding.inflate(inflater, this, true)

        isClickable = true
        isFocusable = true
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.HelpCustomButtonView,
            0,
            0)
            .apply {
                try {
                    binding.customViewText.text = getString(R.styleable.HelpCustomButtonView_customText) ?: ""
                    val imageRes = getResourceId(R.styleable.HelpCustomButtonView_customImage, 0)
                    if (imageRes != 0) {
                        binding.customViewImage.setImageResource(imageRes)
                    }
                    val showDivider = getBoolean(R.styleable.HelpCustomButtonView_showDivider, false)
                    setDividerVisible(showDivider)

                } finally {
                    recycle()
                }
            }
    }
    fun setDividerVisible(isVisible: Boolean) {
        binding.divider.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}