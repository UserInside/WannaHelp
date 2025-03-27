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
    private val bottomNavView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)
    private val viewModel: AuthViewModel by viewModels()

    override fun setupToolbar(toolbar: Toolbar, actionButton: ImageButton) {
        toolbar.title = getString(R.string.authorization)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bottomNavView?.visibility = View.GONE
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(content ?: view)


//        binding.authEditTextEmail.textChanges().subscribe()


//        val disposable = CompositeDisposable()
//        disposable.add(
//
//        )

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
        ) { emailLength, passwordLength -> emailLength > 5 && passwordLength > 5 }
            .subscribe { isButtonActive ->
                Log.e("WOW", "more then six?? -> $isButtonActive")
                binding.authBtnEnter.apply {
                    isClickable = isButtonActive
                    if (isButtonActive) setBackgroundColor(resources.getColor(R.color.leaf, null))
                    else setBackgroundColor(resources.getColor(R.color.warm_grey, null))
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomNavView?.visibility = View.VISIBLE
    }


}