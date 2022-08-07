package com.wit.farmersmarketredo.ui.map

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.wit.farmersmarketredo.R
import com.wit.farmersmarketredo.models.ProduceModel
import com.wit.farmersmarketredo.ui.auth.LoggedInViewModel
import com.wit.farmersmarketredo.ui.list.ListViewModel
import com.wit.farmersmarketredo.utils.createLoader
import com.wit.farmersmarketredo.utils.hideLoader
import com.wit.farmersmarketredo.utils.showLoader

class MapsFragment : Fragment() {

    private val mapsViewModel: MapsViewModel by activityViewModels()
    private val listViewModel: ListViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    lateinit var loader : AlertDialog

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        mapsViewModel.map = googleMap
        mapsViewModel.map.isMyLocationEnabled = true
        mapsViewModel.currentLocation.observe(viewLifecycleOwner,{
            val loc = LatLng(mapsViewModel.currentLocation.value!!.latitude,
                mapsViewModel.currentLocation.value!!.longitude)

            mapsViewModel.map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 14f))
            mapsViewModel.map.uiSettings.isZoomControlsEnabled = true
            mapsViewModel.map.uiSettings.isMyLocationButtonEnabled = true

           listViewModel.observableProducesList.observe(viewLifecycleOwner, Observer { produces ->
                produces?.let {
                    render(produces as ArrayList<ProduceModel>)
                    hideLoader(loader)
                }
            })
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loader = createLoader(requireActivity())

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_map, menu)

        val item = menu.findItem(R.id.toggleProduces) as MenuItem
        item.setActionView(R.layout.togglebutton_layout)
        val toggleProduces: SwitchCompat = item.actionView.findViewById(R.id.toggleButton)
        toggleProduces.isChecked = false

        toggleProduces.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) listViewModel.loadAll()
            else listViewModel.load()
        }
    }

    private fun render(producesList: ArrayList<ProduceModel>) {
        var markerColour: Float
        if (!producesList.isEmpty()) {
            mapsViewModel.map.clear()
            producesList.forEach {
                if(it.email.equals(this.listViewModel.liveFirebaseUser.value!!.email))
                    markerColour = BitmapDescriptorFactory.HUE_AZURE + 5
                else
                    markerColour = BitmapDescriptorFactory.HUE_RED

                mapsViewModel.map.addMarker(
                    MarkerOptions().position(LatLng(it.latitude, it.longitude))
                        .title("${it.paymenttype} €${it.amount}")
                        .snippet(it.message)
                        .icon(BitmapDescriptorFactory.defaultMarker(markerColour ))
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader, "Downloading PRoduces")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                listViewModel.liveFirebaseUser.value = firebaseUser
               listViewModel.load()
            }
        })
    }
}