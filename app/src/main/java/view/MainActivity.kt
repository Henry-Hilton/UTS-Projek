package view

import com.ubaya.habittracker.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.ubaya.habittracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // ANMP Week 1 - ViewBinding in Activity (NMP Week 2 pattern)
    private lateinit var binding: ActivityMainBinding

    // ANMP Week 1 - NavController manages navigation within NavHost
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // NMP Week 2 - ViewBinding setup
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ANMP Week 1 - get NavController from NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.hostFragment) as NavHostFragment
        navController = navHostFragment.navController

        // ANMP Week 1 - connects action bar back button to NavController
        setupActionBarWithNavController(navController)
    }

    // ANMP Week 1 - handles the soft back button in the action bar
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}