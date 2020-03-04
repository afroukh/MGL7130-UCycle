package ca.uqam.ucycle.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import ca.uqam.ucycle.R


class PostProductFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView = inflater.inflate(R.layout.fragment_post_product, container, false)
        val image = rootView.findViewById<ImageView>(R.id.post_product_image)

        image?.setOnClickListener {
            Log.i("My App", "Image View Clicked")
        }

        return rootView
    }




    companion object {
        fun newInstance(): PostProductFragment = PostProductFragment()
    }
}