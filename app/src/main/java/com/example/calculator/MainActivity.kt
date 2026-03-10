package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CalculatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CalculatorViewModel::class.java]

        binding.displayTextView.text = viewModel.displayValue

        setButtonClickListeners()

        viewModel.displayValueLiveData.observe(this) { displayValue ->
            binding.displayTextView.text = displayValue
        }
    }

    private fun setButtonClickListeners() {
        binding.button0.setOnClickListener { viewModel.onDigitClicked("0") }
        binding.button1.setOnClickListener { viewModel.onDigitClicked("1") }
        binding.button2.setOnClickListener { viewModel.onDigitClicked("2") }
        binding.button3.setOnClickListener { viewModel.onDigitClicked("3") }
        binding.button4.setOnClickListener { viewModel.onDigitClicked("4") }
        binding.button5.setOnClickListener { viewModel.onDigitClicked("5") }
        binding.button6.setOnClickListener { viewModel.onDigitClicked("6") }
        binding.button7.setOnClickListener { viewModel.onDigitClicked("7") }
        binding.button8.setOnClickListener { viewModel.onDigitClicked("8") }
        binding.button9.setOnClickListener { viewModel.onDigitClicked("9") }

        binding.buttonPlus.setOnClickListener { viewModel.onOperationClicked("+") }
        binding.buttonMinus.setOnClickListener { viewModel.onOperationClicked("-") }
        binding.buttonMultiply.setOnClickListener { viewModel.onOperationClicked("*") }
        binding.buttonDivide.setOnClickListener { viewModel.onOperationClicked("/") }

        binding.buttonDecimal.setOnClickListener { viewModel.onDecimalClicked() }
        binding.buttonClear.setOnClickListener { viewModel.onClearClicked() }
        binding.buttonEquals.setOnClickListener { viewModel.onEqualsClicked() }
        binding.buttonPlusMinus.setOnClickListener { viewModel.onPlusMinusClicked() }
        binding.buttonPercent.setOnClickListener { viewModel.onPercentClicked() }
    }
}