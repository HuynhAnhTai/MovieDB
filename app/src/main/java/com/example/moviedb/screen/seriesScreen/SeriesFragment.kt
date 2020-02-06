package com.example.moviedb.seriesScreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class SeriesFragment : Fragment() {
    private lateinit var seriesAdapter: SeriessAdapter
    private lateinit var recyclerView: RecyclerView
    companion object {
        fun newInstance() = SeriesFragment()
    }

    private lateinit var viewModel: SeriesViewModel
    private var data: MutableList<SeriesTopRatedResults> = ArrayList()

    private lateinit var progressBar: ProgressBar

    private var initiate = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.series_fragment, container, false)

        progressBar = view.findViewById(R.id.progressBar_series)

        recyclerView = view.findViewById(R.id.recyler_view_series_fragment)

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
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}
