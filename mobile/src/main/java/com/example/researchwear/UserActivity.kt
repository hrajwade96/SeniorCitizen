package com.example.researchwear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.researchwear.interfaces.CallBackInterface
import com.example.researchwear.ui.*

class UserActivity : AppCompatActivity(),
    CallBackInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doFragmentTransaction(LoginFragment(),false,"SetPassFragment",null)
    }
    private fun doFragmentTransaction(
        fragment: Fragment,
        addToBackStack: Boolean,
        tag: String,
        bundle: Bundle?
    ) {
        if (bundle != null) {
            fragment.arguments = bundle
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
    }

    override fun openReg(mno:String) {

        doFragmentTransaction(RegisterFragment(mno),false,"RegisterFragment",null)
    }

    override fun openDashBoard() {
        doFragmentTransaction(DashboardFragment(),false,"DashboardFragment",null)
    }

    override fun openLogin() {
        doFragmentTransaction(LoginFragment(),false,"LoginFragment",null)
    }

    override fun openLostFragment() {
        doFragmentTransaction(LostFragment(),true,"LostFragment",null)
    }

    override fun openWheelChairFragment() {
        doFragmentTransaction(WheelChairFragment(),true,"WheelChairFragment",null)
    }

    override fun openWalkingStickFragment() {
        doFragmentTransaction(WalkingStickFragment(),true,"WalkingStickFragment",null)
    }

    override fun openSanitaryFragment() {
        doFragmentTransaction(SanitaryAssistanceFragment(),true,"SanitaryAssistanceFragment",null)
    }

    override fun openCustomHelpFragement() {
       // doFragmentTransaction(CustomHelpFragment(),false,"SetPassFragment",null)
    }

    override fun openMedicalFragment() {

    }

    override fun openMapFragment() {
        //doFragmentTransaction(MapsFragment(),true,"MapsFragment",null)
        var intent = Intent(this,MapsActivity::class.java)
       intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun openCallHelplineFragment() {

        doFragmentTransaction(CallHelplineFragment(),true,"SetPassFragment",null)

    }

}
