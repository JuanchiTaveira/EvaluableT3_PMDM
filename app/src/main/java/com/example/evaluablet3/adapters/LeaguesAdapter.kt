package com.example.evaluablet3.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluablet3.R

class LeaguesAdapter(var leaguesList: List<String>, var context: Context) : RecyclerView.Adapter<LeaguesAdapter.MyHolder>() {

    class MyHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var leagueName: TextView

        init {
            leagueName = itemView.findViewById(R.id.leagueName)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaguesAdapter.MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_league_recycler, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: LeaguesAdapter.MyHolder, position: Int) {
        holder.leagueName.text = leaguesList[position]
    }

    override fun getItemCount(): Int {
        return leaguesList.size
    }


}