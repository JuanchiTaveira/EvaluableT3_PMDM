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
import com.example.evaluablet3.adapters.LeaguesAdapter
import com.example.evaluablet3.databinding.FragmentFirstBinding
import org.json.JSONObject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var leaguesList: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillLeaguesList()

        binding.recyclerLeagues.adapter = LeaguesAdapter(leaguesList, requireActivity())
        binding.recyclerLeagues.layoutManager = LinearLayoutManager(requireActivity().applicationContext, RecyclerView.VERTICAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fillLeaguesList() {

        if (!leaguesList.isNullOrEmpty())
            return

        val leaguesRequest = JsonObjectRequest(Request.Method.GET,
            "https://www.thesportsdb.com/api/v1/json/3/all_leagues.php",
            null,
            { response ->
                val jsonArray = response.getJSONArray("leagues")

                for (i in 0 until jsonArray.length()) {
                    val actualLeague = jsonArray[i] as JSONObject
                    if (actualLeague.getString("strSport").equals("Soccer")) {
                        leaguesList.add(actualLeague.getString("strLeague"))
                    }
                }

                binding.recyclerLeagues.adapter?.notifyDataSetChanged()
            },
            null)

        Volley.newRequestQueue(requireActivity().applicationContext).add(leaguesRequest)
    }
}