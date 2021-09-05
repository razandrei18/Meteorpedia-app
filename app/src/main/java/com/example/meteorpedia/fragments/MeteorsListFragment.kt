package com.example.meteorpedia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.meteorpedia.R
import com.example.meteorpedia.adapters.MeteorsAdapter
import com.example.meteorpedia.databinding.FragmentMeteorsListBinding
import com.example.meteorpedia.models.MeteorModel
import com.example.meteorpedia.models.Status
import com.example.meteorpedia.viewmodels.MeteorsViewmodel
import kotlinx.android.synthetic.main.fragment_meteors_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MeteorsListFragment : Fragment(), MeteorsCallback {

    private lateinit var binding: FragmentMeteorsListBinding
    private val meteorsViewmodel by sharedViewModel<MeteorsViewmodel>()
    private val filteredMeteors : ArrayList<MeteorModel> = arrayListOf()

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

        binding.swipeRefreshMeteors.setOnRefreshListener {
            binding.swipeRefreshMeteors.isRefreshing = false

            meteorsViewmodel.resetMeteors()
            filteredMeteors.clear()
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

                        for(item in it.data){
                            if(item.fallenYear != null && !item.mass.isNullOrEmpty()) {
                                if (item.fallenYear.startsWith("19") || item.fallenYear.startsWith("20")) {
                                    filteredMeteors.add(item)
                                }
                            }
                        }

                        meteors_list_rv.apply {
                            adapter = MeteorsAdapter(filteredMeteors, this@MeteorsListFragment)
                            layoutManager = GridLayoutManager(activity, 1)
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
                }
                else {
                    binding.meteorsListRv.visibility = View.VISIBLE
                    binding.errorMessageTv.apply {
                        visibility = View.VISIBLE
                        text = resources.getString(R.string.something_went_wrong_message)
                    }
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

    }
}

interface MeteorsCallback {
    fun displayFallenLocation(meteor: MeteorModel)
}