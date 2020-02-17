package com.example.moviedb.moviesScreen

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.moviedb.R
import com.example.moviedb.adapter.MoviesAdapter
import com.example.moviedb.adapter.MoviesClick
import com.example.moviedb.beginScreen.BeginFragmentDirections
import com.example.moviedb.beginScreen.BeginViewModel
import com.example.moviedb.db.FilterEntity
import com.example.moviedb.modelAPI.MoviesTopRatedResults
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import kotlin.properties.Delegates

@Suppress("DEPRECATION")
class MoviesFragment : Fragment() {
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = MoviesFragment()
    }
    private lateinit var viewModel: MoviesViewModel
    private lateinit var viewModelBegin: BeginViewModel

    private lateinit var progressBar: ProgressBar
    private lateinit var progressBarLoad: ProgressBar
    private lateinit var imageViewNoInternet: ImageView
    private lateinit var buttonRetry: Button

    private var filter: FilterEntity = FilterEntity(0,"","","","")

    private var dataPrimary: MutableList<MoviesTopRatedResults> = ArrayList()

    private var type = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.movies_fragment, container, false)

        progressBar = view.findViewById(R.id.progressBar_movie)
        progressBarLoad = view.findViewById(R.id.progressBar_load_movie)
        recyclerView = view.findViewById(R.id.recyler_view_movies_fragment)
        imageViewNoInternet = view.findViewById(R.id.iv_no_internet_movie)
        buttonRetry = view.findViewById(R.id.bt_retry_movie)

        if (dataPrimary.size == 0) {
            progressBarLoad.visibility = View.VISIBLE
        }

        if (!checkNetworkAvailable()) {
            Picasso.get().load("https://art.pixilart.com/c448e718203e765.png")
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageViewNoInternet)
            imageViewNoInternet.visibility = View.VISIBLE
            progressBarLoad.visibility = View.GONE
            buttonRetry.visibility = View.VISIBLE
        }

        buttonRetry.setOnClickListener {
            if (checkNetworkAvailable()) {
                progressBarLoad.visibility = View.VISIBLE
                imageViewNoInternet.visibility = View.GONE
                buttonRetry.visibility = View.GONE
                viewModel.getFilter()
            } else {
                Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show()
            }
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView!!.canScrollVertically(1)) { //1 for down
                    progressBar.visibility = View.VISIBLE
                    viewModel.getFilter()
                }
            }
        })

        try{
            if(moviesAdapter.itemCount>0){
                recyclerView.adapter = moviesAdapter
                setType()
            }
        }catch (e: UninitializedPropertyAccessException){

        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MoviesViewModel::class.java)
        viewModelBegin = ViewModelProviders.of(activity!!).get(BeginViewModel::class.java)

        // TODO: Use the ViewModel
        viewModelBegin.type.observe(viewLifecycleOwner, Observer {
            if (it!=type) {
                updateTypeRecyclerview(it)
            }
        })

        viewModel.filter_all.observe(viewLifecycleOwner, Observer {
            if (it!=filter && it!=null ) {
                dataPrimary = ArrayList()
                filter = it
                viewModel.movieTheaterPage = 0
                viewModel.moviePage = 0
                viewModel.getFilter()
            }else if(it==null){
                dataPrimary = ArrayList()
                viewModel.getFilter()
            }
        })
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            if(it.size>0) {
                for (i in it) {
                    if (i == null) {
                        continue
                    }
                    dataPrimary.add(i)
                }
                moviesAdapter.submitList(dataPrimary)
                viewModel.done()
                progressBar.visibility = View.GONE
                progressBarLoad.visibility = View.GONE
                imageViewNoInternet.visibility = View.GONE
            }
        })
    }

    private fun updateTypeRecyclerview(it: Int) {
        type = it

        moviesAdapter = MoviesAdapter(type, MoviesClick {
            if (checkNetworkAvailable()) {
                this.findNavController().navigate(
                    BeginFragmentDirections.actionBeginFragmentToDetailMoviesFragment(it)
                )
            } else {
                Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show()
            }
        })

        setType()

        recyclerView.adapter = moviesAdapter
        moviesAdapter.submitList(dataPrimary)
    }

    private fun setType(){
        if (type == 1){
            recyclerView.layoutManager = LinearLayoutManager(context)
        }else{
            recyclerView.layoutManager = GridLayoutManager(context,3)
        }
    }

    fun checkNetworkAvailable(): Boolean {
        val connectivityManager = activity!!.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    override fun onDetach() {
        super.onDetach()
        filter = FilterEntity(0,"","","","")
        dataPrimary = ArrayList()
        type = 0
    }
}
