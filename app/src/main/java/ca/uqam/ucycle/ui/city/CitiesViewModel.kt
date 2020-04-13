package ca.uqam.ucycle.ui.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.uqam.ucycle.data.CityRepository
import ca.uqam.ucycle.data.ProductRepository
import ca.uqam.ucycle.models.City
import ca.uqam.ucycle.models.NODE_CITIES
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class CitiesViewModel : ViewModel() {
    
    private  val _cities = MutableLiveData<List<City>>()
    var cities: LiveData<List<City>> =  _cities

    private val _result = MutableLiveData<Exception?>()
    var result: LiveData<Exception?> = _result

    var cityRepository = CityRepository()

    fun addCity(city: City) {

        cityRepository.addCity(city)
        result = cityRepository.result
    }

    fun fetchCities() {
        cityRepository.fetchCities()
        cities = cityRepository.cities
    }

    companion object {
        fun newInstance(): CityRepository = CityRepository()
    }
}