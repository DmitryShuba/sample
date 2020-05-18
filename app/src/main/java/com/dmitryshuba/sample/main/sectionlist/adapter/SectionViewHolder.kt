package com.dmitryshuba.sample.main.sectionlist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.dmitryshuba.sample.databinding.ItemSectionBinding
import com.dmitryshuba.sample.main.sectionlist.adapter.props.SectionProps

class SectionViewHolder(
    private val binding: ItemSectionBinding,
    private val listener: OnSectionSelectedCallback
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(props: SectionProps) {
        binding.props = props
        binding.listener = this.listener
    }
}