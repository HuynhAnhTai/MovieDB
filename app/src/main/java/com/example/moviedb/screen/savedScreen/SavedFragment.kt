package com.example.moviedb.screen.savedScreen

import android.content.Context
import android.content.SharedPreferences
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

@Suppress("DEPRECATION")
class SavedFragment : Fragment() {

    companion object {
        fun newInstance() = SavedFragment()
    }
    private lateinit var viewModel: SavedViewModel

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: SaveMovieAdapter

    private var initiate = false
    private var initiateFirebase = false

    private lateinit var sharedPref : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.saved_fragment, container, false)

        sharedPref = activity?.getSharedPreferences("id",Context.MODE_PRIVATE)!!

        recyclerView = view.findViewById(R.id.recyler_view_save_fragment)

        if (!initiate) {
            adapter = SaveMovieAdapter(SaveMoviesClick {
                this.findNavController()
                    .navigate(BeginFragmentDirections.actionBeginFragmentToDetailSaveFragment(it))
            })
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context,3)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(SavedViewModel::class.java)

        var listId: String = ""

        viewModel.movieSave.observe(viewLifecycleOwner, Observer {
            if (it.size >= 0){

                if (!initiateFirebase) {
                    viewModel.checkDataFirebase()
                    viewModel.conpareFlim(it)
                    initiateFirebase = true
                }

                adapter.submitList(it)
                initiate = true
                for (i in it){
                    if (it.get(it.size-1).id == i.id){
                        listId = i.id.toString()
                    }else{
                        listId = i.id.toString()+","
                    }
                }
                with(sharedPref.edit()) {
                    putString("IdSaveMovie", listId)
                    commit()
                }
            }
        })
    }


}
