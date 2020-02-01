package com.example.moviedb.beginScreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager

import com.example.moviedb.R
import com.example.moviedb.adapter.PagerAdapter
import com.google.android.material.tabs.TabLayout

class BeginFragment : Fragment() {

    companion object {
        fun newInstance() = BeginFragment()
    }

    private lateinit var viewModel: BeginViewModel

    private lateinit var tabLayout: TabLayout

    private lateinit var viewPager: ViewPager

    private lateinit var adapter: PagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.begin_fragment, container, false)
        tabLayout = view.findViewById(R.id.tab_layout_begin_fragment)
        viewPager = view.findViewById(R.id.view_pager_begin_fragment)

        adapter = PagerAdapter(childFragmentManager)

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BeginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
