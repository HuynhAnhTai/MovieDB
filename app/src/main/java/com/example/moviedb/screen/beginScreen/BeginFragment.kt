package com.example.moviedb.beginScreen

import android.content.ClipData
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager

import com.example.moviedb.R
import com.example.moviedb.adapter.PagerAdapter
import com.example.moviedb.moviesScreen.MoviesFragment
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import java.lang.NullPointerException


@Suppress("DEPRECATION")
class BeginFragment : Fragment() {

    companion object {
        fun newInstance() = BeginFragment()
    }
    private lateinit var viewModel: BeginViewModel

    private lateinit var tabLayout: TabLayout

    private lateinit var viewPager: ViewPager

    private lateinit var adapter: PagerAdapter

    private lateinit var toolbar: Toolbar

    private lateinit var itemSearch: MenuItem
    private lateinit var itemFilter: MenuItem

    private lateinit var sharedPref :SharedPreferences

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions

    private  var item: Int = 5

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.begin_fragment, container, false)

        tabLayout = view.findViewById(R.id.tab_layout_begin_fragment)
        viewPager = view.findViewById(R.id.view_pager_begin_fragment)
        toolbar = view.findViewById(R.id.tool_bar_begin_fragment)

        itemSearch = toolbar.menu.findItem(R.id.menu_search)
        itemFilter = toolbar.menu.findItem(R.id.menu_filter)

        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!

        gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(activity!!,gso)

        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_search -> {
                    this.findNavController().navigate(BeginFragmentDirections.actionBeginFragmentToSearchFragment())
                    true
                }
                R.id.menu_filter -> {
                    this.findNavController().navigate(BeginFragmentDirections.actionBeginFragmentToFilterFragment())
                    true
                }
                R.id.menu_sign_out->{

                    viewModel.deleteAllMovieDB()
                    mAuth.signOut()
                    LoginManager.getInstance().logOut()
                    mGoogleSignInClient.signOut()
                    this.findNavController().navigate(BeginFragmentDirections.actionBeginFragmentToLoginFragment())

                    true
                }
                else -> {
                    if (sharedPref != null) {
                        val type = sharedPref.getInt("Type", 3)
                        if (type == 3) {
                            with(sharedPref.edit()) {
                                putInt("Type", 1)
                                commit()
                            }
                            viewModel.updateType(1)
                        }else{
                            with(sharedPref.edit()) {
                                putInt("Type", 3)
                                commit()
                            }
                            viewModel.updateType(3)
                        }
                    }
                    true
                }
            }
       }

        adapter = PagerAdapter(childFragmentManager)

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        viewPager.offscreenPageLimit = 4
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                var position = tab.position
                if (position==0){
                    itemSearch.setVisible(true)
                    itemFilter.setVisible(true)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                var position = tab.position
                if (position==0){
                    itemSearch.setVisible(false)
                    itemFilter.setVisible(false)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(BeginViewModel::class.java)
        // TODO: Use the ViewModel
        try {
            if (sharedPref!!.getInt("Type", 3) != 1) {
                with(sharedPref!!.edit()) {
                    putInt("Type", 3)
                    commit()
                }
                viewModel.updateType(3)
            } else {
                viewModel.updateType(1)
            }
        }catch (e: NullPointerException){
            with(sharedPref!!.edit()) {
                putInt("Type", 3)
                commit()
            }
            viewModel.updateType(3)
        }
        item++
    }
}
