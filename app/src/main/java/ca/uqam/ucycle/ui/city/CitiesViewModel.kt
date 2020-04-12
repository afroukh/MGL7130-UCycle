package ca.uqam.ucycle.ui.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.uqam.ucycle.models.City
import ca.uqam.ucycle.models.NODE_CITIES
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class CitiesViewModel : ViewModel() {
    
    private val dbCities = FirebaseDatabase.getInstance().getReference(NODE_CITIES)

    private  val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>>
        get() = _cities

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    fun addCity(city: City) {

        city.id = dbCities.push().key
        dbCities.child(city.id!!).setValue(city)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }

    fun fetchCities() {
        dbCities.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapShot: DataSnapshot) {
                if (snapShot.exists()) {
                    val cities = mutableListOf<City>()
                    for(citySnapshot in snapShot.children) {
                        val city = citySnapshot.getValue(City::class.java)
                        city?.id = citySnapshot.key
                        city?.let { cities.add(it) }
                    }
                    _cities.value = cities
                }
            }

        })
    }
}