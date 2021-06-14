package com.itzikpich.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.itzikpich.weatherapp.view_models.SharedViewModel


typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

open class BaseFragment<VB: ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {

    protected val TAG = this::class.java.simpleName

    val mainActivity get() = activity as MainActivity

    val sharedViewModel by activityViewModels<SharedViewModel>()

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}