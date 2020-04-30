package com.example.researchwear.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.example.researchwear.R
import kotlinx.android.synthetic.main.fragment_call_helpline.*


class CallHelplineFragment : Fragment() {

     val REQUEST_CALL=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makephoneCall()
      btnCall.setOnClickListener {
         makephoneCall()
      }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_call_helpline, container, false)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CALL-> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makephoneCall()
                } else {
                    Toast.makeText(context, "Permission DENIED", Toast.LENGTH_SHORT).show()
                }
            }

            }
        }

    fun makephoneCall(){

        //  if (Mno.trim().isNotEmpty()) {

        if (ContextCompat.checkSelfPermission(
                context!!,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(android.Manifest.permission.CALL_PHONE),
                REQUEST_CALL
            )
        } else {
            val dial = "tel:+919172562880"
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse(dial)))
        }


//        }else {
//            Toast.makeText(context,"Enter Phone No",Toast.LENGTH_SHORT).show()
//        }
    }

}
