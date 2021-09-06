package com.example.meteorpedia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.meteorpedia.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MeteorsMapFragment : Fragment() {

    private lateinit var supportMapFragment: SupportMapFragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meteors_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backBtn: AppCompatImageView = requireActivity().findViewById(R.id.back_btn)
        backBtn.visibility = View.VISIBLE

        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        val meteorName = arguments?.getString("meteor_name")
        val meteorLat = arguments?.getString("meteor_lat")
        val meteorLong = arguments?.getString("meteor_long")
        val meteorFallenYear = arguments?.getString("meteor_fallen_year")
        supportMapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment

        supportMapFragment.getMapAsync { googleMap ->
            val meteorLocation = LatLng(meteorLat!!.toDouble(), meteorLong!!.toDouble())
            googleMap.addMarker(MarkerOptions().position(meteorLocation).title(meteorName).snippet(
                        "Year: $meteorFallenYear"
            ).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))!!.showInfoWindow()
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(meteorLocation, 10f))
            //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(meteorLocation, 10f))
        }
    }
}