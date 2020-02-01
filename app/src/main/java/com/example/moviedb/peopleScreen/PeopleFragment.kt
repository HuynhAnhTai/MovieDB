package com.example.moviedb.peopleScreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.moviedb.R
import com.example.moviedb.adapter.MoviesAdapter
import com.example.moviedb.adapter.MoviesClick
import com.example.moviedb.adapter.PeoplesAdapter
import com.example.moviedb.adapter.PeoplesClick
import com.example.moviedb.beginScreen.BeginFragmentDirections

class PeopleFragment : Fragment() {
    private lateinit var peopleAdapter: PeoplesAdapter
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = PeopleFragment()
    }

    private lateinit var viewModel: PeopleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.people_fragment, container, false)

        recyclerView = view.findViewById(R.id.recyler_view_people_fragment)

        peopleAdapter = PeoplesAdapter(PeoplesClick {
            this.findNavController().navigate(BeginFragmentDirections.actionBeginFragmentToDetailInformationPeopleFragment(it))
        })
        recyclerView.adapter = peopleAdapter

        recyclerView.layoutManager = GridLayoutManager(context,3)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PeopleViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.people.observe(viewLifecycleOwner, Observer {
            if(it.total_results>0){
                if (it.results.size>0) {
                    peopleAdapter.submitList(it.results)
                }
            }
        })
    }

}
