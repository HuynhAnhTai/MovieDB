package com.example.moviedb.peopleScreen.detailInformationPeople

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.example.moviedb.R
import com.example.moviedb.modelAPI.PersonInfoResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_information_people_fragment.*

@Suppress("DEPRECATION")
class DetailInformationPeopleFragment : Fragment() {

    companion object {
        fun newInstance() = DetailInformationPeopleFragment()
    }

    private lateinit var viewModel: DetailInformationPeopleViewModel

    private lateinit var image_back: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.detail_information_people_fragment, container, false)

        image_back = view.findViewById(R.id.iv_back_details_information_people_fragment)

        image_back.setOnClickListener {
            this.findNavController().popBackStack()
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailInformationPeopleViewModel::class.java)
        viewModel.id = DetailInformationPeopleFragmentArgs.fromBundle(arguments!!).id
        // TODO: Use the ViewModel
        viewModel.infoPeople.observe(viewLifecycleOwner, Observer {
            if(it.id == DetailInformationPeopleFragmentArgs.fromBundle(arguments!!).id){
                loadData(it)
            }

        })
    }

    private fun loadData(it: PersonInfoResponse) {
        if (it.profile_path!=null){
            Picasso.get().load("https://image.tmdb.org/t/p/w500/"+it.profile_path)
                .centerCrop()
                .fit()
                .into(iv_picture_profile_detail_information_people_fragment)
        }else{
            Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSuErL5FJbhFsb_E5fB7HI5uxuDn3EaxiJfDXxeqjZW" +
                    "CMSgwGJ7&s")
                .centerCrop()
                .fit()
                .into(iv_picture_profile_detail_information_people_fragment)
        }

        tv_name_people_detail_information_fragment.text = it.name

        if (it.place_of_birth == null) {
            tv_place_of_birth_detail_information_fragment.text =
                "Place of Birth: Still Update"
        }else{
            tv_place_of_birth_detail_information_fragment.text =
                "Place of Birth: " + it.place_of_birth
        }

        if (it.birthday == null) {
            tv_birthday_detail_information_fragment.text = "Birthday: Still Update"
        }else{
            tv_birthday_detail_information_fragment.text = "Birthday: " + it.birthday
        }

        if (it.biography.equals("")) {
            tv_overview_people_detail_fragment.text = "Overview Still Update"
        }else{
            tv_overview_people_detail_fragment.text = it.biography
        }
        scroll_view_detail_people_fragment.visibility = View.VISIBLE
        progressBar_load_detail_people_fragment.visibility = View.GONE
    }


}
