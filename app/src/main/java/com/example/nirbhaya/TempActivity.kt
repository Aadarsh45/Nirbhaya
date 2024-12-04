package com.example.nirbhaya

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.nirbhaya.databinding.ActivityTempBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ola.mapsdk.interfaces.OlaMapCallback
import com.ola.mapsdk.model.OlaLatLng
import com.ola.mapsdk.model.OlaMarkerOptions
import com.ola.mapsdk.view.OlaMap
import com.ola.mapsdk.view.OlaMapView


class TempActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTempBinding
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    lateinit var olaMapView : OlaMapView
    lateinit var  fetchLocation : FloatingActionButton

    lateinit var olaMap2 : OlaMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTempBinding.inflate(layoutInflater)
        setContentView(binding.root)


        olaMapView = findViewById(R.id.mapView1)
        fetchLocation = findViewById(R.id.user_loc)

        fetchLocation.setOnClickListener {
            val currentLocation: OlaLatLng? = olaMap2?.getCurrentLocation()
            if (currentLocation != null) {

                olaMap2.moveCameraToLatLong(currentLocation,15.0,2000)
                // Use the current location
            } else {
                // Handle the case where the location is not available
            }
        }



        olaMapView.getMap(apiKey = "",
            olaMapCallback = object : OlaMapCallback {
                override fun onMapReady(olaMap: OlaMap) {


                    // Map is ready to use
                    olaMap2 = olaMap


                    olaMap2.showCurrentLocation()

                    var olapoints = OlaLatLng(18.52145653681468, 73.93178277572254)

                    val markerOptions1 = OlaMarkerOptions.Builder()
                        .setMarkerId("marker1")
                        .setPosition(olapoints)
                        .setIsIconClickable(true)
                        .setIconRotation(0f)
                        .setIsAnimationEnable(true)
                        .setIsInfoWindowDismissOnClick(true)
                        .build()

                    var marker1 = olaMap.addMarker(markerOptions1)
                    var handler = Handler(Looper.getMainLooper())

                    handler.postDelayed({
                        olaMap.moveCameraToLatLong(olapoints,15.0,2000)
                    },2000)

                }

                override fun onMapError(error: String) {
                    // Handle map error
                }
            }
        )

    }
}


