package com.example.moviedb.filterScreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast

import com.example.moviedb.R
import kotlinx.android.synthetic.main.filter_fragment.*
import android.app.DatePickerDialog
import android.widget.TextView
import com.example.moviedb.db.FilterEntity
import java.util.*


class FilterFragment : Fragment() {


    companion object {
        fun newInstance() = FilterFragment()
    }
    private lateinit var viewModelFactory: FilterViewModelFactory
    private lateinit var viewModel: FilterViewModel

    private lateinit var radioGroupSort: RadioGroup
    private lateinit var radioGroupDates: RadioGroup

    private lateinit var bt_action: Button
    private lateinit var bt_animation: Button
    private lateinit var bt_comedy: Button
    private lateinit var bt_crime: Button
    private lateinit var bt_document: Button
    private lateinit var bt_drama: Button
    private lateinit var bt_family: Button
    private lateinit var bt_kids: Button
    private lateinit var bt_mystery: Button
    private lateinit var bt_news: Button
    private lateinit var bt_reality: Button
    private lateinit var bt_sci: Button
    private lateinit var bt_soap: Button
    private lateinit var bt_talk: Button
    private lateinit var bt_war: Button
    private lateinit var bt_western: Button
    private lateinit var bt_star_time: Button
    private lateinit var bt_end_time: Button

    private lateinit var tv_to: TextView

    private var yearStart: Int = Calendar.getInstance().get(Calendar.YEAR)
    private var monthStart: Int = Calendar.getInstance().get(Calendar.MONTH)
    private var dateStart: Int = Calendar.getInstance().get(Calendar.DATE)
    private var yearEnd: Int = Calendar.getInstance().get(Calendar.YEAR)
    private var monthEnd: Int = Calendar.getInstance().get(Calendar.MONTH)
    private var dateEnd: Int = Calendar.getInstance().get(Calendar.DATE)

    private var sortBy: String = ""
    private var genres: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.filter_fragment, container, false)

        radioGroupSort = view.findViewById(R.id.rg_sort_by_filter_fragment)
        radioGroupDates = view.findViewById(R.id.rg_dates_filter_fragment)

        bt_star_time = view.findViewById(R.id.bt_start_time_picker_filter_fragment)
        bt_end_time = view.findViewById(R.id.bt_end_time_filter_fragment)

        bt_action = view.findViewById(R.id.bt_action_filter_fragment)
        bt_action.setOnClickListener {onClick(bt_action)}

        bt_animation = view.findViewById(R.id.bt_animation_filter_fragment)
        bt_animation.setOnClickListener {onClick(bt_animation)}

        bt_comedy = view.findViewById(R.id.bt_comedy_filter_fragment)
        bt_comedy.setOnClickListener {onClick(bt_comedy)}

        bt_crime = view.findViewById(R.id.bt_crime_filter_fragment)
        bt_crime.setOnClickListener {onClick(bt_crime)}

        bt_document = view.findViewById(R.id.bt_documentary_filter_fragment)
        bt_document.setOnClickListener {onClick(bt_document)}

        bt_drama = view.findViewById(R.id.bt_drama_filter_fragment)
        bt_drama.setOnClickListener {onClick(bt_drama)}

        bt_family = view.findViewById(R.id.bt_family_filter_fragment)
        bt_family.setOnClickListener {onClick(bt_family)}

        bt_kids = view.findViewById(R.id.bt_kids_filter_fragment)
        bt_kids.setOnClickListener {onClick(bt_kids)}

        bt_mystery = view.findViewById(R.id.bt_mystery_filter_fragment)
        bt_mystery.setOnClickListener {onClick(bt_mystery)}

        bt_news = view.findViewById(R.id.bt_news_filter_fragment)
        bt_news.setOnClickListener {onClick(bt_news)}

        bt_reality = view.findViewById(R.id.bt_reality_filter_fragment)
        bt_reality.setOnClickListener {onClick(bt_reality)}

        bt_sci = view.findViewById(R.id.bt_sci_filter_fragment)
        bt_sci.setOnClickListener {onClick(bt_sci)}

        bt_soap = view.findViewById(R.id.bt_soap_filter_fragment)
        bt_soap.setOnClickListener {onClick(bt_soap)}

        bt_talk = view.findViewById(R.id.bt_talk_filter_fragment)
        bt_talk.setOnClickListener {onClick(bt_talk)}

        bt_war = view.findViewById(R.id.bt_war_filter_fragment)
        bt_war.setOnClickListener {onClick(bt_war)}

        bt_western = view.findViewById(R.id.bt_western_filter_fragment)
        bt_western.setOnClickListener {onClick(bt_western)}

        tv_to = view.findViewById(R.id.tv_to)

        bt_star_time.setText(dateStart.toString() + "-" + (monthStart + 1) + "-" + yearStart)
        bt_end_time.setText(dateEnd.toString() + "-" + (monthEnd + 1) + "-" + yearEnd)

        bt_star_time.setOnClickListener {
            onClickDateTimePicker(bt_star_time)
        }

        bt_end_time.setOnClickListener {
            onClickDateTimePicker(bt_end_time)
        }

        radioGroupSort.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.rb_most_popular_filter_fragment -> sortBy = "popular"
                R.id.rb_best_rated_filter_fragment -> sortBy = "rated"
                R.id.rb_release_date_filter_fragment -> sortBy = "date"
                else -> sortBy = "order"
            }
        }

        radioGroupDates.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.rb_in_theatre_filter_fragment -> {
                    bt_start_time_picker_filter_fragment.visibility = View.GONE
                    bt_end_time_filter_fragment.visibility = View.GONE
                    tv_to.visibility = View.GONE
                }
                else -> {
                    bt_start_time_picker_filter_fragment.visibility = View.VISIBLE
                    bt_end_time_filter_fragment.visibility = View.VISIBLE
                    tv_to.visibility = View.VISIBLE
                }
            }
        }

        return view
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFactory = FilterViewModelFactory(context!!)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FilterViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.filter.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it.id == 1){
                loadData(it)
            }
        })
    }

    private fun loadData(it: FilterEntity) {
        when(it.sortBy){
            "popular" -> radioGroupSort.check(R.id.rb_most_popular_filter_fragment)
            "rated" -> radioGroupSort.check(R.id.rb_best_rated_filter_fragment)
            "date" -> radioGroupSort.check(R.id.rb_release_date_filter_fragment)
            else -> radioGroupSort.check(R.id.rb_alphabetic_order_filter_fragment)
        }

        when(it.startTime){
            ""-> radioGroupDates.check(R.id.rb_in_theatre_filter_fragment)
            else ->{
                radioGroupDates.check(R.id.rb_between_two_dates_filter_fragment)
                bt_star_time.text = it.startTime
                bt_end_time.text = it.endTime
            }
        }

        var genres1 = it.genres.split(",")
        genres = it.genres
        if (genres1.contains("action & adventure")){
            bt_action.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("animation")){
            bt_animation.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("comedy")){
            bt_comedy.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("crime")){
            bt_crime.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("documentary")){
            bt_document.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("drama")){
            bt_drama.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("family")){
            bt_family.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("kids")){
            bt_kids.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("mystery")){
            bt_mystery.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("news")){
            bt_news.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("reality")){
            bt_reality.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("sci-fi & fantasy")){
            bt_sci.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("soap")){
            bt_soap.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("talk")){
            bt_talk.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("war & politics")){
            bt_war.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("western")){
            bt_western.setTextColor(resources.getColor(R.color.red))
        }
    }

    fun onClick(p0: Button) {
        if(p0.currentTextColor == -16185336){
            p0.setTextColor(resources.getColor(R.color.red))
            genres+=","+p0.text.toString()
        }else{
            p0.setTextColor(resources.getColor(R.color.black))
            genres = genres.replace(","+p0.text,"")
        }
    }

    fun onClickDateTimePicker(p0: Button){
        when(p0.id){
            R.id.bt_start_time_picker_filter_fragment -> {
                val c = Calendar.getInstance()
                yearStart = c.get(Calendar.YEAR)
                monthStart = c.get(Calendar.MONTH)
                dateStart = c.get(Calendar.DAY_OF_MONTH)


                val datePickerDialog = DatePickerDialog(context!!,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        if(year<=yearEnd && monthOfYear <= monthEnd && dayOfMonth <= dateEnd){
                            bt_star_time.setText(
                                dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                            )
                        }
                    }, yearStart, monthStart, dateStart
                )
                datePickerDialog.show()
            }
            else -> {
                val c = Calendar.getInstance()
                yearEnd = c.get(Calendar.YEAR)
                monthEnd = c.get(Calendar.MONTH)
                dateEnd = c.get(Calendar.DAY_OF_MONTH)


                val datePickerDialog = DatePickerDialog(context!!,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        if(year>=yearStart && monthOfYear >= monthStart && dayOfMonth >= dateStart){
                            bt_end_time.text = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                        }
                    }, yearEnd, monthEnd, dateEnd
                )
                datePickerDialog.show()
            }
        }
    }

    override fun onStop() {
        super.onStop()

        var startTime: String = ""
        var endTime: String = ""

        when(rg_dates_filter_fragment.checkedRadioButtonId){
            R.id.rb_in_theatre_filter_fragment -> {
                startTime = ""
                endTime = ""
            }
            else -> {
                startTime = bt_star_time.text.toString()
                endTime = bt_end_time.text.toString()
            }
        }
        viewModel.insertFilter(sortBy, startTime, endTime,genres)

    }

}
