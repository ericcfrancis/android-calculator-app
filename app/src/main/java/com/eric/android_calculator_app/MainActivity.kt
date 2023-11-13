package com.eric.android_calculator_app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  private lateinit var resultTextView: TextView
  private var operand1: Double = 0.0
  private var operator: String = ""
  private var isOperand1Input = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    resultTextView = findViewById(R.id.textViewResult)
  }

  fun onButtonClick(view: View) {
    if (view is Button) {
      val buttonText: String = view.text.toString()

      when {
        buttonText in "0123456789." -> handleDigitButtonClick(buttonText)
        buttonText in "+-X/" -> handleOperatorButtonClick(buttonText)
        buttonText == "=" -> handleEqualButtonClick()
        buttonText == "AC" -> handleACButtonClick()
        buttonText == "+/-" -> handleToggleSignButtonClick()
        buttonText == "%" -> handlePercentageButtonClick()
      }
    }
  }

  private fun handleDigitButtonClick(digit: String) {
    if (isOperand1Input) {
      resultTextView.text = digit
      isOperand1Input = false
    } else {
      resultTextView.append(digit)
    }
  }

  private fun handleOperatorButtonClick(operator: String) {
    if (!isOperand1Input) {
      if (this.operator.isNotEmpty()) {
        // Calculate result only if the second operand is already input
        calculateResult()
      }
    }

    this.operator = operator
    operand1 = resultTextView.text.toString().toDouble()
    isOperand1Input = true
  }

  private fun handleEqualButtonClick() {
    if (!isOperand1Input) {
      calculateResult()
      isOperand1Input = true
      operator = ""
    }
  }

  private fun handleACButtonClick() {
    resultTextView.text = "0"
    operand1 = 0.0
    operator = ""
    isOperand1Input = true
  }

  private fun handleToggleSignButtonClick() {
    val currentValue = resultTextView.text.toString().toDouble()
    resultTextView.text = (-currentValue).toString()
  }

  private fun handlePercentageButtonClick() {
    val currentValue = resultTextView.text.toString().toDouble()
    resultTextView.text = (currentValue / 100).toString()
  }

  private fun calculateResult() {
    val operand2 = resultTextView.text.toString().toDouble()

    when (operator) {
      "+" -> operand1 += operand2
      "-" -> operand1 -= operand2
      "X" -> operand1 *= operand2
      "/" -> operand1 /= operand2
    }

    val resultString = if (operand1 % 1 == 0.0) {
      operand1.toInt().toString()
    } else {
      operand1.toString()
    }

    resultTextView.text = resultString
  }

}

