package com.example.moviedb.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.moviedb.moviesScreen.MoviesFragment
import com.example.moviedb.peopleScreen.PeopleFragment
import com.example.moviedb.savedScreen.SavedFragment
import com.example.moviedb.seriesScreen.SeriesFragment

class PagerAdapter (fm: FragmentManager):FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        when (position){
            0 -> return MoviesFragment.newInstance()
            1 -> return SeriesFragment.newInstance()
            2 -> return SavedFragment.newInstance()
            else -> return PeopleFragment.newInstance()
        }
    }

    override fun getCount(): Int = 4

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> return "MOVIES"
            1 -> return "SERIES"
            2 -> return "SAVED"
            else -> return "PEOPLE"
        }
    }
}