package edu.us.ischool.yimengl.tipcalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.text.TextWatcher
import android.text.Editable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        var percentage: Int = 15
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val btnTip = findViewById<Button>(R.id.btnTip)
        val txtAmount = findViewById<EditText>(R.id.txtAmount)
        btnTip.isEnabled = false

        txtAmount.setOnClickListener{
            txtAmount.setText("")
        }

        txtAmount.setOnFocusChangeListener{ _, _
            -> txtAmount.setText("")}

        txtAmount.addTextChangedListener(object: TextWatcher {
            val textView: TextView = txtAmount
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!txtAmount.text.toString().contains('$')) {
                    textView.text = "$${txtAmount.text}"
                }
                if (txtAmount.text.toString().length > 2 && txtAmount.text.toString()[2] == '$') {
                    val temp = textView.text.toString().replace("$", "")
                    textView.text = "$${temp}"
                }
                txtAmount.setSelection(txtAmount.length())
                btnTip.isEnabled = !txtAmount.text.toString().isBlank() && (txtAmount.text.toString() != "$" && (txtAmount.text.toString() != "$."))
            }

            override fun afterTextChanged(p0: Editable?) {
                if (txtAmount.text.contains('.')) {
                    val decimal = txtAmount.text.split('.')[1]
                    val number = txtAmount.text.split('.')[0]
                    if (decimal.length > 2) {
                        Toast.makeText(getApplicationContext(),"Please enter a valid number!", Toast.LENGTH_SHORT).show()
                        textView.text = ""
                    }
                }
            }
        })

        radioGroup.setOnCheckedChangeListener{ radioGroup, id ->
            percentage = when(id) {
                R.id.radioButton10 -> 10
                R.id.radioButton15 -> 15
                R.id.radioButton18 -> 18
                R.id.radioButton20 -> 20
                else -> {0}
            }
        }

        btnTip.setOnClickListener(){
            val validAmount = txtAmount.text.substring(1,txtAmount.length()).toString().toDouble() * 100.toInt()
            val tip = "%.2f".format(validAmount * percentage / 10000)
            Toast.makeText(getApplicationContext(), "Tip: $$tip", Toast.LENGTH_SHORT).show();
        }
    }
}
