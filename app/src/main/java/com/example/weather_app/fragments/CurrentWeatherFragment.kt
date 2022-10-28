package com.example.weather_app.fragments

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.text.util.Linkify
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.weather_app.R
import com.example.weather_app.adapter.HourlyWeatherAdapter
import com.example.weather_app.databinding.FragmentCurrentWeatherBinding
import com.example.weather_app.domain.model.CurrentWeather
import com.example.weather_app.util.Settings
import com.example.weather_app.util.URLs
import com.example.weather_app.view_models.CurrentWeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.jar.Manifest

//private const val ARG_PARAM_LATITUDE = "latitude"
//private const val ARG_PARAM_LONGITUDE = "longitude"

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment() {
//    private var param_lat: String? = null
//    private var param_lon: String? = null

    private lateinit var binding: FragmentCurrentWeatherBinding
    private val currentWeatherViewModel: CurrentWeatherViewModel by viewModels()
    private lateinit var defaultCity: String
    private lateinit var adapter: HourlyWeatherAdapter
    private val hourlyWeatherList = arrayListOf<CurrentWeather>()


    private var repeat = 3
    private var repeat2 = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



//        (activity as AppCompatActivity).supportActionBar?.title = "Saint-Petersburg"
//        arguments?.let {
//            param_lon = it.getString(ARG_PARAM_LONGITUDE)
//            param_lat = it.getString(ARG_PARAM_LATITUDE)
//        }
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

        adapter = HourlyWeatherAdapter(ArrayList())
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvHourWeather.layoutManager = layoutManager
        binding.rvHourWeather.adapter = adapter


//        requireActivity().actionBar?.title =  "Saint-Petersburg"

        currentWeatherViewModel.getCurrentWeatherByCoordinates(Settings.DEFAULT_LAT, Settings.DEFAULT_LON, URLs.APP_ID)
//        currentWeatherViewModel.getCurrentWeatherByCity("Saint-Petersburg", URLs.APP_ID)
        currentWeatherViewModel.getDailyWeatherByCoordinates(Settings.DEFAULT_LAT, Settings.DEFAULT_LON, URLs.APP_ID)
        callOpenWeatherMapApi()
    }

    private fun callOpenWeatherMapApi(){
        val progressBar = binding.progressBar
        CoroutineScope(Dispatchers.Main).launch {

            repeat(repeat2) {
                currentWeatherViewModel._dailyWeatherValue.collect { value ->
                    when {
                        value.isLoading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        value.error.isNotBlank() -> {

//                            findNavController().navigate(R.id.action_descriptionFragment_to_errorFragment)
                            progressBar.visibility = View.GONE
                            repeat2 = 0
                        }
                        value.dailyWeather != null -> {
                            repeat2 = 0
                            progressBar.visibility = View.GONE

                            hourlyWeatherList.addAll(value.dailyWeather)
                            adapter.setData(hourlyWeatherList)

                        }
                    }
                    delay(1000)
                }
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            repeat(repeat){
                currentWeatherViewModel._curWeatherValue.collect{value->
                    when {
                        value.isLoading -> {
                            progressBar.visibility = View.VISIBLE
                            with(binding){}
                        }
                        value.error.isNotBlank() -> {

//                            findNavController().navigate(R.id.action_descriptionFragment_to_errorFragment)
                            progressBar.visibility = View.GONE
                            repeat = 0
                        }
                        value.currentWeather!=null-> {
                            repeat = 0
                            progressBar.visibility = View.GONE

                            binding.curTemperature.text = getString(R.string.temp_c, value.currentWeather.temp.toInt())
                            binding.feelsLike.text = getString(R.string.feels_like_temp_c, value.currentWeather.feels_like.toInt())
                            binding.curWeatherDescr.text = value.currentWeather.weather_descr.replaceFirstChar { it.uppercaseChar()}
                            binding.curWind.text = getString(R.string.wind_speed, String.format("%.2f", value.currentWeather.wind))
                            binding.curHumidity.text = getString(R.string.humidity, value.currentWeather.humidity)
                            binding.curPressure.text = getString(R.string.pressure, value.currentWeather.pressure)
                            binding.curSunrise.text = value.currentWeather.sunrise
                            binding.curSunset.text = value.currentWeather.sunset

                            Glide.with(this@CurrentWeatherFragment)
                                .asBitmap()
                                .load("https://openweathermap.org/img/wn/${value.currentWeather.icon}@2x.png")
                                .into(object : CustomTarget<Bitmap>(){
                                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                        binding.ivCurWeatherIcon.setImageBitmap(resource)
                                    }
                                    override fun onLoadCleared(placeholder: Drawable?) {
                                    }
                                })
                        }
                    }
                    delay(1000)
                }
            }
        }

    }



}