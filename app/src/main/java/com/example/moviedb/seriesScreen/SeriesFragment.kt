package com.example.moviedb.seriesScreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.moviedb.R
import com.example.moviedb.adapter.MoviesAdapter
import com.example.moviedb.adapter.MoviesClick
import com.example.moviedb.adapter.SeriesClick
import com.example.moviedb.adapter.SeriessAdapter

class SeriesFragment : Fragment() {
    private lateinit var seriesAdapter: SeriessAdapter
    private lateinit var recyclerView: RecyclerView
    companion object {
        fun newInstance() = SeriesFragment()
    }

    private lateinit var viewModel: SeriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.series_fragment, container, false)
        recyclerView = view.findViewById(R.id.recyler_view_series_fragment)

        seriesAdapter = SeriessAdapter(SeriesClick {
            Toast.makeText(context,it, Toast.LENGTH_LONG).show()
        })
        recyclerView.adapter = seriesAdapter

        recyclerView.layoutManager = GridLayoutManager(context,3)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SeriesViewModel::class.java)

        viewModel.series.observe(viewLifecycleOwner, Observer {
            if(it.total_results>0){
                if (it.results.size>0) {
                    seriesAdapter.submitList(it.results)
                }
            }
        })
    }

}
