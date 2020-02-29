package ca.uqam.ucycle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ca.uqam.ucycle.R

class PostProductFragment : Fragment{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_post_product, container, false)

    companion object {
        fun newInstance(): PostProductFragment = PostProductFragment()
    }
}