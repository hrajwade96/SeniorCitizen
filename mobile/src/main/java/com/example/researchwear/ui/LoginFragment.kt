package com.example.researchwear.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.researchwear.interfaces.CallBackInterface

import com.example.researchwear.R
import com.example.researchwear.config.SharedPrefManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class LoginFragment : Fragment() {
    val db = FirebaseFirestore.getInstance()
    var mno = ""
    var RC_SIGN_IN = 100
    var isAdmin = false
    var flag= false
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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPrefManager = SharedPrefManager(context!!)
        if (sharedPrefManager.isLoggedIn()!!){

            callBackInterface.openDashBoard()
        }
else {

            Toast.makeText(context, "not logged in", Toast.LENGTH_SHORT).show()




            FirebaseApp.initializeApp(context!!)


            val providers = arrayListOf(

                // AuthUI.IdpConfig.PhoneBuilder().build()
                AuthUI.IdpConfig.PhoneBuilder().build()
            )

            startActivityForResult(
                AuthUI.getInstance()

                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN
            )
            //  var phoneNumber:PhoneNumber= PhoneNumber.emptyPhone()


        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    mno=   IdpResponse.fromResultIntent(data)?.phoneNumber.toString()
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(context,
                    IdpResponse.fromResultIntent(data)?.phoneNumber.toString(),
                    Toast.LENGTH_LONG).show()


                checkIfNewUser()


            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
    fun checkIfAdmin():Boolean {
        isAdmin = false

    db.collection("users").whereEqualTo("mno", mno).whereEqualTo("isAdmin", true)
            .get().addOnSuccessListener { users ->

            isAdmin = users.documents.isNotEmpty()
            callBackInterface.openDashBoard()
            }
        return isAdmin
    }

    fun checkIfNewUser():Boolean {





         db.collection("users").whereEqualTo("mno", mno)
            .get().addOnSuccessListener { users ->

                if (users.documents.isEmpty()) {
                    Toast.makeText(context, "New User", Toast.LENGTH_SHORT).show()
                    flag = false

                    val user = FirebaseAuth.getInstance().currentUser
                    callBackInterface.openReg(mno)



                } else {
                    flag = true
                    callBackInterface.openDashBoard()
//                    Toast.makeText(context, "Already Existing user", Toast.LENGTH_SHORT).show()
                    checkIfAdmin()



                    //  Toast.makeText(this, "DocumentSnapshot data: ${userList}",Toast.LENGTH_LONG).show()

                }
            }


        return flag
    }
}
