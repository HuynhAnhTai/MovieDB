package com.example.moviedb.moviesScreen.detailMoviesSreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.moviedb.R
import com.example.moviedb.adapter.CastMovieAdapter
import com.example.moviedb.adapter.CasterClick
import com.example.moviedb.db.MoviesEntity
import com.example.moviedb.modelAPI.MovieByIdResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_movies_fragment.*

class DetailMoviesFragment : Fragment() {

    companion object {
        fun newInstance() = DetailMoviesFragment()
    }

    private lateinit var viewModel: DetailMoviesViewModel
    private lateinit var viewModelFactory: DetailMoviesViewModelFactory

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageViewStarOff: ImageView
    private lateinit var imageViewStarOn: ImageView

    private lateinit var adapter: CastMovieAdapter

    private lateinit var moviesEntity: MoviesEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.detail_movies_fragment, container, false)

        recyclerView = view.findViewById(R.id.recyler_view_cast_detail_movie_fragment)

        imageViewStarOff = view.findViewById(R.id.iv_start_off_detal_movies_fragment)
        imageViewStarOn = view.findViewById(R.id.iv_start_on_detal_movies_fragment)

        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)

        adapter = CastMovieAdapter(CasterClick {
            this.findNavController().navigate(DetailMoviesFragmentDirections.actionDetailMoviesFragmentToDetailInformationPeopleFragment(it))
        })
        recyclerView.adapter = adapter

        imageViewStarOff.setOnClickListener {
            iv_start_off_detal_movies_fragment.visibility = View.GONE
            iv_start_on_detal_movies_fragment.visibility = View.VISIBLE

            //viewModel.insertMovie(moviesEntity)
        }

        imageViewStarOn.setOnClickListener {
            iv_start_off_detal_movies_fragment.visibility = View.VISIBLE
            iv_start_on_detal_movies_fragment.visibility = View.GONE

            //viewModel.deleteMovie(moviesEntity)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFactory = DetailMoviesViewModelFactory(DetailMoviesFragmentArgs.fromBundle(arguments!!).id, context!!)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailMoviesViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.detailMovie.observe(viewLifecycleOwner, Observer {
            if (it.id == DetailMoviesFragmentArgs.fromBundle(arguments!!).id){
                loadData(it)
            }
        })

        viewModel.castMovie.observe(viewLifecycleOwner, Observer {
            if(it.id == DetailMoviesFragmentArgs.fromBundle(arguments!!).id ){
                if (it.cast.size>0){
                    adapter.submitList(it.cast)
                }
            }
        })

        viewModel.save.observe(viewLifecycleOwner, Observer {
            if (it.id == DetailMoviesFragmentArgs.fromBundle(arguments!!).id){
                iv_start_off_detal_movies_fragment.visibility = View.GONE
                iv_start_on_detal_movies_fragment.visibility = View.VISIBLE
            }else{
                iv_start_off_detal_movies_fragment.visibility = View.VISIBLE
                iv_start_on_detal_movies_fragment.visibility = View.GONE
            }
        })
    }

    override fun onResume() {
        super.onResume()

        if(view == null){
            return
        }
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
        view!!.setOnKeyListener { view, i, keyEvent ->
            if(keyEvent.action == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_BACK){
                if (imageViewStarOn.visibility == View.VISIBLE){
                    viewModel.insertMovie(moviesEntity)
                }else{
                    viewModel.deleteMovie(moviesEntity)
                }
                viewModel.back.observe(viewLifecycleOwner, Observer {
                    if(it==true){
                        true
                    }
                })
            }

            false
        }
    }

    fun loadData(it: MovieByIdResponse){
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+it.backdrop_path).into(iv_back_movies_details_movies_fragment)
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+it.poster_path).into(iv_posster_movies_details_fragment)
        tv_name_movies_details_fragment.text = it.title

        var genres: String = ""
        it.genres.get(it.genres.size-1)
        for (i in it.genres){
            if (it.genres.get(it.genres.size-1) == i){
                genres+=i.name
            }else{
                genres+=i.name+", "
            }
        }

        tv_genres_movies_details_fragment.text = genres
        tv_overview_movie_detail_fragment.text = it.overview

        moviesEntity = MoviesEntity(it.adult, it.backdrop_path, genres, it.id, it.overview, it.poster_path, it.title, it.vote_average)
    }
}
