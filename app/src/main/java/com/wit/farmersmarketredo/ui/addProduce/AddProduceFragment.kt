package com.wit.farmersmarketredo.ui.addProduce

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.wit.farmersmarketredo.R
import com.wit.farmersmarketredo.databinding.FragmentAddProduceBinding
import com.wit.farmersmarketredo.main.FarmersApp
import com.wit.farmersmarketredo.models.ProduceModel
import com.wit.farmersmarketredo.ui.list.ListViewModel

class AddProduceFragment :Fragment() {

    //lateinit var app: FarmersApp
    var totalDonated = 0
    private var _fragBinding: FragmentAddProduceBinding? = null

    private val fragBinding get() = _fragBinding!!

    private lateinit var addProduceViewModel: AddProduceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // app = activity?.application as FarmersApp
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentAddProduceBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_addproduce)

        addProduceViewModel = ViewModelProvider(this).get(AddProduceViewModel::class.java)
        addProduceViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })

        fragBinding.progressBar.max = 10000
        fragBinding.amountPicker.minValue = 1
        fragBinding.amountPicker.maxValue = 1000

        fragBinding.amountPicker.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to paymentAmount
            fragBinding.paymentAmount.setText("$newVal")
        }
        setButtonListener(fragBinding)
        return root;
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    //findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.produceError),Toast.LENGTH_LONG).show()
        }
    }

    fun setButtonListener(layout: FragmentAddProduceBinding) {
        layout.donateButton.setOnClickListener {
            val amount = if (layout.paymentAmount.text.isNotEmpty())
                layout.paymentAmount.text.toString().toInt() else layout.amountPicker.value
            if(totalDonated >= layout.progressBar.max)
                Toast.makeText(context,"Donate Amount Exceeded!", Toast.LENGTH_LONG).show()
            else {
                val paymentmethod = if(layout.paymentMethod.checkedRadioButtonId == R.id.Direct) "Direct" else "Paypal"
                totalDonated += amount
                layout.totalSoFar.text = getString(R.string.total_donated,totalDonated)
                layout.progressBar.progress = totalDonated
                addProduceViewModel.addProduce(ProduceModel(paymentmethod = paymentmethod,amount = amount))

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_addproduce, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
        val listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.observableDonationsList.observe(viewLifecycleOwner, Observer {
            totalDonated = listViewModel.observableDonationsList.value!!.sumOf { it.amount }
            fragBinding.progressBar.progress = totalDonated
            fragBinding.totalSoFar.text = getString(R.string.total_donated,totalDonated)
        })
    }
}