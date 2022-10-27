package com.example.weather_app.fragments

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.text.util.Linkify
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weather_app.R
import com.example.weather_app.databinding.FragmentCurrentWeatherBinding
import com.example.weather_app.util.URLs
import com.example.weather_app.view_models.CurrentWeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

    private var repeat = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        currentWeatherViewModel.getCurrentWeatherByCoordinates(49.0, 30.0, URLs.APP_ID)
//        currentWeatherViewModel.getCurrentWeatherByCity("Saint-Petersburg", URLs.APP_ID)
        callOpenWeatherMapApi()
    }

    private fun callOpenWeatherMapApi(){
        CoroutineScope(Dispatchers.Main).launch {
            repeat(repeat){
                currentWeatherViewModel._curWeatherValue.collect{value->
                    when {
                        value.isLoading -> {
//                            binding.progressBar.visibility = View.VISIBLE
                        }
                        value.error.isNotBlank() -> {

//                            findNavController().navigate(R.id.action_descriptionFragment_to_errorFragment)
//                            binding.progressBar.visibility = View.GONE
                            repeat = 0
                        }
                        value.currentWeather!=null-> {
                            repeat = 0
                            binding.curTemperature.text = getString(R.string.temp_c, value.currentWeather.temp.toInt())
                            binding.curFeelsLike.text = getString(R.string.feels_like, value.currentWeather.feels_like.toInt())
                            binding.curWeatherDescr.text = value.currentWeather.weather_descr.replaceFirstChar { it.uppercaseChar()}
                            binding.curWind.text = getString(R.string.wind_speed, String.format("%.2f", value.currentWeather.wind))
                            binding.curHumidity.text = getString(R.string.humidity, value.currentWeather.humidity)
                            binding.curPressure.text = getString(R.string.pressure, value.currentWeather.pressure)
                            binding.curSunrise.text = value.currentWeather.sunrise
                            binding.curSunset.text = value.currentWeather.sunset

//                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }



}