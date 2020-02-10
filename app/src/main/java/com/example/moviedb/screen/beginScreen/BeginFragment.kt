package com.example.moviedb.beginScreen

import android.content.ClipData
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager

import com.example.moviedb.R
import com.example.moviedb.adapter.PagerAdapter
import com.example.moviedb.moviesScreen.MoviesFragment
import com.google.android.material.tabs.TabLayout



class BeginFragment : Fragment() {

    companion object {
        fun newInstance() = BeginFragment()
    }
    private lateinit var viewModel: BeginViewModel

    private lateinit var tabLayout: TabLayout

    private lateinit var viewPager: ViewPager

    private lateinit var adapter: PagerAdapter

    private lateinit var toolbar: Toolbar

    private lateinit var itemSearch: MenuItem
    private lateinit var itemFilter: MenuItem


    private  var item: Int = 5
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.begin_fragment, container, false)

        tabLayout = view.findViewById(R.id.tab_layout_begin_fragment)
        viewPager = view.findViewById(R.id.view_pager_begin_fragment)
        toolbar = view.findViewById(R.id.tool_bar_begin_fragment)

        itemSearch = toolbar.menu.findItem(R.id.menu_search)
        itemFilter = toolbar.menu.findItem(R.id.menu_filter)

       toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_search -> {
                    this.findNavController().navigate(BeginFragmentDirections.actionBeginFragmentToSearchFragment())
                    true
                }
                else -> {
                    this.findNavController().navigate(BeginFragmentDirections.actionBeginFragmentToFilterFragment())
                    true
                }
            }
       }

        adapter = PagerAdapter(childFragmentManager)

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        viewPager.offscreenPageLimit = 4
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                var position = tab.position
                if (position==0){
                    itemSearch.setVisible(true)
                    itemFilter.setVisible(true)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                var position = tab.position
                if (position==0){
                    itemSearch.setVisible(false)
                    itemFilter.setVisible(false)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(BeginViewModel::class.java)
        // TODO: Use the ViewModel
//        viewModel.genresDB.observe(viewLifecycleOwner, Observer {
//            if (it.size>0){
//                Toast.makeText(context, it.size.toString(), Toast.LENGTH_LONG).show()
//            }
//        })
        item++
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
