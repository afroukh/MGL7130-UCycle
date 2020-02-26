package ca.uqam.ucycle

import androidx.fragment.app.Fragment

interface Communicator {
//    fun passDataCom(title: String)
    fun passDataCom(key: String, value: String, fragment: Fragment)
}