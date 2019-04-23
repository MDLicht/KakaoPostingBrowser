package com.mdlicht.zb.kakaopostingbrowser.dialog


import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RadioButton
import com.mdlicht.zb.kakaopostingbrowser.R
import com.mdlicht.zb.kakaopostingbrowser.databinding.FragmentOrderDialogBinding


class OrderDialog : DialogFragment() {
    lateinit var binding: FragmentOrderDialogBinding
    private var listener: OnOrderListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        setStyle(STYLE_NO_TITLE, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_dialog, container, false)
        binding.apply {
            btnCancel.setOnClickListener {
                listener?.onCancelClick(this@OrderDialog)
            }
            btnConfirm.setOnClickListener {
                val checkedChild = radioGroup.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
                listener?.onConfirmClick(this@OrderDialog, radioGroup.indexOfChild(checkedChild))
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnOrderListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnOrderListener {
        // TODO: Update argument type and name
        fun onCancelClick(dialogFragment: DialogFragment)
        fun onConfirmClick(dialogFragment: DialogFragment, order: Int)
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            OrderDialog().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
