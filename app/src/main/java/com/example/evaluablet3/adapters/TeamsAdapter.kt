package com.example.evaluablet3.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.evaluablet3.R
import com.example.evaluablet3.model.Team
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TeamsAdapter(var teamsList: List<Team>, var context: Context) : RecyclerView.Adapter<TeamsAdapter.MyHolder>() {

    val listener: OnClickTeamListener = context as OnClickTeamListener
    val database = FirebaseDatabase.getInstance("https://evaluablet3-pmdm-default-rtdb.europe-west1.firebasedatabase.app/")
    val auth = FirebaseAuth.getInstance()

    class MyHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var teamName: TextView
        var teamImg: ImageView
        var btnAddToFavs: ImageButton

        init {
            teamName = itemView.findViewById(R.id.teamName)
            teamImg = itemView.findViewById(R.id.teamImg)
            btnAddToFavs = itemView.findViewById(R.id.btnAddToFavs)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_team_recycler, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val actualTeam = teamsList[position]
        holder.teamName.text = actualTeam.name
        Glide.with(context).load(actualTeam.image).into(holder.teamImg)

        database.getReference("users/${auth.currentUser!!.uid}/favorites").child(actualTeam.idTeam).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    holder.btnAddToFavs.setImageResource(android.R.drawable.btn_star_big_on)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        holder.btnAddToFavs.setOnClickListener {
            listener.addTeamToFavs(actualTeam)
            holder.btnAddToFavs.setImageResource(android.R.drawable.btn_star_big_on)
        }
    }

    override fun getItemCount(): Int {
        return teamsList.size
    }

    interface OnClickTeamListener {
        fun addTeamToFavs(team: Team)
    }
}