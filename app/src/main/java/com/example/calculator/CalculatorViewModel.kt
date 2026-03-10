package com.example.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.math.BigDecimal
import java.math.RoundingMode

class CalculatorViewModel : ViewModel() {

    private val _displayValueLiveData = MutableLiveData<String>("0")
    val displayValueLiveData: LiveData<String> = _displayValueLiveData

    var displayValue: String = "0"
        set(value) {
            field = value
            _displayValueLiveData.value = value
        }

    private var firstOperand: BigDecimal? = null
    private var currentOperation: String? = null

    fun onDigitClicked(digit: String) {
        if (displayValue == "0" || displayValue == "Error") {
            displayValue = digit
        } else {
            displayValue += digit
        }
    }

    fun onOperationClicked(operation: String) {
        try {
            firstOperand = displayValue.toBigDecimal()
            currentOperation = operation
            displayValue = "0"
        } catch (e: NumberFormatException) {
            displayValue = "Error"
        }
    }

    fun onDecimalClicked() {
        if (!displayValue.contains(".")) {
            displayValue += "."
        }
    }

    fun onClearClicked() {
        displayValue = "0"
        firstOperand = null
        currentOperation = null
    }

    fun onEqualsClicked() {
        if (firstOperand != null && currentOperation != null) {
            try {
                val secondOperand = displayValue.toBigDecimal()
                val result = performOperation(firstOperand!!, secondOperand, currentOperation!!)
                displayValue = result.toString()
                firstOperand = null
                currentOperation = null
            } catch (e: NumberFormatException) {
                displayValue = "Error"
            } catch (e: ArithmeticException) {
                displayValue = "Error" // Handle division by zero
            }
        }
    }

    fun onPlusMinusClicked() {
        try {
            val currentValue = displayValue.toBigDecimal()
            displayValue = currentValue.multiply(BigDecimal("-1")).toString()
        } catch (e: NumberFormatException) {
            displayValue = "Error"
        }
    }

    fun onPercentClicked() {
        try {
            val currentValue = displayValue.toBigDecimal()
            displayValue = currentValue.divide(BigDecimal("100")).toString()
        } catch (e: NumberFormatException) {
            displayValue = "Error"
        }
    }

    private fun performOperation(firstOperand: BigDecimal, secondOperand: BigDecimal, operation: String): BigDecimal {
        return when (operation) {
            "+" -> firstOperand.add(secondOperand)
            "-" -> firstOperand.subtract(secondOperand)
            "*" -> firstOperand.multiply(secondOperand)
            "/" -> {
                if (secondOperand.compareTo(BigDecimal.ZERO) == 0) {
                    throw ArithmeticException("Division by zero")
                }
                firstOperand.divide(secondOperand, 10, RoundingMode.HALF_UP)
            }
            else -> throw IllegalArgumentException("Invalid operation")
        }
    }
}