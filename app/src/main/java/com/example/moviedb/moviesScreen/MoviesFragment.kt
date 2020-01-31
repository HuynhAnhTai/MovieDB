package com.example.moviedb.moviesScreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager

import com.example.moviedb.R
import com.example.moviedb.adapter.MoviesAdapter
import com.example.moviedb.adapter.MoviesClick
import com.example.moviedb.model.Movies
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movies_fragment.*

class MoviesFragment : Fragment() {
    private lateinit var moviesAdapter: MoviesAdapter
    companion object {
        fun newInstance() = MoviesFragment()
    }

    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        // TODO: Use the ViewModel
        var movies = Movies("Gooooooood morning")
        var data: MutableList<Movies> = ArrayList<Movies>()

        for(i in 0..10){
            data.add(movies)
        }

        moviesAdapter = MoviesAdapter(MoviesClick {
            Toast.makeText(context,it,Toast.LENGTH_LONG).show()
        })
        moviesAdapter.submitList(data)

        recyler_view_movies_fragment.layoutManager = GridLayoutManager(context,3)

        recyler_view_movies_fragment.adapter = moviesAdapter
    }

}
