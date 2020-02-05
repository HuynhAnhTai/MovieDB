package com.example.moviedb.filterScreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup

import com.example.moviedb.R
import kotlinx.android.synthetic.main.filter_fragment.*
import android.app.DatePickerDialog
import android.view.KeyEvent
import android.widget.TextView
import com.example.moviedb.db.FilterEntity
import java.time.LocalDate
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
    private lateinit var bt_adventure: Button
    private lateinit var bt_mystery: Button
    private lateinit var bt_thriller: Button
    private lateinit var bt_horror: Button
    private lateinit var bt_sci: Button
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

        bt_adventure = view.findViewById(R.id.bt_adventure_filter_fragment)
        bt_adventure.setOnClickListener {onClick(bt_adventure)}

        bt_mystery = view.findViewById(R.id.bt_mystery_filter_fragment)
        bt_mystery.setOnClickListener {onClick(bt_mystery)}

        bt_thriller = view.findViewById(R.id.bt_thriller_filter_fragment)
        bt_thriller.setOnClickListener {onClick(bt_thriller)}

        bt_horror = view.findViewById(R.id.bt_horror_filter_fragment)
        bt_horror.setOnClickListener {onClick(bt_horror)}

        bt_sci = view.findViewById(R.id.bt_sci_filter_fragment)
        bt_sci.setOnClickListener {onClick(bt_sci)}

        bt_war = view.findViewById(R.id.bt_war_filter_fragment)
        bt_war.setOnClickListener {onClick(bt_war)}

        bt_western = view.findViewById(R.id.bt_western_filter_fragment)
        bt_western.setOnClickListener {onClick(bt_western)}

        tv_to = view.findViewById(R.id.tv_to)
        setDataButton(bt_star_time,yearStart,monthStart+1,dateStart)
        setDataButton(bt_end_time,yearEnd,monthEnd+1,dateEnd)

        bt_star_time.setOnClickListener {
            onClickDateTimePicker(bt_star_time)
        }

        bt_end_time.setOnClickListener {
            onClickDateTimePicker(bt_end_time)
        }

        radioGroupSort.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.rb_most_popular_filter_fragment -> {
                    sortBy = "popularity"
                }
                R.id.rb_best_rated_filter_fragment ->{
                    sortBy = "vote_average"
                }
                R.id.rb_release_date_filter_fragment -> {
                    sortBy = "release_date"
                }
                R.id.rb_alphabetic_order_filter_fragment->{
                    sortBy = "title"
                }
                else -> {
                    sortBy = ""
                }
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
            "popularity" -> radioGroupSort.check(R.id.rb_most_popular_filter_fragment)
            "vote_average" -> radioGroupSort.check(R.id.rb_best_rated_filter_fragment)
            "release_date" -> radioGroupSort.check(R.id.rb_release_date_filter_fragment)
            else -> radioGroupSort.check(R.id.rb_alphabetic_order_filter_fragment)
        }

        sortBy = it.sortBy

        when(it.startTime){
            "now"-> {
                radioGroupDates.check(R.id.rb_in_theatre_filter_fragment)
            }
            else ->{
                radioGroupDates.check(R.id.rb_between_two_dates_filter_fragment)
                bt_star_time.text = it.startTime

                var data = it.startTime.split("-")
                yearStart = data.get(0).toInt()
                monthStart = data.get(1).toInt()
                dateStart= data.get(2).toInt()

                bt_end_time.text = it.endTime

                data = it.endTime.split("-")
                yearEnd = data.get(0).toInt()
                monthEnd = data.get(1).toInt()
                dateEnd= data.get(2).toInt()
            }
        }

        var genres1 = it.genres.split(",")
        genres = it.genres
        if (genres1.contains("28")){
            bt_action.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("16")){
            bt_animation.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("35")){
            bt_comedy.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("80")){
            bt_crime.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("99")){
            bt_document.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("18")){
            bt_drama.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("10751")){
            bt_family.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("12")){
            bt_adventure.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("9648")){
            bt_mystery.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("53")){
            bt_thriller.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("27")){
            bt_horror.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("878")){
            bt_sci.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("10752")){
            bt_war.setTextColor(resources.getColor(R.color.red))
        }
        if (genres1.contains("37")){
            bt_western.setTextColor(resources.getColor(R.color.red))
        }
    }

    fun onClick(p0: Button) {
        if(p0.currentTextColor == -16185336){
            p0.setTextColor(resources.getColor(R.color.red))
            genres+=","+p0.hint.toString()
        }else{
            p0.setTextColor(resources.getColor(R.color.black))
            genres = genres.replace(","+p0.hint,"")
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
                        if(year<=yearEnd){
                            if (year<yearEnd){
                                setDataButton(bt_star_time,year,monthOfYear+1,dayOfMonth)
                            }else{
                                if ((monthOfYear+1) <= monthEnd){
                                    if ((monthOfYear+1) < monthEnd){
                                        setDataButton(bt_star_time,year,monthOfYear+1,dayOfMonth)
                                    }
                                    else{
                                        if (dayOfMonth <= dateEnd){
                                            setDataButton(bt_star_time,year,monthOfYear+1,dayOfMonth)
                                        }
                                    }
                                }
                            }
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
                        if(year>=yearStart){
                            if (year>yearStart){
                                setDataButton(bt_end_time,year,monthOfYear+1,dayOfMonth)
                            }else{
                                if ((monthOfYear+1)>=monthStart){
                                    if ((monthOfYear+1)>monthStart){
                                        setDataButton(bt_end_time,year,monthOfYear+1,dayOfMonth)
                                    }
                                    else{
                                        if (dayOfMonth>=dateStart){
                                            setDataButton(bt_end_time,year,monthOfYear+1,dayOfMonth)
                                        }
                                    }
                                }
                            }
                        }
                    }, yearEnd, monthEnd, dateEnd
                )
                datePickerDialog.show()
            }
        }
    }

    private fun setDataButton(button: Button, year: Int, month: Int, day: Int){
        var m: String = month.toString()
        var d: String = day.toString()
        if (month<10){
            m = "0"+m
        }
        if (day<10){
            d = "0"+d
        }
        button.text = year.toString()+"-"+m+"-"+d
        if (button.id == R.id.bt_start_time_picker_filter_fragment){
            yearStart = year
            monthStart = month
            dateStart = day
        }else{
            yearEnd = year
            monthEnd = month
            dateEnd = day
        }
    }

    override fun onResume() {
        super.onResume()

        if(view == null){
            return
        }
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
        view!!.setOnKeyListener { view, i, keyEvent ->
            if(keyEvent.action == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_BACK){
                insertFilter()
                viewModel.back.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    if (it==true){
                        true
                    }
                })
            }

            false
        }
    }

    fun insertFilter(){
        var startTime: String = ""
        var endTime: String = ""

        when(rg_dates_filter_fragment.checkedRadioButtonId){
            R.id.rb_in_theatre_filter_fragment -> {
                startTime = "now"
                endTime = "now"
            }
            else -> {
                startTime = bt_star_time.text.toString()
                endTime = bt_end_time.text.toString()
            }
        }
        viewModel.insertFilter(sortBy, startTime, endTime,genres)
    }

}
