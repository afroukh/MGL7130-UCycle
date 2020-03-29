package ca.uqam.ucycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import ca.uqam.ucycle.data.Category
import ca.uqam.ucycle.data.Product
import ca.uqam.ucycle.fragments.FavoritFragment
import ca.uqam.ucycle.fragments.ProfilFragment
import ca.uqam.ucycle.fragments.HomeFragment
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
            R.id.navigation_favorit -> {
                toolbar.title = "Favorit"
                val favoritFragment = FavoritFragment.newInstance()
                openFragment(favoritFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profil -> {
                  toolbar.title = "Profil"
                val ProfilFragment = ProfilFragment.newInstance()
                openFragment(ProfilFragment)
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

        val frame = findViewById<FrameLayout>(R.id.collection_wrapper)
        if (supportFragmentManager.findFragmentById(R.id.collection_wrapper) != null) {
            frame.removeAllViews()
        }

        transaction.add(R.id.collection_wrapper, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
