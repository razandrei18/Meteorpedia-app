package com.example.meteorpedia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meteorpedia.R
import com.example.meteorpedia.adapters.MeteorsAdapter
import com.example.meteorpedia.databinding.FragmentMeteorsListBinding
import com.example.meteorpedia.models.MeteorModel
import com.example.meteorpedia.models.Status
import com.example.meteorpedia.viewmodels.MeteorsViewmodel
import kotlinx.android.synthetic.main.fragment_meteors_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MeteorsListFragment : Fragment(), MeteorsCallback {

    private lateinit var binding: FragmentMeteorsListBinding
    private val meteorsViewmodel by viewModel<MeteorsViewmodel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meteors_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backBtn: AppCompatImageView = requireActivity().findViewById(R.id.back_btn)
        backBtn.visibility = View.GONE


        binding.swipeRefreshMeteors.setOnRefreshListener {
            binding.swipeRefreshMeteors.isRefreshing = false
            getMeteorsDetails()
        }

        getMeteorsDetails()
        observeIsLoading()
        observeMeteorsData()
    }

    private fun getMeteorsDetails(){
        meteorsViewmodel.getMeteorsData()
    }

    private fun observeMeteorsData(){
        meteorsViewmodel.getMeteorsResponse.observe(viewLifecycleOwner, {
            it?.let {
                if(it.status == Status.SUCCESS){
                    if(!it.data.isNullOrEmpty()){
                        binding.meteorsListRv.visibility = View.VISIBLE
                        binding.errorMessageTv.visibility = View.GONE
                        val filteredMeteors : ArrayList<MeteorModel> = arrayListOf()
                        for(item in it.data){
                            if(item.fallenYear != null && !item.mass.isNullOrEmpty()) {
                                if (item.fallenYear.startsWith("19") || item.fallenYear.startsWith("20")) {
                                    filteredMeteors.add(item)
                                }
                            }
                        }

                        meteors_list_rv.apply {
                            adapter = MeteorsAdapter(filteredMeteors, this@MeteorsListFragment)
                            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                            setHasFixedSize(true)
                        }
                    }
                    else{
                        binding.meteorsListRv.visibility = View.VISIBLE
                        binding.errorMessageTv.apply {
                            visibility = View.VISIBLE
                            text = resources.getString(R.string.no_meteors_error_message)
                        }
                    }
                    meteorsViewmodel.resetMeteors()
                }
                else {
                    binding.meteorsListRv.visibility = View.VISIBLE
                    binding.errorMessageTv.apply {
                        visibility = View.VISIBLE
                        text = resources.getString(R.string.something_went_wrong_message)
                    }
                    meteorsViewmodel.resetMeteors()
                }
            }
        })
    }

    private fun observeIsLoading() {
        meteorsViewmodel.isLoading.observe(viewLifecycleOwner, {
            binding.isLoading = it ?: true
        })
    }

    override fun displayFallenLocation(meteor: MeteorModel) {
        if(meteor.geolocation == null || meteor.geolocation.latitude.isEmpty() || meteor.geolocation.longitude.isEmpty()) {
            Toast.makeText(context, getString(R.string.location_unavailable), Toast.LENGTH_SHORT).show()
        }
        else{
            val bundle = Bundle()
            bundle.putString("meteor_name", meteor.name)
            bundle.putString("meteor_fallen_year", meteor.fallenYear!!.subSequence(0, 4).toString())
            bundle.putString("meteor_lat", meteor.geolocation.latitude)
            bundle.putString("meteor_long", meteor.geolocation.longitude)
            view?.findNavController()!!.navigate(R.id.action_meteorsListFragment_to_meteorsMapFragment, bundle)
        }
    }
}

interface MeteorsCallback {
    fun displayFallenLocation(meteor: MeteorModel)
}