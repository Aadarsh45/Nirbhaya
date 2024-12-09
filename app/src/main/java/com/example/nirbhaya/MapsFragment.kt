package com.example.nirbhaya

//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.example.nirbhaya.databinding.FragmentMapsBinding
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.ola.mapsdk.interfaces.OlaMapCallback
//import com.ola.mapsdk.model.OlaLatLng
//import com.ola.mapsdk.view.OlaMap
//import com.ola.mapsdk.view.OlaMapView
//
//class MapsFragment : Fragment() {
//
//    private var _binding: FragmentMapsBinding? = null
//    private val binding get() = _binding!!
//
//    private lateinit var olaMapView: OlaMapView
//    private lateinit var fetchLocation: FloatingActionButton
//    private lateinit var olaMap2: OlaMap
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentMapsBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        olaMapView = binding.mapView
//        fetchLocation = binding.userLoc
//
//        fetchLocation.setOnClickListener {
//            val currentLocation: OlaLatLng? = olaMap2.getCurrentLocation()
//            if (currentLocation != null) {
//                olaMap2.moveCameraToLatLong(currentLocation, 15.0, 2000)
//            } else {
//                // Handle the case where the location is not available
//            }
//        }
//
//        olaMapView.getMap(
//            apiKey = "UKgJQ6Uryy7lLCHKhukCWIst6wKV4TPuxqwCaXhn",
//            olaMapCallback = object : OlaMapCallback {
//                override fun onMapReady(olaMap: OlaMap) {
//                    // Map is ready to use
//                    olaMap2 = olaMap
//
//                    olaMap2.showCurrentLocation()
//                }
//
//                override fun onMapError(error: String) {
//                    // Handle map error
//                }
//            }
//        )
//    }
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

//    companion object {
//        @JvmStatic
//        fun newInstance() = MapsFragment()
//    }
//}


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nirbhaya.databinding.FragmentMapsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ola.mapsdk.interfaces.OlaMapCallback
import com.ola.mapsdk.model.OlaLatLng
import com.ola.mapsdk.model.OlaMarkerOptions
import com.ola.mapsdk.view.OlaMap
import com.ola.mapsdk.view.OlaMapView

class MapsFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private lateinit var olaMapView: OlaMapView
    private lateinit var fetchLocation: FloatingActionButton
    private lateinit var olaMap2: OlaMap
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        olaMapView = binding.mapView
        fetchLocation = binding.userLoc

        fetchLocation.setOnClickListener {
            val currentLocation: OlaLatLng? = olaMap2.getCurrentLocation()
            if (currentLocation != null) {
                olaMap2.moveCameraToLatLong(currentLocation, 15.0, 2000)
            } else {
                // Handle the case where the location is not available
            }
        }

        olaMapView.getMap(
            apiKey = "UKgJQ6Uryy7lLCHKhukCWIst6wKV4TPuxqwCaXhn",
            olaMapCallback = object : OlaMapCallback {
                override fun onMapReady(olaMap: OlaMap) {
                    // Map is ready to use
                    olaMap2 = olaMap

                    // Show current user's location
                    olaMap2.showCurrentLocation()

                    // Fetch and display invited user's location
                    fetchInvitedUserLocation()
                }

                override fun onMapError(error: String) {
                    // Handle map error
                }
            }
        )
    }
    private fun fetchInvitedUserLocation() {
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
        if (currentUserEmail != null) {
            firestore.collection("users")
                .document(currentUserEmail)
                .collection("invites")
                .get()
                .addOnSuccessListener { inviteDocuments ->
                    for (document in inviteDocuments) {
                        val invitedUserEmail = document.id // Assumes the document ID is the email
                        firestore.collection("users")
                            .document(invitedUserEmail)
                            .get()
                            .addOnSuccessListener { userDocument ->
                                if (userDocument.exists()) {
                                    val latitude = userDocument.getDouble("latitude")
                                    val longitude = userDocument.getDouble("longitude")
                                    val name = userDocument.getString("name") ?: "Invited User"

                                    if (latitude != null && longitude != null) {
                                        val invitedUserLocation = OlaLatLng(latitude, longitude)

                                        // Create a marker options object
                                        val markerOptions = OlaMarkerOptions.Builder()
                                            .setMarkerId(invitedUserEmail)
                                            .setPosition(invitedUserLocation)
                                            .setSnippet("Name: $name\nEmail: $invitedUserEmail")
                                            .setIsIconClickable(true)
                                            .setIconRotation(0f)
                                            .setIsAnimationEnable(true)
                                            .setIsInfoWindowDismissOnClick(true)
                                            .build()

                                        // Add the marker to the map
                                        olaMap2.addMarker(markerOptions)

                                        // Optionally move the camera to the first invited user's location
                                        olaMap2.moveCameraToLatLong(invitedUserLocation, 15.0, 2000)
                                    }
                                }
                            }
                            .addOnFailureListener { e ->
                                // Log or handle the failure of fetching invited user's details
                            }
                    }
                }
                .addOnFailureListener { e ->
                    // Log or handle the failure of fetching invites
                }
        }
    }


//    private fun fetchInvitedUserLocation() {
//        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
//        if (currentUserEmail != null) {
//            firestore.collection("locations")
//                .document(currentUserEmail)
//                .get()
//                .addOnSuccessListener { document ->
//                    if (document != null && document.exists()) {
//                        val latitude = document.getDouble("latitude")
//                        val longitude = document.getDouble("longitude")
//                        if (latitude != null && longitude != null) {
//                            val invitedUserLocation = OlaLatLng(latitude, longitude)
//                            olaMap2.addMarker(invitedUserLocation) // Add a marker for invited user's location
//                            olaMap2.moveCameraToLatLong(invitedUserLocation, 15.0, 2000)
//                        }
//                    } else {
//                        // No location data found
//                    }
//                }
//                .addOnFailureListener { e ->
//                    // Log or handle failure
//                }
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MapsFragment()
    }
}


