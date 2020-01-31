package com.robbedec.android.gpx.ui.home

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import com.robbedec.android.gpx.MainActivity
import com.robbedec.android.gpx.PusherApplication
import com.robbedec.android.gpx.R
import com.robbedec.android.gpx.databinding.FragmentHomeBinding
import com.robbedec.android.gpx.services.LocationRecorderService
import org.osmdroid.views.MapView
import javax.inject.Inject
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import timber.log.Timber
import java.security.acl.Owner


class HomeFragment : Fragment() {

    @Inject lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var map: MapView

    private lateinit var locationRecorderServiceConnection: ServiceConnection
    private lateinit var locationRecorderService: LocationRecorderService

    private var locationRecorderServiceBound = false

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
        PusherApplication.appComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        map.onResume()

        homeViewModel.newSegmentId.observe(viewLifecycleOwner, Observer {
            binding.startButton.isVisible = false
            binding.pauseButton.isVisible = true
            startService()
            stopService()
        })
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

    private fun startService() {
        Timber.i("Service started")

        val intent = Intent(context, LocationRecorderService::class.java)
        intent.putExtra("TRACK_ID", homeViewModel.newTrackId.value)
        intent.putExtra("SEGMENT_ID", homeViewModel.newSegmentId.value)


        locationRecorderServiceConnection = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                locationRecorderService.stopLocationUpdates()
                locationRecorderServiceBound = false
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val binder = service as LocationRecorderService.LocationRecorderServiceBinder
                locationRecorderService = binder.getService()
                locationRecorderServiceBound = true
            }
        }

        context!!.startService(intent)
        context!!.bindService(Intent(context, LocationRecorderService::class.java), locationRecorderServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun stopService() {
        Handler().postDelayed({
            context!!.stopService(Intent(context, LocationRecorderService::class.java))
            context!!.unbindService(locationRecorderServiceConnection)
            resetLayout()
            homeViewModel.test()
        }, 1000 * 7)
    }

    private fun resetLayout() {

    }
}