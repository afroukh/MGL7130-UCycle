package ca.uqam.ucycle

import androidx.fragment.app.Fragment
import ca.uqam.ucycle.data.Category
import ca.uqam.ucycle.data.Product

interface Communicator {
//    fun passDataCom(title: String)
    fun passDataCom(key: String, value: Product, fragment: Fragment)
    fun passDataCom2(key: String, value: Category, fragment: Fragment)

}