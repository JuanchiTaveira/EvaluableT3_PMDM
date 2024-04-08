package com.example.evaluablet3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.evaluablet3.adapters.TeamsAdapter
import com.example.evaluablet3.databinding.FragmentSecondBinding
import com.example.evaluablet3.model.Team
import org.json.JSONObject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var league: String
    private val teamsList: ArrayList<Team> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        league = arguments?.getString("league").toString()

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

        if (!teamsList.isNullOrEmpty())
            return

        val teamsRequest = JsonObjectRequest(
            Request.Method.GET,
            "https://www.thesportsdb.com/api/v1/json/3/search_all_teams.php?l=" + league,
            null,
            { response ->
                val jsonArray = response.getJSONArray("teams")

                for (i in 0 until jsonArray.length()) {
                    val actualTeam = jsonArray[i] as JSONObject
                    teamsList.add(Team(actualTeam.getString("idTeam"), actualTeam.getString("strTeam"), actualTeam.getString("strTeamBadge")))
                }

                binding.recyclerTeams.adapter?.notifyDataSetChanged()
            },
            null)

        Volley.newRequestQueue(requireActivity().applicationContext).add(teamsRequest)
    }
}