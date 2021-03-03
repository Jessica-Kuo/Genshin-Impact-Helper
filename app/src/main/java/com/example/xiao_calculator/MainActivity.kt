package com.example.xiao_calculator

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.example.xiao_calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currencyOne = 1
    private var currencyTwo = 1
    private val PRIMOS = 1
    private val PULLS = 0

    /*
    * currencyOne is for currently owned primos/pulls
    * currencyTwo is for desired primos/pulls
    * formula: (desired-current)/days = primos needed per day
    *
    * Translation note: pulls = fates
    * */

    /*
    * TODO: Notification setter
    * In a separate activity, have something that lets you set notifs for mats
    * Like every wednesday, have one for teachings of ballad being back
    * Or every monday, send a notif reminding weekly boss reset
    * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initialSetup()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.website -> {
                val openDocs = Intent(Intent.ACTION_VIEW)
                openDocs.data = Uri.parse("https://genshin.mihoyo.com/en")
                startActivity(openDocs)
                try {
                    startActivity(openDocs)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this, "No browser to open link", Toast.LENGTH_SHORT).show()
                }
                true
            }
            R.id.wiki -> {
                val openDocs = Intent(Intent.ACTION_VIEW)
                openDocs.data = Uri.parse("https://genshin-impact.fandom.com/wiki/Genshin_Impact_Wiki")
                startActivity(openDocs)
                try {
                    startActivity(openDocs)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this, "No browser to open link", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onButtonClick() {
        val days = binding.daysToGetThem.text.toString()
        val valueOne = binding.currentPrimos.text.toString()
        val valueTwo = binding.howManyYouWant.text.toString()
        if(isNullOrEmpty(valueOne) || isNullOrEmpty(valueTwo) || isNullOrEmpty(days)) {
            Toast.makeText(this, "All values must be filled out", Toast.LENGTH_SHORT).show()
        } else {
            var intOne = valueOne.toInt()
            var intTwo = valueTwo.toInt()
            if (currencyOne == currencyTwo) {
                if (currencyOne == PRIMOS) {
                    val result = (intTwo - intOne) / days.toInt()
                    binding.resultText.text =
                        "You'll need " + result.toString() + " primos a day to reach this goal"
                } else if (currencyOne == PULLS) {
                    val result = (intTwo - intOne) / days.toInt()
                    binding.resultText.text =
                        "You'll need " + result.toString() + " pulls a day to reach this goal"
                }
            } else {
                if (currencyOne == PRIMOS && currencyTwo == PULLS) {
                    intTwo *= 160
                    val result = (intTwo - intOne) / days.toInt()
                    binding.resultText.text =
                        "You'll need " + result.toString() + " primos a day to reach this goal"
                } else {
                    intOne *= 160
                    val result = (intTwo - intOne) / days.toInt()
                    binding.resultText.text =
                        "You'll need " + result.toString() + " primos a day to reach this goal"
                }
            }
        }
    }

    fun onRadioGroupOneClick(view: View) {
        if(view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.primoRadioButton ->
                    if (checked) {
                        currencyOne = 1
                    }
                R.id.fateRadioButton ->
                    if (checked) {
                        currencyOne = 0
                    }
            }
        }
    }

    fun onRadioGroupTwoClick(view: View) {
        if(view is RadioButton) {
            val checked = view.isChecked

            when(view.getId()) {
                R.id.primoRadioButton2 ->
                    if(checked) {
                        currencyTwo = 1
                    }
                R.id.fateRadioButton2 ->
                    if(checked) {
                        currencyTwo = 0
                    }
            }
        }
    }

    private fun initialSetup() {
        binding.calculate.setOnClickListener {
            onButtonClick()
        }
    }

    //utility
    private fun isNullOrEmpty(text: String): Boolean {
        return TextUtils.isEmpty(text) || text.trim { it <= ' ' }.isEmpty()
    }
}