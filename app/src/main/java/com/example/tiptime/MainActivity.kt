package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        //Bind the contentView layout
        setContentView(binding.root)


        //Trigger after tapping the button
        binding.calculateButton.setOnClickListener{
            this.calculateTip()
        }

        //Trigger after keyboard enter key pressed
        binding.costOfServiceEditText.setOnKeyListener { v, keyCode, event -> this.handleKeyEvent(v,keyCode) }
    }

    private fun calculateTip(){
        //Tip Amount
        var tip:Double

        //Get Editable text amount
        val cost = binding.costOfServiceEditText.text.toString().toDoubleOrNull()

        //Get tip percentage
        val tipPercentage = when(binding.tipOptions.checkedRadioButtonId){
            R.id.option_twenty_present->0.20
            R.id.option_eighteen_present->0.18
            else->0.15
        }

        //Return if null recieve from cost
        if(cost==null||cost==0.0){
            this.displayTip(0.0)
            return
        }

        //Calculate Tip
        tip = cost*tipPercentage

        //Return tip
        if(binding.roundUpSwitch.isChecked)
            tip= ceil(tip)

        //Display the tip
        this.displayTip(tip)


    }

    private fun displayTip(tip:Double){
        //Format tip
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)

        //Display tip
        binding.tipResult.text = getString(R.string.tip_amount,formattedTip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}