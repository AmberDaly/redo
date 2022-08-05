package com.wit.farmersmarketredo.ui.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.wit.farmersmarketredo.R
import com.wit.farmersmarketredo.adapters.ProduceAdapter
import com.wit.farmersmarketredo.adapters.ProduceClickListener
import com.wit.farmersmarketredo.databinding.FragmentListBinding
import com.wit.farmersmarketredo.main.FarmersApp
import com.wit.farmersmarketredo.models.ProduceModel
import com.wit.farmersmarketredo.utils.createLoader
import com.wit.farmersmarketredo.utils.hideLoader
import com.wit.farmersmarketredo.utils.showLoader

class ListFragment :  Fragment() , ProduceClickListener {

    lateinit var app: FarmersApp
    private var _fragBinding: FragmentListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var listViewModel: ListViewModel
    lateinit var loader : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        loader = createLoader(requireActivity())

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)

        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        showLoader(loader,"Downloading Donations")
        listViewModel.observableProducesList.observe(viewLifecycleOwner, Observer {
                donations ->
            donations?.let { render(donations)
                hideLoader(loader)}
            checkSwipeRefresh()
        })

        setSwipeRefresh()
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun render(donationsList: List<ProduceModel>) {
        fragBinding.recyclerView.adapter = ProduceAdapter(donationsList,this)
        if (donationsList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.donationsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.donationsNotFound.visibility = View.GONE
        }
    }
    override fun onProduceClick(produce: ProduceModel){
        val action = ListFragmentDirections.actionListFragmentToProduceDetailFragment(produce.id)
        findNavController().navigate(action)
    }
    override fun onResume() {
        super.onResume()
        listViewModel.load()
    }
    fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader,"Downloading Donations")
            //Retrieve Donation List again here

        }
    }

    fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}