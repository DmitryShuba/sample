package com.dmitryshuba.sample.main.sectionlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dmitryshuba.sample.R
import com.dmitryshuba.sample.main.sectionlist.adapter.props.SectionProps

class SectionListAdapter(private val listener: OnSectionSelectedCallback) : RecyclerView.Adapter<SectionViewHolder>() {

    private var sectionList = listOf<SectionProps>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder =
        SectionViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_section,
                parent, false
            ),
            listener
        )

    override fun getItemCount(): Int = sectionList.size

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) =
        holder.bind(sectionList[position])

    fun updateUI(list: List<SectionProps>) {
        if (sectionList != list) {
            sectionList = list
            notifyDataSetChanged()
        }
    }
}