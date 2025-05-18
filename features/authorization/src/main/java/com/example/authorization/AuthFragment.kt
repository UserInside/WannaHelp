package com.example.authorization

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.authorization.databinding.FragmentAuthBinding
import com.example.authorization.di.AuthComponentViewModel
import com.example.common.ToolbarFragment
import com.example.common.extensions.navigate
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.common.R as commonR

class AuthFragment : ToolbarFragment(R.layout.fragment_auth, showBackButton = true) {
    private lateinit var binding: FragmentAuthBinding

    private val viewModel: AuthViewModel by viewModels ()

    @Inject
    lateinit var navigator: AuthorizationNavigator

    override fun setupToolbar(
        toolbar: Toolbar,
        actionButton: ImageButton,
    ) {
        toolbar.apply {
            title = getString(R.string.authorization)
            setNavigationOnClickListener {
                requireActivity().finishAffinity()
            }
        }
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this)[AuthComponentViewModel::class].authComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        requireActivity().onBackPressedDispatcher.addCallback(
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finishAffinity()
                }
            },
        )

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(content ?: view)

        if (savedInstanceState != null) {
            binding.authEditTextEmail.setText(viewModel.emailTextValue.value)
            binding.authEditTextPassword.setText(viewModel.passwordTextValue.value)
        }

        binding.authEditTextEmail.doOnTextChanged { newText, _, _, _ ->
            viewModel.emailTextValue.value = newText.toString()
        }

        binding.authEditTextPassword.doOnTextChanged { newText, _, _, _ ->
            viewModel.passwordTextValue.value = newText.toString()
        }

        lifecycleScope.launch {
            viewModel.isFieldsLengthSufficient.collect { isButtonActive ->
                binding.authBtnEnter.apply {
                    isClickable = isButtonActive
                    if (isButtonActive) {
                        setBackgroundColor(
                            resources.getColor(
                                commonR.color.leaf,
                                null
                            )
                        )
                        setOnClickListener {
                            Log.e("VIV", "click")
                            navigate(navigator.toCategories)
                        }
                    } else {
                        setBackgroundColor(
                            resources.getColor(
                                commonR.color.warm_grey,
                                null
                            )
                        )
                        setOnClickListener {}
                    }
                }
            }
        }
    }
}
