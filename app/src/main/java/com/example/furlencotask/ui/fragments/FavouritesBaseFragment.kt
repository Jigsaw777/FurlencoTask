package com.example.furlencotask.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.furlencotask.R
import com.example.furlencotask.domain.entities.RequestType
import com.example.furlencotask.ui.adapters.FavsViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_favourites.*

class FavouritesBaseFragment : Fragment(), OnItemClick {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vp_fav_fragments.adapter = FavsViewPagerAdapter(this)
        TabLayoutMediator(favs_tab_layout, vp_fav_fragments){tab,position ->
            tab.text = RequestType.values()[position].toString()
        }.attach()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavouritesBaseFragment()
    }

    override fun onClick(url: String) {
        parentFragmentManager.beginTransaction()
            .add(R.id.favs_container, ShowNewsFragment.newInstance(url))
            .addToBackStack(null).commit()
    }
}
