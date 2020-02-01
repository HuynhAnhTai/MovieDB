package com.example.moviedb.moviesScreen.detailMoviesSreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.moviedb.R

class DetailMoviesFragment : Fragment() {
    private var id: Long = 0
    companion object {
        fun newInstance() = DetailMoviesFragment()
    }

    private lateinit var viewModel: DetailMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.detail_movies_fragment, container, false)
        id = arguments!!.get("id") as Long
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailMoviesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
