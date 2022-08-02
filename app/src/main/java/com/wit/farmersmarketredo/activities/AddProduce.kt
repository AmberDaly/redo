package com.wit.farmersmarketredo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.wit.farmersmarketredo.R
import com.wit.farmersmarketredo.databinding.ActivityAddproduceBinding
import com.wit.farmersmarketredo.main.FarmersApp
import com.wit.farmersmarketredo.models.ProduceModel
import timber.log.Timber

class AddProduce : AppCompatActivity() {

    private lateinit var donateLayout : ActivityAddproduceBinding
    lateinit var app: FarmersApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        donateLayout = ActivityAddproduceBinding.inflate(layoutInflater)
        setContentView(donateLayout.root)
        app = this.application as FarmersApp

        donateLayout.progressBar.max = 10000

        donateLayout.amountPicker.minValue = 1
        donateLayout.amountPicker.maxValue = 1000

        donateLayout.amountPicker.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to paymentAmount
            donateLayout.paymentAmount.setText("$newVal")
        }
        var totalDonated = 0

        donateLayout.donateButton.setOnClickListener {
            val amount = if (donateLayout.paymentAmount.text.isNotEmpty())
                donateLayout.paymentAmount.text.toString().toInt() else donateLayout.amountPicker.value
            if(totalDonated >= donateLayout.progressBar.max)
                Toast.makeText(applicationContext,"Donate Amount Exceeded!", Toast.LENGTH_LONG).show()
            else {
                val paymentmethod = if(donateLayout.paymentMethod.checkedRadioButtonId == R.id.Direct)
                    "Direct" else "Paypal"
                totalDonated += amount
                donateLayout.totalSoFar.text = "$$totalDonated"
                donateLayout.progressBar.progress = totalDonated
                app.producesStore.create(ProduceModel(paymentmethod = paymentmethod,amount = amount))
                Timber.i("Total Donated so far $totalDonated")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_addproduce, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_list -> { startActivity(Intent(this,
                                                                        List::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}