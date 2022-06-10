package com.example.registrationapp.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.registrationapp.databinding.FragmentSignInBinding
import com.example.registrationapp.events.SignInEvent
import com.example.registrationapp.utils.observe
import com.example.registrationapp.viewmodels.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    private val viewModel: SignInViewModel by viewModels()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignInBinding = FragmentSignInBinding.inflate(inflater, container, false)

    override fun setUpListeners() {
        viewBinding.etPhoneNumber.addTextChangedListener { text ->
            text?.let { viewModel.onEvent(SignInEvent.PhoneNumberChanged(it.toString())) }
        }

        viewBinding.etPassword.addTextChangedListener { text ->
            text?.let { viewModel.onEvent(SignInEvent.PasswordChanged(it.toString())) }
        }

        viewBinding.btnSignIn.setOnClickListener {
            viewModel.onEvent(SignInEvent.SignIn)
        }
    }

    override fun setUpObservers() {
        observe(viewModel.signInStateFlow) { signInState ->
            viewBinding.btnSignIn.isEnabled =
                signInState.phoneNumber.isNotEmpty() && signInState.password.isNotEmpty()
        }
    }
}