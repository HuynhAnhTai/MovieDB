package com.example.moviedb.savedScreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.moviedb.R
import com.example.moviedb.adapter.SaveMovieAdapter
import com.example.moviedb.adapter.SaveMoviesClick
import com.example.moviedb.beginScreen.BeginFragmentDirections

class SavedFragment : Fragment() {

    companion object {
        fun newInstance() = SavedFragment()
    }
    private lateinit var viewModelFactory: SavedViewModelFactory
    private lateinit var viewModel: SavedViewModel

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: SaveMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.saved_fragment, container, false)

        recyclerView = view.findViewById(R.id.recyler_view_save_fragment)

        adapter = SaveMovieAdapter(SaveMoviesClick {
            this.findNavController().navigate(BeginFragmentDirections.actionBeginFragmentToDetailSaveFragment(it))
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context,3)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFactory = SavedViewModelFactory(context!!)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SavedViewModel::class.java)

        viewModel.movieSave.observe(viewLifecycleOwner, Observer {
            if (it.size > 0){
                adapter.submitList(it)
            }
        })
    }

}
