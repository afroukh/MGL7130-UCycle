package ca.uqam.ucycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBar
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import ca.uqam.ucycle.data.Category
import ca.uqam.ucycle.data.Product
import ca.uqam.ucycle.fragments.AlbumsFragment
import ca.uqam.ucycle.fragments.ArtistsFragment
import ca.uqam.ucycle.fragments.HomeFragment
import ca.uqam.ucycle.fragments.PostProductFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), Communicator {

    override fun passDataCom(key: String, value: Product, fragment: Fragment) {
        val bundle = Bundle()
        bundle.putParcelable(key, value)
        fragment.arguments = bundle
        toolbar.title = value.title
        openFragment(fragment)
    }
    override fun passDataCom2(key: String, value: Category, fragment: Fragment) {
        val bundle = Bundle()
        bundle.putParcelable(key, value)
        fragment.arguments = bundle
        openFragment2(fragment)
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

    private fun openFragment2(fragment: Fragment) {

        val transaction = supportFragmentManager.beginTransaction()

        var frame = findViewById<FrameLayout>(R.id.collection_wrapper)
        if (supportFragmentManager.findFragmentById(R.id.collection_wrapper) != null) {
            frame.removeAllViews()
        }

        transaction.add(R.id.collection_wrapper, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
