package com.example.meteorpedia

import android.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentContainer: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDefaultNavGraph(null)
    }

    private fun setDefaultNavGraph(bundle: Bundle?) {
        fragmentContainer = my_nav_host_fragment as NavHostFragment
        val inflater = fragmentContainer.findNavController().navInflater
        val graph = inflater.inflate(R.navigation.navigation)
        fragmentContainer.findNavController().setGraph(graph, bundle)
    }
}