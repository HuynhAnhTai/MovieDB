package com.example.moviedb.screen.searchSreen

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.moviedb.R
import com.example.moviedb.adapter.MoviesAdapter
import com.example.moviedb.adapter.MoviesClick
import com.example.moviedb.beginScreen.BeginFragmentDirections
import androidx.core.content.ContextCompat.getSystemService



class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    private lateinit var et_search_name: EditText
    private lateinit var bt_search: Button
    private lateinit var recyclerview: RecyclerView

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.search_fragment, container, false)

        et_search_name = view.findViewById(R.id.et_search_fragment)
        bt_search = view.findViewById(R.id.bt_search_fragment)
        recyclerview = view.findViewById(R.id.recyler_view_search_fragment)

        moviesAdapter = MoviesAdapter(MoviesClick {
            view.findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailMoviesFragment(it))
        })
        recyclerview.adapter = moviesAdapter

        recyclerview.layoutManager = GridLayoutManager(context,3)

        bt_search.setOnClickListener {
            if(et_search_name.text.toString().length>0){
                viewModel.getMoviesSearch(et_search_name.text.toString())
                hideSoftKeyBoard(activity!!)
            }
        }

        return view
    }

    fun hideSoftKeyBoard(activity: Activity){
        if(activity.currentFocus == null){
            return
        }
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity!!.currentFocus!!.windowToken, 0)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            moviesAdapter.submitList(it)
        })
    }

}
