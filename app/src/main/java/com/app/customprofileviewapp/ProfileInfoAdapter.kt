package com.app.customprofileviewapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProfileInfoAdapter(val context: Context, val infoList: ArrayList<String>) :
    RecyclerView.Adapter<ProfileInfoAdapter.ProfileViewHolder>() {
    class ProfileViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tv_name: TextView = itemView.findViewById(R.id.tv_name)
        val iv_logo: ImageView = itemView.findViewById(R.id.iv_logo)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_info, parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.tv_name.text = infoList[position]
    }

    override fun getItemCount(): Int {
        return infoList.size
    }
}