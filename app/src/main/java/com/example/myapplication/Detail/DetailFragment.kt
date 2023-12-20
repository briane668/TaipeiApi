package com.example.myapplication.Detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.AttractionData
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailFragment : Fragment(), OnMapReadyCallback {
    private lateinit var attractionData: AttractionData
    private lateinit var _binding: FragmentDetailBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        arguments?.let {
            attractionData = it.getSerializable("attraction") as AttractionData
            val activity: MainActivity? = activity as MainActivity?
            activity?.changeTitle(attractionData.name)
        }
        Glide.with(this)
            .load(attractionData.images.firstOrNull()?.src)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(RequestOptions().placeholder(R.drawable.placeholder))// 设置渐变效果（可选）
            .into(_binding.picture)
        _binding.introduction.text = attractionData.introduction
        _binding.openTime.text = "營業時間" + attractionData.open_time
        _binding.address.text = "地址:" + attractionData.address
        _binding.phone.text = "聯繫電話" + attractionData.tel
        _binding.webviewUrl.text = attractionData.url
        _binding.otherImage.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        val imageAdapter = ImageAdapter(context!!)
        imageAdapter.images = attractionData.images
        _binding.otherImage.adapter =imageAdapter

        _binding.webviewUrl.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("url", attractionData.url)
            findNavController().navigate(R.id.action_global_webFragment, bundle)
        }
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()
        binding.mapView.getMapAsync(this)
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val x = attractionData.nlat
        val y = attractionData.elong
        val sydney = LatLng(x, y)
        googleMap.setMinZoomPreference(16.0f)
        googleMap.addMarker(MarkerOptions().position(sydney))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}