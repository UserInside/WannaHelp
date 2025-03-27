package com.example.wannahelp.authScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.wannahelp.MainActivity
import com.example.wannahelp.R
import com.example.wannahelp.common.ToolbarFragment
import com.example.wannahelp.databinding.FragmentAuthBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.rxbinding.widget.RxTextView.textChanges
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import rx.Subscriber

class AuthFragment : ToolbarFragment(R.layout.fragment_auth, showBackButton = true) {

    private lateinit var binding: FragmentAuthBinding
    private lateinit var bottomNavView: View
    private val viewModel: AuthViewModel by viewModels()

    override fun setupToolbar(toolbar: Toolbar, actionButton: ImageButton) {
        toolbar.title = getString(R.string.authorization)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bottomNavView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view).also {
                it.visibility = View.GONE
            }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(content ?: view)

        var emailLengthObservable: Observable<Int> =
            binding.authEditTextEmail.textChanges().map {
                it.length
            }

        var passwordLengthObservable: Observable<Int> =
            binding.authEditTextPassword.textChanges().map {
                it.length
            }

        Observable.combineLatest(
            emailLengthObservable, passwordLengthObservable
        ) { emailLength, passwordLength -> emailLength >= 6 && passwordLength >= 6 }
            .subscribe { isButtonActive ->
                Log.e("WOW", "more than six ?? -> $isButtonActive")
                binding.authBtnEnter.apply {
                    isClickable = isButtonActive
                    if (isButtonActive) {
                        setBackgroundColor(resources.getColor(R.color.leaf, null))
                        setOnClickListener {
                            NavHostFragment.findNavController(this@AuthFragment)
                                .navigate(R.id.navigateToWannaHelpScreenFragment)
                            bottomNavView.visibility = View.VISIBLE
                        }
                    } else setBackgroundColor(resources.getColor(R.color.warm_grey, null))
                }
            }
    }
}