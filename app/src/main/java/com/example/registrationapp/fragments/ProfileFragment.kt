package com.example.registrationapp.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.registrationapp.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

    override fun setUpListeners() {
    }

    override fun setUpObservers() {
    }
}