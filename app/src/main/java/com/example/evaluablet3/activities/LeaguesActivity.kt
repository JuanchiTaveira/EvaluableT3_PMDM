package com.example.evaluablet3.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.evaluablet3.R
import com.example.evaluablet3.adapters.LeaguesAdapter
import com.example.evaluablet3.adapters.TeamsAdapter
import com.example.evaluablet3.databinding.ActivityLeaguesBinding
import com.example.evaluablet3.model.Team
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LeaguesActivity : AppCompatActivity(), LeaguesAdapter.OnClickLeagueListener, TeamsAdapter.OnClickTeamListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityLeaguesBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLeaguesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main2)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://evaluablet3-pmdm-default-rtdb.europe-west1.firebasedatabase.app/")
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main2)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favs -> {
                findNavController(R.id.nav_host_fragment_content_main2).navigate(R.id.action_LeaguesFragment_to_favsFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun seeTeamsFromLeague(league: String) {
        val bundle = Bundle()
        bundle.putString("league", league)

        findNavController(R.id.nav_host_fragment_content_main2).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }

    override fun addOrRemoveTeamFromFavs(team: Team, add: Boolean) {
        if (add) {
            Snackbar.make(binding.root, "${team.name} agregado a favoritos", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(binding.root, "${team.name} eliminado de favoritos", Snackbar.LENGTH_SHORT).show()
        }
    }
}