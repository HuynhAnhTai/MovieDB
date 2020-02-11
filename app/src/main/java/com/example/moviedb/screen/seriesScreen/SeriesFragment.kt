package com.example.moviedb.seriesScreen

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.moviedb.R
import com.example.moviedb.adapter.MoviesAdapter
import com.example.moviedb.adapter.MoviesClick
import com.example.moviedb.adapter.SeriesClick
import com.example.moviedb.adapter.SeriessAdapter
import com.example.moviedb.modelAPI.SeriesTopRatedResults
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class SeriesFragment : Fragment() {
    private lateinit var seriesAdapter: SeriessAdapter
    private lateinit var recyclerView: RecyclerView
    companion object {
        fun newInstance() = SeriesFragment()
    }

    private lateinit var viewModel: SeriesViewModel
    private var data: MutableList<SeriesTopRatedResults> = ArrayList()

    private lateinit var progressBar: ProgressBar
    private lateinit var progressBarLoad: ProgressBar
    private lateinit var imageViewNoInternet: ImageView
    private lateinit var buttonRetry: Button

    private var initiate = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.series_fragment, container, false)

        progressBar = view.findViewById(R.id.progressBar_series)
        progressBarLoad = view.findViewById(R.id.progressBar_load_series)
        recyclerView = view.findViewById(R.id.recyler_view_series_fragment)
        imageViewNoInternet = view.findViewById(R.id.iv_no_internet_series)
        buttonRetry = view.findViewById(R.id.bt_retry_series)

        if(data.size==0){
            progressBarLoad.visibility = View.VISIBLE
        }

        if (!checkNetworkAvailable()){
            Picasso.get().load("https://art.pixilart.com/c448e718203e765.png")
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageViewNoInternet)
            imageViewNoInternet.visibility = View.VISIBLE
            progressBarLoad.visibility = View.GONE
            buttonRetry.visibility = View.VISIBLE
        }

        buttonRetry.setOnClickListener {
            if(checkNetworkAvailable()) {
                progressBarLoad.visibility = View.VISIBLE
                imageViewNoInternet.visibility = View.GONE
                buttonRetry.visibility = View.GONE
                viewModel.getSeriesTopRated()
            }else{
                Toast.makeText(context,"No internet",Toast.LENGTH_SHORT).show()
            }
        }
        if (!initiate) {
            seriesAdapter = SeriessAdapter(SeriesClick {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            })
        }
        recyclerView.adapter = seriesAdapter

        recyclerView.layoutManager = GridLayoutManager(context,3)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (! recyclerView!!.canScrollVertically(1)){ //1 for down
                    progressBar.visibility = View.VISIBLE
                    viewModel.getSeriesTopRated()
                }
            }
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(SeriesViewModel::class.java)

        viewModel.series.observe(viewLifecycleOwner, Observer {
            if (it.results.size>0) {
                for (i in it.results){
                    data.add(i)
                }
                initiate = true
                seriesAdapter.submitList(data)
                progressBar.visibility = View.GONE
                progressBarLoad.visibility = View.GONE
                imageViewNoInternet.visibility = View.GONE
            }else{
                progressBar.visibility = View.GONE
                progressBarLoad.visibility = View.GONE
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
}
