package com.example.meteorpedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentContainer: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDefaultNavGraph()
    }

    private fun setDefaultNavGraph() {
        fragmentContainer = my_nav_host_fragment as NavHostFragment
        val inflater = fragmentContainer.findNavController().navInflater
        val graph = inflater.inflate(R.navigation.navigation)
        fragmentContainer.findNavController().graph = graph
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}