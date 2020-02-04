package com.example.moviedb.moviesScreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
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

    private lateinit var progressBar: ProgressBar

    private var filter: FilterEntity = FilterEntity(0,"","","","")

    private var parser = SimpleDateFormat("yyyy-MM-dd")

    private var dataPrimary: MutableList<MoviesTopRatedResults> = ArrayList()

    private var hasScroll: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.movies_fragment, container, false)

        progressBar = view.findViewById(R.id.progressBar_movie)
        recyclerView = view.findViewById(R.id.recyler_view_movies_fragment)


        moviesAdapter = MoviesAdapter(MoviesClick {
            view.findNavController().navigate(BeginFragmentDirections.actionBeginFragmentToDetailMoviesFragment(it))
        })

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
        viewModelFactory = MoviesViewModelFactory(context!!)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel::class.java)

        // TODO: Use the ViewModel
        viewModel.filter_all.observe(viewLifecycleOwner, Observer {
            if (it!=null) {
                filter = it
                dataPrimary = ArrayList<MoviesTopRatedResults>()
                viewModel.getFilter()
            }
        })
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            if(it.size>0){
                filterMovies(it)
            }
        })
    }

    private fun filterMovies(it: List<MoviesTopRatedResults>) {


        var genres = filter.genres.split(",")

        var temp2 = ArrayList<MoviesTopRatedResults>()

        if (genres.size>1) {
            for (i in it) {
                for (t in i.genre_ids) {
                    if (t.toString() in genres) {
                        temp2.add(i)
                        dataPrimary.add(i)
                        break
                    }
                }
            }

            for (i in dataPrimary) {
                var flag: Int = 0
                for (t in i.genre_ids) {
                    if (t.toString() in genres) {
                        flag = 1
                        break
                    }
                }
                if (flag == 0){
                    dataPrimary.remove(i)
                }
            }
        }else{
            for (i in it){
                temp2.add(i)
            }
        }
        if(filter.startTime.equals("now")){
            for (i in temp2){
                if (!dataPrimary.contains(i)){
                    dataPrimary.add(i)
                }
            }
            moviesAdapter.submitList(filter(dataPrimary))
        }else{
            var startTime = parser.parse(filter.startTime)
            var endTime = parser.parse(filter.endTime)

            var data: MutableList<MoviesTopRatedResults> = ArrayList()

            for (i in temp2){
                var releaseTime = parser.parse(i.release_date)
                if(startTime<=releaseTime && releaseTime<=endTime){
                    data.add(i)
                }
            }

            for (i in data){
                if(!dataPrimary.contains(i)){
                    dataPrimary.add(i)
                }
            }
            moviesAdapter.submitList(filter(dataPrimary))
        }
        progressBar.visibility = View.GONE
    }

    private fun filter(it: List<MoviesTopRatedResults>) : List<MoviesTopRatedResults>{
        when(filter.sortBy){
            "popular"-> return it.sortedWith(compareByDescending { it.popularity })
            "rated"-> return it.sortedWith(compareByDescending { it.vote_average })
            "date"-> return it.sortedWith(compareByDescending { parser.parse(it.release_date) })
            else-> return it.sortedWith(compareBy { it.title })
        }
    }

    override fun onStop() {
        super.onStop()
        hasScroll = false
        viewModel.moviePage = 0
        viewModel.movieTheaterPage = 0
    }
}
