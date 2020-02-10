package com.example.moviedb.moviesScreen

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.moviedb.R
import com.example.moviedb.adapter.MoviesAdapter
import com.example.moviedb.adapter.MoviesClick
import com.example.moviedb.beginScreen.BeginFragmentDirections
import com.example.moviedb.db.FilterEntity
import com.example.moviedb.modelAPI.MoviesTopRatedResults
import java.text.SimpleDateFormat

@Suppress("DEPRECATION")
class MoviesFragment : Fragment() {
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = MoviesFragment()
    }
    private lateinit var viewModel: MoviesViewModel

    private lateinit var progressBar: ProgressBar

    private var filter: FilterEntity = FilterEntity(0,"","","","")

    private var dataPrimary: MutableList<MoviesTopRatedResults> = ArrayList()

    private var initiate = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.movies_fragment, container, false)

        progressBar = view.findViewById(R.id.progressBar_movie)
        recyclerView = view.findViewById(R.id.recyler_view_movies_fragment)

        if (!initiate){
            moviesAdapter = MoviesAdapter(MoviesClick {
                if(checkNetworkAvailable()) {
                    this.findNavController().navigate(
                        BeginFragmentDirections.actionBeginFragmentToDetailMoviesFragment(it)
                    )
                }else{
                    Toast.makeText(context,"No internet",Toast.LENGTH_SHORT).show()
                }
            })
        }

        recyclerView.adapter = moviesAdapter

        recyclerView.layoutManager = GridLayoutManager(context,3)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (! recyclerView!!.canScrollVertically(1)){ //1 for down
                        progressBar.visibility = View.VISIBLE
                        viewModel.getFilter()
                }
            }
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MoviesViewModel::class.java)

        // TODO: Use the ViewModel
        viewModel.filter_all.observe(viewLifecycleOwner, Observer {
            if (it!=filter && it!=null ) {
                dataPrimary = ArrayList()
                filter = it
                viewModel.getFilter()
                initiate = true
            }else if(it==null){
                dataPrimary = ArrayList()
                viewModel.getFilter()
                initiate = true
            }
        })
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            if(it.size>0){
                for (i in it){
                    if (i==null){
                        continue
                    }
                    dataPrimary.add(i)
                }
                //viewModel.done()
                moviesAdapter.submitList(dataPrimary)
                progressBar.visibility = View.GONE
            }
            else if(it.size==0){
                progressBar.visibility = View.GONE
            }
        })
    }

    fun checkNetworkAvailable(): Boolean {
        val connectivityManager = activity!!.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    override fun onStop() {
        super.onStop()
        viewModel.moviePage = 0
        viewModel.movieTheaterPage = 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
