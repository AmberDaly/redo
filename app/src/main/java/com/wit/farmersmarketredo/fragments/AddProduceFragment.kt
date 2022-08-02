package com.wit.farmersmarketredo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.wit.farmersmarketredo.R
import com.wit.farmersmarketredo.databinding.FragmentAddProduceBinding
import com.wit.farmersmarketredo.main.FarmersApp
import com.wit.farmersmarketredo.models.ProduceModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddProduceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddProduceFragment :Fragment() {
    lateinit var app: FarmersApp
    var totalDonated = 0
    private var _fragBinding: FragmentAddProduceBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as FarmersApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentAddProduceBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_addproduce)

        fragBinding.progressBar.max = 10000
        fragBinding.amountPicker.minValue = 1
        fragBinding.amountPicker.maxValue = 1000

        fragBinding.amountPicker.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to paymentAmount
            fragBinding.paymentAmount.setText("$newVal")
        }
        return root;
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddProduceFragment().apply {
                arguments = Bundle().apply {}
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
                layout.totalSoFar.text = "$$totalDonated"
                layout.progressBar.progress = totalDonated
                app.producesStore.create(ProduceModel(paymentmethod = paymentmethod,amount = amount))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
        totalDonated = app.producesStore.findAll().sumOf { it.amount }
        fragBinding.progressBar.progress = totalDonated
        fragBinding.totalSoFar.text = "$$totalDonated"
    }
}
