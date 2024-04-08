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
import com.example.evaluablet3.databinding.ActivityMainBinding
import com.example.evaluablet3.model.Team
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), LeaguesAdapter.OnClickLeagueListener, TeamsAdapter.OnClickTeamListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main2)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

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
            R.id.action_favs -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun seeTeamsFromLeague(league: String) {
        val bundle = Bundle()
        bundle.putString("league", league)

        findNavController(R.id.nav_host_fragment_content_main2).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }

    override fun addTeamToFavs(team: Team) {
        Snackbar.make(binding.root, "${team.name} agregado a favoritos", Snackbar.LENGTH_SHORT).show()
    }
}