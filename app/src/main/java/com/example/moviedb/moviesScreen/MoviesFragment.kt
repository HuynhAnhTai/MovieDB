package com.example.moviedb.moviesScreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager

import com.example.moviedb.R
import com.example.moviedb.adapter.MoviesAdapter
import com.example.moviedb.adapter.MoviesClick
import com.example.moviedb.adapter.PagerAdapter
import com.example.moviedb.beginScreen.BeginFragmentDirections
import com.example.moviedb.model.Movies
import com.example.moviedb.moviesScreen.detailMoviesSreen.DetailMoviesFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movies_fragment.*

class MoviesFragment : Fragment() {
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = MoviesFragment()
    }
    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.movies_fragment, container, false)

        recyclerView = view.findViewById(R.id.recyler_view_movies_fragment)


        moviesAdapter = MoviesAdapter(MoviesClick {
            view.findNavController().navigate(BeginFragmentDirections.actionBeginFragmentToDetailMoviesFragment(it))
        })
        recyclerView.adapter = moviesAdapter

        recyclerView.layoutManager = GridLayoutManager(context,3)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            if(it.total_results>0){
                if (it.results.size>0) {
                    moviesAdapter.submitList(it.results)
                }
            }
        })

    }

}
