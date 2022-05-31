package info.hasanaga.flexiblealertdialogexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import info.hasanaga.flexiblealertdialog.FlexibleAlertDialog

class Fragment1 : Fragment() {

    lateinit var resultTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        resultTextView = view.findViewById(R.id.textView2)

        view.findViewById<Button>(R.id.button1).setOnClickListener {
            showSample1();
        }

        view.findViewById<Button>(R.id.button2).setOnClickListener {
            showSample2();
        }


        view.findViewById<Button>(R.id.button3).setOnClickListener {
            showSample3();
        }


    }

    private fun showSample1() {

        FlexibleAlertDialog.Builder(requireContext())
            .setTitle("Simple dialog Title")
            .setParent(R.id.frame_layout)
            .setFragmentManager(childFragmentManager)
            .setMessage("Message")
            .setPositiveButton {
                it.dismiss()
            }
            .show()
    }

    private fun showSample2(){

        FlexibleAlertDialog.Builder(requireContext())
            .setTitle("Make Uppercase")
            .setParent(R.id.frame_layout)
            .setFragmentManager(childFragmentManager)
            .setView(R.layout.uppercase_layout)
            .setPositiveButton {
                it.dismiss()
            }
            .setEvent {

                onClickButton = { buttonType, view ->

                    if(buttonType == FlexibleAlertDialog.ButtonType.PositiveButton){

                        view?.findViewById<EditText>(R.id.edit_text)?.text?.let {
                            resultTextView.text = it.toString().uppercase()
                        }
                    }
                }

            }
            .show()
    }


    private fun showSample3() {

        FlexibleAlertDialog.Builder(requireContext())
            .setTitle("Calculator with custom View")
            .setParent(R.id.frame_layout)
            .setFragmentManager(childFragmentManager)
            .setPositiveButton("Close") {
                it.dismiss()
            }
            .setView(R.layout.custom_layout)
            .setEvent {

                var lastResult = ""

                fun calculate(param1: String, param2: String, operation: Char): String {

                    if (param1.isEmpty() || param2.isEmpty()) return "Invalid params";

                    val intParam1: Int = try {
                        param1.toInt()
                    } catch (e: NumberFormatException) {
                        return "Invalid Param1"
                    }
                    val intParam2: Int = try {
                        param2.toInt()
                    } catch (e: NumberFormatException) {
                        return "Invalid Param2"
                    }

                    return when (operation) {
                        '+' -> "${intParam1.toFloat() + intParam2}"
                        '-' -> "${intParam1.toFloat() - intParam2}"
                        '*' -> "${intParam1.toFloat() * intParam2}"
                        '/' -> "${if (intParam2 == 0) 0F else intParam1.toFloat() / intParam2}"
                        else -> ""
                    }
                }

                onCreateView = {

                    val num1: EditText = it.findViewById(R.id.number1);
                    val num2: EditText = it.findViewById(R.id.number2);
                    val result: TextView = it.findViewById(R.id.result);

                    it.findViewById<Button>(R.id.addition).setOnClickListener {
                        lastResult = calculate(num1.text.toString(), num2.text.toString(), '+')
                        result.text = lastResult
                    }

                    it.findViewById<Button>(R.id.subtraction).setOnClickListener {
                        lastResult = calculate(num1.text.toString(), num2.text.toString(), '-')
                        result.text = lastResult
                    }

                    it.findViewById<Button>(R.id.division).setOnClickListener {
                        lastResult = calculate(num1.text.toString(), num2.text.toString(), '/')
                        result.text = lastResult
                    }

                    it.findViewById<Button>(R.id.multiplication).setOnClickListener {
                        lastResult = calculate(num1.text.toString(), num2.text.toString(), '*')
                        result.text = lastResult
                    }

                }

                onClickButton = { buttonType, _ ->

                    if (buttonType == FlexibleAlertDialog.ButtonType.PositiveButton) {
                        resultTextView.text = lastResult
                    }
                }


            }
            .show()
    }


}