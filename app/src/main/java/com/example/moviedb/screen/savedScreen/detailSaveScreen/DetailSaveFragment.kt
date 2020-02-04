package com.example.moviedb.screen.savedScreen.detailSaveScreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer

import com.example.moviedb.R
import com.example.moviedb.db.MoviesEntity
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_save_fragment.*

class DetailSaveFragment : Fragment() {

    companion object {
        fun newInstance() =
            DetailSaveFragment()
    }
    private lateinit var viewModelFactory: DetailSaveViewModelFactory
    private lateinit var viewModel: DetailSaveViewModel

    private lateinit var imageViewStarOff: ImageView
    private lateinit var imageViewStarOn: ImageView

    private lateinit var moviesEntity: MoviesEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.detail_save_fragment, container, false)

        imageViewStarOff = view.findViewById(R.id.iv_start_off_detal_movies_save_fragment)
        imageViewStarOn = view.findViewById(R.id.iv_start_on_detal_movies_save_fragment)

        imageViewStarOff.setOnClickListener {
            imageViewStarOff.visibility = View.GONE
            imageViewStarOn.visibility = View.VISIBLE

            viewModel.insertMovie(moviesEntity)
        }

        imageViewStarOn.setOnClickListener {
            imageViewStarOff.visibility = View.VISIBLE
            imageViewStarOn.visibility = View.GONE

            viewModel.deleteMovie(moviesEntity)
        }


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFactory =
            DetailSaveViewModelFactory(
                context!!,
                DetailSaveFragmentArgs.fromBundle(
                    arguments!!
                ).id
            )
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(DetailSaveViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.movie.observe(viewLifecycleOwner, Observer {
            if(it.id == DetailSaveFragmentArgs.fromBundle(
                    arguments!!
                ).id){
                loadData(it)
            }
        })
    }

    private fun loadData(it: MoviesEntity) {

        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+it.backdrop_path)
            .networkPolicy(NetworkPolicy.OFFLINE).into(iv_back_movies_details_movies_save_fragment)
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+it.poster_path)
            .networkPolicy(NetworkPolicy.OFFLINE).into(iv_posster_movies_details_save_fragment)
        tv_name_movies_details_save_fragment.text = it.title

        tv_genres_movies_details_save_fragment.text = it.genres
        tv_overview_movie_detail_save_fragment.text = it.overview

        moviesEntity = MoviesEntity(it.adult, it.backdrop_path, it.genres, it.id, it.overview, it.poster_path, it.title, it.vote_average)
    }

}
