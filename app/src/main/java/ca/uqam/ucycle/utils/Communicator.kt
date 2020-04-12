package ca.uqam.ucycle.utils

import androidx.fragment.app.Fragment
import ca.uqam.ucycle.models.Category
import ca.uqam.ucycle.models.Product

interface Communicator {
//    fun passDataCom(title: String)
    fun passDataCom(key: String, value: Product, fragment: Fragment)
    fun passDataCom2(key: String, value: Category, fragment: Fragment)

}