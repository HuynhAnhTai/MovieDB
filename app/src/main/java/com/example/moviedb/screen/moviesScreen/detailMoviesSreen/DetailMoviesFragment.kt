package com.example.moviedb.moviesScreen.detailMoviesSreen

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.widget.Toast


@Suppress("DEPRECATION")
class DetailMoviesFragment : Fragment() {

    companion object {
        fun newInstance() = DetailMoviesFragment()
    }

    private lateinit var viewModel: DetailMoviesViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageViewStarOff: ImageView
    private lateinit var imageViewStarOn: ImageView
    private lateinit var textViewStillUpdate: TextView
    private lateinit var bt_trailer: Button

    private lateinit var adapter: CastMovieAdapter

    private var urlTrailer: String = ""

    private var moviesEntity: MoviesEntity = MoviesEntity(false,"","",0,"","","",0F)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.detail_movies_fragment, container, false)

        recyclerView = view.findViewById(R.id.recyler_view_cast_detail_movie_fragment)

        imageViewStarOff = view.findViewById(R.id.iv_start_off_detal_movies_fragment)
        imageViewStarOn = view.findViewById(R.id.iv_start_on_detal_movies_fragment)
        textViewStillUpdate = view.findViewById(R.id.tv_still_update_details_movies_framgnet)
        bt_trailer = view.findViewById(R.id.bt_trailer_video_detals_movies_fragment)

        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)

        adapter = CastMovieAdapter(CasterClick {
            this.findNavController().navigate(DetailMoviesFragmentDirections.actionDetailMoviesFragmentToDetailInformationPeopleFragment(it))
        })
        recyclerView.adapter = adapter

        imageViewStarOff.setOnClickListener {
            iv_start_off_detal_movies_fragment.visibility = View.GONE
            iv_start_on_detal_movies_fragment.visibility = View.VISIBLE
        }

        imageViewStarOn.setOnClickListener {
            iv_start_off_detal_movies_fragment.visibility = View.VISIBLE
            iv_start_on_detal_movies_fragment.visibility = View.GONE

        }

        bt_trailer.setOnClickListener {
            if(checkNetworkAvailable()) {
                if (urlTrailer.equals("")){
                    Toast.makeText(context,"No Available Trailer", Toast.LENGTH_SHORT).show()
                }else{
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + urlTrailer)
                        )
                    )
                }
            }else{
                Toast.makeText(context,"No Internet", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailMoviesViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel._id = DetailMoviesFragmentArgs.fromBundle(arguments!!).id

        viewModel.detailMovie.observe(viewLifecycleOwner, Observer {
            if (it.id == DetailMoviesFragmentArgs.fromBundle(arguments!!).id){
                loadData(it)
            }
        })

        viewModel.castMovie.observe(viewLifecycleOwner, Observer {
            if(it.id == DetailMoviesFragmentArgs.fromBundle(arguments!!).id ){
                if (it.cast.size>0){
                    adapter.submitList(it.cast)
                    recyclerView.visibility = View.VISIBLE
                    textViewStillUpdate.visibility = View.GONE
                }else{
                    recyclerView.visibility = View.GONE
                    textViewStillUpdate.visibility = View.VISIBLE
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
        var backdrop_path: String = ""
        if (it.backdrop_path==null){
            Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTX70gC9QJxGtg6XcQEb4t793LTfR8M5nOcJ-ZoxW6ZNI29B93N").fit().into(iv_back_movies_details_movies_fragment)
            backdrop_path = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTX70gC9QJxGtg6XcQEb4t793LTfR8M5nOcJ-ZoxW6ZNI29B93N"
        }else{
            Picasso.get().load("https://image.tmdb.org/t/p/w500/"+it.backdrop_path).fit().into(iv_back_movies_details_movies_fragment)
            backdrop_path = it.backdrop_path
        }

        var poster_path: String = ""
        if ( it.poster_path == null){
            poster_path = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTX70gC9QJxGtg6XcQEb4t793LTfR8M5nOcJ-ZoxW6ZNI29B93N"
            Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTX70gC9QJxGtg6XcQEb4t793LTfR8M5nOcJ-ZoxW6ZNI29B93N").fit().into(iv_posster_movies_details_fragment)
        }else{
            Picasso.get().load("https://image.tmdb.org/t/p/w500/"+it.poster_path).fit().into(iv_posster_movies_details_fragment)
            poster_path = it.poster_path
        }

        tv_name_movies_details_fragment.text = it.title

        var genres: String = ""
        if (it.genres.size>0){
            for (i in it.genres){
                if (it.genres.get(it.genres.size-1) == i){
                    genres+=i.name
                }else{
                    genres+=i.name+", "
                }
            }
            tv_genres_movies_details_fragment.text = genres
        }else{
            tv_genres_movies_details_fragment.text = "Still update genres"
            genres = "Still update genres"
        }

        var overview: String = ""
        if ( it.overview.equals("")){
            tv_overview_movie_detail_fragment.text = "We still update overview"
            overview = "We still update overview"
        }else{
            tv_overview_movie_detail_fragment.text = it.overview
            overview = it.overview
        }

        if (it.videos.results.size>0){
            urlTrailer = it.videos.results.get(0).key
        }

        moviesEntity = MoviesEntity(it.adult,backdrop_path, genres, it.id, overview, poster_path, it.title, it.vote_average)
    }

    fun checkNetworkAvailable(): Boolean {
        val connectivityManager = activity!!.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }
}
