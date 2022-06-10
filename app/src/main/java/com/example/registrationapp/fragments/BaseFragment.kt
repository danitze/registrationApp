package com.example.registrationapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding> : Fragment() {

    private var _viewBinding: B? = null
    protected val viewBinding: B
    get() = checkNotNull(_viewBinding)

    protected var loadingPb: ProgressBar? = null

    protected abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup?): B
    protected abstract fun setUpViews()
    protected abstract fun setUpObservers()

    protected fun EditText.setTextCursorEnd(text: String) {
        setText(text)
        setSelection(this@setTextCursorEnd.length())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = initBinding(inflater, container)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    fun setLoading(isLoading: Boolean) {
        loadingPb?.visibility = if(isLoading) View.VISIBLE else View.GONE
    }
}