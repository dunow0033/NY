package com.example.ny.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ny.databinding.SchoolsRecyclerviewItemBinding
import com.example.ny.model.School
import com.example.ny.ui.SchoolViewHolder


class SchoolListAdapter(diffCallback: SchoolDiff) : ListAdapter<School, SchoolViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        return SchoolViewHolder.build(parent)
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SchoolDiff: DiffUtil.ItemCallback<School>() {
        override fun areItemsTheSame(oldItem: School, newItem: School): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: School, newItem: School): Boolean {
            return oldItem.school_name == newItem.school_name
        }
    }
}
