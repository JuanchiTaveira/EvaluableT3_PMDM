package com.example.evaluablet3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluablet3.adapters.TeamsAdapter
import com.example.evaluablet3.databinding.FragmentFavsBinding
import com.example.evaluablet3.model.Team
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FavsFragment : Fragment() {

    private var _binding: FragmentFavsBinding? = null
    private val binding get() = _binding!!
    private val teamsList: ArrayList<Team> = ArrayList()
    val database = FirebaseDatabase.getInstance("https://evaluablet3-pmdm-default-rtdb.europe-west1.firebasedatabase.app/")
    val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillTeamsList()

        binding.recyclerTeams.adapter = TeamsAdapter(teamsList, requireActivity())
        binding.recyclerTeams.layoutManager = LinearLayoutManager(requireActivity().applicationContext, RecyclerView.VERTICAL, false)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fillTeamsList() {

        if (teamsList.isNotEmpty())
            return

        database.getReference("users/${auth.currentUser!!.uid}/favorites").addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { dataSnapshot ->
                    val teamId = dataSnapshot.key
                    val teamData = dataSnapshot.value as Map<*, *>
                    val teamImage = teamData["image"] as String
                    val teamName = teamData["name"] as String

                    val team = Team(teamId!!, teamName, teamImage)
                    teamsList.add(team)
                }

                binding.recyclerTeams.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }
}