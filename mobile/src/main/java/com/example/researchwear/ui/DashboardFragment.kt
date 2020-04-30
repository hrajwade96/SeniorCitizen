package com.example.researchwear.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.researchwear.R
import com.example.researchwear.config.SharedPrefManager
import com.example.researchwear.interfaces.CallBackInterface
import com.example.researchwear.interfaces.INetworkAPI
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.fragment_dashboard.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class DashboardFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPrefManager = SharedPrefManager(context!!)
        retro()

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            sharedPrefManager.clearLoginSession()
            callBackInterface.openLogin()
        }
        card1.setOnClickListener{
         callBackInterface.openCustomHelpFragement()
        }
        card2.setOnClickListener{
           callBackInterface.openWheelChairFragment()
        }
        card3.setOnClickListener{
           callBackInterface.openMedicalFragment()
        }
        card4.setOnClickListener {
  callBackInterface.openLostFragment()
        }
        card5.setOnClickListener{
           callBackInterface.openMapFragment()
        }
        card9.setOnClickListener {
            callBackInterface.openCallHelplineFragment()
        }
        card10.setOnClickListener {
            callBackInterface.openSanitaryFragment()
        }
    }

    fun retro()
    {
        val retrofit = Retrofit.Builder().addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://research-app12.herokuapp.com/").build()

        val postsApi = retrofit.create(INetworkAPI::class.java)

        var response = postsApi.getHomeAPI()

        response
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(IoScheduler())
            .subscribe({response->

                when(response.code()){
                    200 ->{

                        var resData = response.body()

                //  Toast.makeText(context,"SUCCESS : ${resData!!.homeList[0].title}", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                     //   Toast.makeText(context,"HTTP ERROR : ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
                response.body()
            },{error ->
//                Toast.makeText(context,"${error.message}", Toast.LENGTH_SHORT).show()
            })

    }

}
