package com.example.weather_app.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.weather_app.R
import com.example.weather_app.databinding.HourlyWeatherItemBinding
import com.example.weather_app.domain.model.CurrentWeather

class HourlyWeatherAdapter(var hourlyWeatherList: ArrayList<CurrentWeather>): RecyclerView.Adapter<HourlyWeatherAdapter.HourlyViewHolder>() {

    class HourlyViewHolder(val binding: HourlyWeatherItemBinding):
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: ArrayList<CurrentWeather>) {
        this.hourlyWeatherList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val binding = HourlyWeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourlyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val curItem = hourlyWeatherList[position]
        holder.binding.tvHourTemp.text = holder.itemView.context.getString(R.string.temp_c, curItem.temp.toInt())
        holder.binding.tvHourHumidity.text = holder.itemView.context.getString(R.string.humidity, curItem.humidity)
        holder.binding.tvHourTime.text = curItem.dt_txt

        Glide.with(holder.itemView.context)
            .asBitmap()
            .load("https://openweathermap.org/img/wn/${curItem.icon}@2x.png")
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    holder.binding.ivHourIcon.setImageBitmap(resource)
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    override fun getItemCount(): Int {
        return hourlyWeatherList.size
    }
}