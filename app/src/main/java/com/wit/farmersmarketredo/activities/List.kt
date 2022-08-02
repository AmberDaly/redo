package com.wit.farmersmarketredo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.wit.farmersmarketredo.R
import com.wit.farmersmarketredo.adapters.ProduceAdapter
import com.wit.farmersmarketredo.databinding.ActivityAddproduceBinding
import com.wit.farmersmarketredo.databinding.ActivityListBinding
import com.wit.farmersmarketredo.main.FarmersApp

class List : AppCompatActivity() {

    lateinit var app: FarmersApp
    lateinit var reportLayout : ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reportLayout = ActivityListBinding.inflate(layoutInflater)
        setContentView(reportLayout.root)

        app = this.application as FarmersApp
        reportLayout.recyclerView.layoutManager = LinearLayoutManager(this)
        reportLayout.recyclerView.adapter = ProduceAdapter(app.producesStore.findAll())
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_addproduce -> { startActivity(
                Intent(this,
                    AddProduce::class.java)
            )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}