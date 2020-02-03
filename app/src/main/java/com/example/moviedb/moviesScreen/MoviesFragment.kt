package com.example.moviedb.moviesScreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.moviedb.R
import com.example.moviedb.adapter.MoviesAdapter
import com.example.moviedb.adapter.MoviesClick
import com.example.moviedb.beginScreen.BeginFragmentDirections
import com.example.moviedb.db.FilterEntity
import com.example.moviedb.modelAPI.MoviesTopRatedResults
import java.text.SimpleDateFormat

class MoviesFragment : Fragment() {
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = MoviesFragment()
    }
    private lateinit var viewModelFactory: MoviesViewModelFactory
    private lateinit var viewModel: MoviesViewModel

    private lateinit var filter: FilterEntity

    private var parser = SimpleDateFormat("yyyy-MM-dd")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.movies_fragment, container, false)

        recyclerView = view.findViewById(R.id.recyler_view_movies_fragment)


        moviesAdapter = MoviesAdapter(MoviesClick {
            view.findNavController().navigate(BeginFragmentDirections.actionBeginFragmentToDetailMoviesFragment(it))
        })
        recyclerView.adapter = moviesAdapter

        recyclerView.layoutManager = GridLayoutManager(context,3)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFactory = MoviesViewModelFactory(context!!)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.filter_all.observe(viewLifecycleOwner, Observer {
            filter = it
            viewModel.getFilter()
        })
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            if(it.size>0){
                filterMovies(it)

            }
        })
    }

    private fun filterMovies(it: List<MoviesTopRatedResults>) {
        var temp = listOf<MoviesTopRatedResults>()
        when(filter.sortBy){
            "popular"-> temp = it.sortedWith(compareByDescending { it.popularity })
            "rated"->temp = it.sortedWith(compareByDescending { it.vote_average })
            "date"->temp = it.sortedWith(compareByDescending { parser.parse(it.release_date) })
            else->temp = it.sortedWith(compareByDescending { it.title })
        }

        if(filter.startTime.equals("")){
            moviesAdapter.submitList(temp)
        }else{
            var startTime = parser.parse(filter.startTime)
            var endTime = parser.parse(filter.endTime)

            var data: MutableList<MoviesTopRatedResults> = ArrayList()

            for (i in temp){
                var releaseTime = parser.parse(i.release_date)
                if(startTime<=releaseTime && releaseTime<=endTime){
                    data.add(i)
                }
            }
            moviesAdapter.submitList(data)
        }

    }


}
