package com.example.wannahelp.presentation.authScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.wannahelp.R
import com.example.wannahelp.common.ToolbarFragment
import com.example.wannahelp.databinding.FragmentAuthBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class AuthFragment : ToolbarFragment(R.layout.fragment_auth, showBackButton = true) {
    private lateinit var binding: FragmentAuthBinding
    private lateinit var bottomNavView: View
    private val viewModel: AuthViewModel by viewModels()

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

        bottomNavView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view).also {
                it.visibility = View.GONE
            }
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
                        setBackgroundColor(resources.getColor(R.color.leaf, null))
                        setOnClickListener {
                            NavHostFragment.findNavController(this@AuthFragment)
                                .navigate(R.id.navigateToWannaHelpScreenFragment)
                            bottomNavView.visibility = View.VISIBLE
                        }
                    } else {
                        setBackgroundColor(resources.getColor(R.color.warm_grey, null))
                        setOnClickListener {}
                    }
                }
            }
        }
    }
}
