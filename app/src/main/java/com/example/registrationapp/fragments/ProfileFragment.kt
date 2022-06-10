package com.example.registrationapp.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.registrationapp.databinding.FragmentProfileBinding
import com.example.registrationapp.events.ProfileEvent
import com.example.registrationapp.states.AuthState
import com.example.registrationapp.utils.observe
import com.example.registrationapp.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.take


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

    override fun setUpViews() {
        loadingPb = viewBinding.pbLoading

        viewBinding.etName.addTextChangedListener { text ->
            text?.let { viewModel.onEvent(ProfileEvent.NameChanged(it.toString())) }
        }

        viewBinding.etSurname.addTextChangedListener { text ->
            text?.let { viewModel.onEvent(ProfileEvent.SurnameChanged(it.toString())) }
        }

        viewBinding.etPhoneNumber.addTextChangedListener { text ->
            text?.let { viewModel.onEvent(ProfileEvent.PhoneNumberChanged(it.toString())) }
        }

        viewBinding.btnSave.setOnClickListener {
            viewBinding.etName.onEditorAction(EditorInfo.IME_ACTION_DONE)
            viewBinding.etSurname.onEditorAction(EditorInfo.IME_ACTION_DONE)
            viewBinding.etPhoneNumber.onEditorAction(EditorInfo.IME_ACTION_DONE)
            viewModel.onEvent(ProfileEvent.SaveProfile)
        }
    }

    override fun setUpObservers() {
        observe(viewModel.authorizedSharedFlow) { authState ->
            when(authState) {
                is AuthState.Authorized -> {}
                is AuthState.Unauthorized -> {
                    val action = ProfileFragmentDirections.signOut()
                    findNavController().navigate(action)
                }
            }
        }

        observe(viewModel.profileFragmentStateFlow.take(2)) { profileFragmentState ->
            viewBinding.etName.setText(profileFragmentState.name)
            viewBinding.etSurname.setText(profileFragmentState.surname)
            viewBinding.etPhoneNumber.setText(profileFragmentState.phoneNumber)
        }

        observe(viewModel.profileFragmentStateFlow) { profileFragmentState ->
            if(profileFragmentState.dataChanged && viewModel.currentUserInfo.notEmpty()) {
                viewBinding.btnSave.visibility = View.VISIBLE
            } else {
                viewBinding.btnSave.visibility = View.GONE
            }
            setLoading(profileFragmentState.isLoading)
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