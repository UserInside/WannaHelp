package com.example.wannahelp.authScreen

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import com.example.wannahelp.R
import com.example.wannahelp.common.ToolbarFragment
import com.example.wannahelp.databinding.FragmentAuthBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class AuthFragment : ToolbarFragment(R.layout.fragment_auth, showBackButton = true) {
    private lateinit var binding: FragmentAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun setupToolbar(toolbar: Toolbar, actionButton: ImageButton) {
        toolbar.title = getString(R.string.authorization)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(content ?: view)


        val emailLengthObservable = Observable.just(binding.authEditTextEmail.text?.length ?: 0)
        val passwordLengthObservable =
            Observable.just(binding.authEditTextPassword.text?.length ?: 0)


        val isEnterButtonActiveObservable = Observable.combineLatest(
            emailLengthObservable, passwordLengthObservable
        ) { emailLength, passwordLength -> emailLength > 5 && passwordLength > 5 }


        val observer = object : Observer<Boolean> {
            override fun onSubscribe(d: Disposable) {}
            override fun onNext(t: Boolean) {
                val btnColor = if (t) R.color.leaf else R.color.warm_grey
                binding.authBtnEnter.setBackgroundColor(resources.getColor(btnColor, null))
            }


            override fun onError(e: Throwable) {}

            override fun onComplete() {}


        }

        isEnterButtonActiveObservable.subscribe(observer)



    }


}