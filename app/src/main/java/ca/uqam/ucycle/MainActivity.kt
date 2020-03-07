package ca.uqam.ucycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import ca.uqam.ucycle.fragments.AlbumsFragment
import ca.uqam.ucycle.fragments.ArtistsFragment
import ca.uqam.ucycle.fragments.HomeFragment
import ca.uqam.ucycle.fragments.PostProductFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), Communicator {

    override fun passDataCom(key: String, value: String, fragment: Fragment) {
        val bundle = Bundle()
        bundle.putString(key, value)
        fragment.arguments = bundle
        toolbar.title = value
        openFragment(fragment)
    }

    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //keep actual fragment when rotating
        if (savedInstanceState == null) {
            //start home fragment
            toolbar.title = "Home"
            val homeFragment = HomeFragment.newInstance()

            openFragment(homeFragment)
        }


    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                toolbar.title = "Home"
                val homeFragment = HomeFragment.newInstance()

                openFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_albums -> {
                toolbar.title = "Albums"
                val albumsFragment = AlbumsFragment.newInstance()
                openFragment(albumsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_artists -> {
                  toolbar.title = "Artists"
                val artistsFragment = ArtistsFragment.newInstance()
                openFragment(artistsFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}
