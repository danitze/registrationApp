package com.example.registrationapp.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.registrationapp.R
import com.example.registrationapp.databinding.FragmentSignInBinding
import com.example.registrationapp.events.SignInEvent
import com.example.registrationapp.states.AuthState
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

    override fun setUpViews() {
        loadingPb = viewBinding.pbLoading

        viewBinding.etPhoneNumber.addTextChangedListener { text ->
            text?.let { viewModel.onEvent(SignInEvent.PhoneNumberChanged(it.toString())) }
        }

        viewBinding.etPassword.addTextChangedListener { text ->
            text?.let { viewModel.onEvent(SignInEvent.PasswordChanged(it.toString())) }
        }

        viewBinding.btnSignIn.setOnClickListener {
            viewBinding.etPhoneNumber.onEditorAction(EditorInfo.IME_ACTION_DONE)
            viewBinding.etPassword.onEditorAction(EditorInfo.IME_ACTION_DONE)
            viewModel.onEvent(SignInEvent.SignIn)
        }
    }

    override fun setUpObservers() {
        observe(viewModel.signInStateFlow) { signInState ->

            viewBinding.etPhoneNumber.setTextCursorEnd(signInState.phoneNumber)
            viewBinding.etPassword.setTextCursorEnd(signInState.password)

            viewBinding.btnSignIn.isEnabled =
                signInState.phoneNumber.isNotEmpty() && signInState.password.isNotEmpty()
            setLoading(signInState.isLoading)
        }

        observe(viewModel.authorizedSharedFlow) { authState ->
            when (authState) {
                is AuthState.Unauthorized -> {}
                is AuthState.Authorized -> {
                    val action = SignInFragmentDirections.actionSignInToProfile(authState.userId)
                    findNavController().navigate(action)
                }
            }
        }

        observe(viewModel.errorsFlow) { msg ->
            Toast.makeText(
                requireContext(),
                msg,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}