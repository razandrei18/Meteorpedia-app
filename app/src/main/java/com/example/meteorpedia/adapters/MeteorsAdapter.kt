package com.example.meteorpedia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.meteorpedia.BR
import com.example.meteorpedia.R
import com.example.meteorpedia.fragments.MeteorsCallback
import com.example.meteorpedia.models.MeteorModel

class MeteorsAdapter(private val meteorsArray: ArrayList<MeteorModel>,
                     private val callback: MeteorsCallback
) : RecyclerView.Adapter<MeteorsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeteorsViewHolder {
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)
        return MeteorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeteorsViewHolder, position: Int) {
        holder.bind(meteorsArray, callback, position)
    }

    override fun getItemCount(): Int {
        return meteorsArray.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.meteor_item_layout
    }
}

class MeteorsViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
 //   private val filteredMeteors : ArrayList<MeteorModel> = arrayListOf()
    fun bind(meteorsArray: ArrayList<MeteorModel>, callback: MeteorsCallback, position: Int) {

      /*  for(item in meteorsArray){
            if(item.fallenYear != null && !item.mass.isNullOrEmpty()) {
                if (item.fallenYear.startsWith("19") || item.fallenYear.startsWith("20")) {
                    filteredMeteors.add(item)
                }
            }
        }*/

        binding.setVariable(BR.meteorData, meteorsArray.sortedByDescending { it.mass!!.toFloat() }[position])
        binding.setVariable(BR.callback, callback)
        binding.executePendingBindings()
    }
}