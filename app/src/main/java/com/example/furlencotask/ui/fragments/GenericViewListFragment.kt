package com.example.furlencotask.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.furlencotask.R
import com.example.furlencotask.data.constants.AppConstants
import kotlinx.android.synthetic.main.fragment_generic_view_list.*

class GenericViewListFragment : Fragment() {

    private var params: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_generic_view_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        params = arguments?.getInt(AppConstants.REQUEST_TYPE_POSITION)
        text.text = params.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance(position: Int) =
            GenericViewListFragment().apply {
                arguments = Bundle().apply {
                    putInt(AppConstants.REQUEST_TYPE_POSITION, position)
                }
            }
    }
}