package com.example.weather_app.ui.fragment

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.location.Address
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLayoutChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.weather_app.R
import com.example.weather_app.databinding.FragmentCurrentWeatherBinding
import com.example.weather_app.domain.model.CurrentWeather
import com.example.weather_app.ui.adapter.HourlyWeatherAdapter
import com.example.weather_app.ui.view_model.WeatherViewModel
import com.example.weather_app.util.LocationTracker
import com.example.weather_app.util.Settings
import com.example.weather_app.util.URLs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment(){
    private lateinit var binding: FragmentCurrentWeatherBinding
    private val weatherViewModel: WeatherViewModel by viewModels()

    private lateinit var adapter: HourlyWeatherAdapter
    private var hourlyWeatherList = arrayListOf<CurrentWeather>()

    private lateinit var locationTracker: LocationTracker
    private var address: Address? = null

    private var repeatDailyWeatherRequest = 3
    private var repeatCurrentWeatherRequest = 3
    private var searchCityName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionLocation()
        locationTracker = LocationTracker(requireContext())
        address = locationTracker.getLocation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentWeatherBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set rv for hourly weather
        adapter = HourlyWeatherAdapter(ArrayList())
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvHourWeather.layoutManager = layoutManager
        binding.rvHourWeather.adapter = adapter

        binding.fabCurrentLocation.setOnClickListener { _ ->
            setWeatherByDefaultCityOrCurrentLocation()
        }

        setWeatherByDefaultCityOrCurrentLocation()
        searchCityListener()

//        Toast.makeText(requireContext(),address.latitude.toString() + " " + curLong.toString(), Toast.LENGTH_SHORT).show()

    }

    private fun setWeatherByDefaultCityOrCurrentLocation() {
        if (address == null) {
            weatherViewModel.getCurrentWeatherByCoordinates(
                Settings.DEFAULT_LAT,
                Settings.DEFAULT_LON,
                URLs.APP_ID
            )
            weatherViewModel.getDailyWeatherByCoordinates(
                Settings.DEFAULT_LAT,
                Settings.DEFAULT_LON,
                URLs.APP_ID
            )
        } else {
            weatherViewModel.getCurrentWeatherByCoordinates(
                address!!.latitude,
                address!!.longitude,
                URLs.APP_ID
            )
            weatherViewModel.getDailyWeatherByCoordinates(
                address!!.latitude,
                address!!.longitude,
                URLs.APP_ID
            )

        }
        callOpenWeatherMapApi()
    }

    private fun searchCityListener() {
        binding.ivSearchCity.setOnClickListener { view ->
            //Hide keyboard
            val inputMethod = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethod.hideSoftInputFromWindow(view.windowToken, 0)

            if(binding.etSearchCity.text.isNotEmpty()){
                searchCityName = binding.etSearchCity.text.toString()
                binding.etSearchCity.text.clear()
                binding.etSearchCity.clearFocus()
                weatherViewModel.getCurrentWeatherByCity(searchCityName.toLowerCase(), URLs.APP_ID)
                weatherViewModel.getDailyWeatherByCity(searchCityName.toLowerCase(), URLs.APP_ID)
                callOpenWeatherMapApi()
            }
        }
    }

    private fun callOpenWeatherMapApi(){
        val progressBar = binding.progressBar

        CoroutineScope(Dispatchers.Main).launch {

            repeat(repeatDailyWeatherRequest) {
                weatherViewModel._dailyWeatherValue.collect { value ->
                    when {
                        value.isLoading -> {
//                            progressBar.visibility = View.VISIBLE
                        }
                        value.error.isNotBlank() -> {
//                            progressBar.visibility = View.GONE
                            repeatDailyWeatherRequest = 0
                        }
                        value.dailyWeather.isNotEmpty() -> {
                            repeatDailyWeatherRequest = 0
//                            progressBar.visibility = View.GONE

                            setDailyWeatherFields(value.dailyWeather)
                        }
                    }
                    delay(1000)
                }
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            repeat(repeatCurrentWeatherRequest){
                weatherViewModel._curWeatherValue.collect{ value->
                    when {
                        value.isLoading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        value.error.isNotBlank() -> {
                            progressBar.visibility = View.GONE
                            repeatCurrentWeatherRequest = 0
                            Toast.makeText(requireContext(), "City not found, try again", Toast.LENGTH_SHORT).show()
                            searchCityName = ""
                        }
                        value.currentWeather!=null-> {
                            repeatCurrentWeatherRequest = 0
                            progressBar.visibility = View.GONE

                            setCurrentWeatherFields(value.currentWeather)
                            searchCityName = ""
                        }
                    }
                    delay(1000)
                }
            }
        }

    }

    private fun setDailyWeatherFields(dailyWeatherList: List<CurrentWeather>) {
        binding.rvHourWeather.visibility = View.VISIBLE

//        binding.rvHourWeather.addOnLayoutChangeListener {
//                view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
//                    if(bottom < oldBottom){
//                        binding.rvHourWeather.post {
//                            binding.rvHourWeather.smoothScrollToPosition(
//                                (binding.rvHourWeather.adapter?.itemCount?: -1)
//                            )
//                        }
//                    }
//
//        }

        hourlyWeatherList = dailyWeatherList as ArrayList<CurrentWeather>
//        hourlyWeatherList.addAll(dailyWeatherList)
        adapter.setData(hourlyWeatherList)
    }

    private fun setCurrentWeatherFields(curWeather: CurrentWeather) {
        binding.gridLayoutCurWeather.visibility = View.VISIBLE
        if(searchCityName!=""){
            binding.curCity.text = searchCityName.capitalize()
//            binding.etSearchCity.text.clear()
        }
        else if(address == null){
            binding.curCity.text = Settings.DEFAULT_CITY
        }
        else {
            binding.curCity.text = address?.locality.toString()
        }

        binding.curTemperature.text = getString(R.string.temp_c, curWeather.temp.toInt())
        binding.feelsLike.text = getString(R.string.feels_like_temp_c, curWeather.feels_like.toInt())
        binding.curWeatherDescr.text = curWeather.weather_descr.replaceFirstChar { it.uppercaseChar()}
        binding.curWind.text = getString(R.string.wind_speed, String.format("%.2f", curWeather.wind))
        binding.curHumidity.text = getString(R.string.humidity, curWeather.humidity)
        binding.curPressure.text = getString(R.string.pressure, curWeather.pressure)
        binding.curSunrise.text = curWeather.sunrise
        binding.curSunset.text = curWeather.sunset

        if(!isAdded) return
        Glide.with(this@CurrentWeatherFragment)
            .asBitmap()
            .load("https://openweathermap.org/img/wn/${curWeather.icon}@2x.png")
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    binding.ivCurWeatherIcon.setImageBitmap(resource)
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    private fun requestPermissionLocation(){
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION),
            100
        )
    }
}