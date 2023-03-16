package com.example.beercatalogue.presenter.splashScreen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.beercatalogue.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)

        lifecycleScope.launch {
            delay(2000)
            if(sharedPref.getBoolean("first_run", true)){
                val action =
                    SplashScreenFragmentDirections.actionSplashScreenFragmentToOnBoardingFragment()
                findNavController().navigate(action)
            }else{
                val action =
                    SplashScreenFragmentDirections.actionSplashScreenFragmentToBeerCatalogueListFragment()
                findNavController().navigate(action)
            }
        }
    }

}