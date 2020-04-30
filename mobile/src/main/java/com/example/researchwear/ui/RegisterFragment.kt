package com.example.researchwear.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.researchwear.interfaces.CallBackInterface
import com.example.researchwear.R
import com.example.researchwear.config.SharedPrefManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment(mno:String) : Fragment() {

    private var mno=mno
    private lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var callBackInterface: CallBackInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CallBackInterface) {
            this.callBackInterface = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPrefManager = SharedPrefManager(context!!)
        btnRegister.setOnClickListener {
            putData()
            sharedPrefManager.createLoginSession(mno)
            callBackInterface.openDashBoard()
        }
    }
    val db = FirebaseFirestore.getInstance()

    fun putData()
    {

        val user = hashMapOf(

            "mno" to mno,
            "illness" to etIllness.text.toString(),
            "UserID" to etEmpID.text.toString(),
            "isAdmin" to false
        )

        db.collection("users").add(user)


            .addOnSuccessListener { documentReference ->
//                Toast.makeText(context,"Registered successfully", Toast.LENGTH_SHORT).show()
                sharedPrefManager.createLoginSession(mno)
                callBackInterface.openDashBoard()

            }
            .addOnFailureListener { e ->
                Log.w("putData", "Error adding document", e)
            }
    }
}
