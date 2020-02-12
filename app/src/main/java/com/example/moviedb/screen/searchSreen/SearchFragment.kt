package com.example.moviedb.screen.searchSreen

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
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
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedb.modelAPI.MoviesTopRatedResults
import kotlinx.android.synthetic.main.filter_fragment.*


@Suppress("DEPRECATION")
class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    private lateinit var et_search_name: EditText
    private lateinit var recyclerview: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var moviesAdapter: MoviesAdapter

    private var dataPrimary = ArrayList<MoviesTopRatedResults>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.search_fragment, container, false)

        getActivity()!!.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        et_search_name = view.findViewById(R.id.et_search_fragment)
        recyclerview = view.findViewById(R.id.recyler_view_search_fragment)
        progressBar = view.findViewById(R.id.progressBar_movie_search)

        moviesAdapter = MoviesAdapter(3,MoviesClick {
            if (checkNetworkAvailable()) {
                view.findNavController()
                    .navigate(SearchFragmentDirections.actionSearchFragmentToDetailMoviesFragment(it))
            }else{
                Toast.makeText(context,"No Internet", Toast.LENGTH_SHORT).show()
            }
        })
        recyclerview.adapter = moviesAdapter

        recyclerview.layoutManager = GridLayoutManager(context,3)

        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (! recyclerView!!.canScrollVertically(1)){ //1 for down
                    if(checkNetworkAvailable()) {
                        progressBar.visibility = View.VISIBLE
                        viewModel.getMoviesSearch(et_search_name.text.toString())
                    }
                }
            }
        })

        et_search_name.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH){
                hideSoftKeyBoard(activity!!)
                if(checkNetworkAvailable()) {
                    viewModel.page = 0
                    progressBar.visibility = View.VISIBLE
                    viewModel.getMoviesSearch(et_search_name.text.toString())
                }else{
                    Toast.makeText(context,"No Internet",Toast.LENGTH_SHORT).show()
                }
                true
            }
            false
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            if (it.size>0) {
                for (i in it){
                    if (i!=null){
                        dataPrimary.add(i)
                    }
                }
                moviesAdapter.submitList(dataPrimary)
                progressBar.visibility = View.GONE
            }
        })
    }

    override fun onResume() {
        super.onResume()
        et_search_name.requestFocus()
        var inputManager: InputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(et_search_name, InputMethodManager.SHOW_IMPLICIT)

    }

    fun hideSoftKeyBoard(activity: Activity){
        if(activity.currentFocus == null){
            return
        }
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity!!.currentFocus!!.windowToken, 0)
        dataPrimary = ArrayList<MoviesTopRatedResults>()
    }

    fun checkNetworkAvailable(): Boolean {
        val connectivityManager = activity!!.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }
}
