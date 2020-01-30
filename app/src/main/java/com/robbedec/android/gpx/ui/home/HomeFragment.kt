package com.robbedec.android.gpx.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.robbedec.android.gpx.PusherApplication
import com.robbedec.android.gpx.R
import com.robbedec.android.gpx.databinding.FragmentHomeBinding
import org.osmdroid.views.MapView
import javax.inject.Inject
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay



class HomeFragment : Fragment() {

    @Inject lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var map: MapView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val ctx = activity!!.applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home, container, false)

        binding.viewmodel = homeViewModel

        map = binding.map
        setupMap()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity!!.applicationContext as PusherApplication).appComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    private fun setupMap() {
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT)
        map.setMultiTouchControls(true)

        val mapController = map.controller
        mapController.setZoom(15.0)
        val startPoint = GeoPoint(51.1787473, 3.1315382)
        mapController.setCenter(startPoint)


        val mLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), map)
        mLocationOverlay.enableMyLocation()
        map.overlays.add(mLocationOverlay)
    }
}