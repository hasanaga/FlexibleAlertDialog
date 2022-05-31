package info.hasanaga.flexiblealertdialogexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import info.hasanaga.flexiblealertdialog.FlexibleAlertDialog

class Fragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        view.findViewById<Button>(R.id.button).setOnClickListener {



            FlexibleAlertDialog.Builder(requireContext())
                .setTitle("Dialog from Fragment2")
                .setParent(R.id.frame_layout)
                .setFragmentManager(childFragmentManager)
                .setMessage("Message")
                .setNegativeButton {
                    it.dismiss()
                }
                .setPositiveButton {
                    it.dismiss()
                }
                .show()
        }

    }
}