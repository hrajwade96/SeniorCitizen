package com.example.researchwear.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.researchwear.R
import com.example.researchwear.SendNotificationClasses.SendNotif
import kotlinx.android.synthetic.main.fragment_lost.*


class WheelChairFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_wheelchair, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSend.setOnClickListener {
                var body = etSendText.text.toString()
                SendNotif().sendNotificationToPatner(body)
        }
    }

}
