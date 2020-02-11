package com.example.moviedb.peopleScreen

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.moviedb.R
import com.example.moviedb.adapter.PeoplesAdapter
import com.example.moviedb.adapter.PeoplesClick
import com.example.moviedb.beginScreen.BeginFragmentDirections
import com.example.moviedb.modelAPI.PeoplesPopularResults
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class PeopleFragment : Fragment() {
    private lateinit var peopleAdapter: PeoplesAdapter
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = PeopleFragment()
    }

    private lateinit var viewModel: PeopleViewModel

    private var data: MutableList<PeoplesPopularResults> = ArrayList()
    private lateinit var progressBar: ProgressBar
    private lateinit var progressBarLoad: ProgressBar
    private lateinit var imageViewNoInternet: ImageView

    private var initiate = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.people_fragment, container, false)

        progressBar = view.findViewById(R.id.progressBar_people)
        progressBarLoad = view.findViewById(R.id.progressBar_load_people)
        recyclerView = view.findViewById(R.id.recyler_view_people_fragment)
        imageViewNoInternet = view.findViewById(R.id.iv_no_internet_people)

        if(data.size==0){
            progressBarLoad.visibility = View.VISIBLE
        }

        if(!checkNetworkAvailable()){
            Picasso.get().load("https://art.pixilart.com/c448e718203e765.png")
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageViewNoInternet)
            imageViewNoInternet.visibility = View.VISIBLE
            progressBarLoad.visibility = View.GONE
        }
        if(!initiate) {
            peopleAdapter = PeoplesAdapter(PeoplesClick {
                if(checkNetworkAvailable()) {
                    this.findNavController().navigate(
                        BeginFragmentDirections.actionBeginFragmentToDetailInformationPeopleFragment(
                            it
                        )
                    )
                }else{
                    Toast.makeText(context,"No internet",Toast.LENGTH_SHORT).show()
                }
            })
        }
        recyclerView.adapter = peopleAdapter

        recyclerView.layoutManager = GridLayoutManager(context,3)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (! recyclerView!!.canScrollVertically(1)){ //1 for down
                    progressBar.visibility = View.VISIBLE
                    viewModel.getPeoplePopular()
                }
            }
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(PeopleViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.people.observe(viewLifecycleOwner, Observer {
            if (it.results.size>0) {
                for (i in it.results){
                    data.add(i)
                }
                initiate = true
                peopleAdapter.submitList(data)
                progressBar.visibility = View.GONE
                progressBarLoad.visibility = View.GONE
                imageViewNoInternet.visibility = View.GONE
            }else if(it.results.size==0){
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
