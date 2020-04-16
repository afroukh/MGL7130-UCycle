package ca.uqam.ucycle.ui.product

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import ca.uqam.ucycle.models.Product
import ca.uqam.ucycle.ui.category.CategoriesViewModel
import com.google.android.material.button.MaterialButton
import ca.uqam.ucycle.utils.GpsTracker
import ca.uqam.ucycle.R
import android.location.Geocoder
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class PostProductFragment : Fragment() {

    private lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var productsViewModel: ProductsViewModel
    private lateinit var productTitleInput: EditText
    private lateinit var autoCompleteCategories: AutoCompleteTextView
    private lateinit var productDescriptionInput: EditText
    private lateinit var productLocalisationInput: EditText
    private lateinit var imageProduct: ImageView
    private lateinit var btnPicGallery: Button
    private lateinit var btnPicCamera: Button
    private lateinit var btnLocalisation: MaterialButton
    private lateinit var btnSendProduct: MaterialButton
    private lateinit var filePath: Uri
    private var categoriesHashMap: MutableMap<String, String> = mutableMapOf()
    private var categoryId: String = ""
    var currentPath: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoriesViewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
        productsViewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)

        activity?.title = "Add new ad"
        var rootView = inflater.inflate(R.layout.fragment_post_product, container, false)
        productTitleInput = rootView.findViewById(R.id.product_title_input)
        productDescriptionInput = rootView.findViewById(R.id.product_description_input)
        productLocalisationInput = rootView.findViewById(R.id.product_localisation_input)
        imageProduct = rootView.findViewById(R.id.post_product_image)
        btnPicGallery = rootView.findViewById(R.id.btn_pic_gallery)
        btnPicCamera = rootView.findViewById(R.id.btn_pic_camera)
        btnLocalisation = rootView.findViewById(R.id.btn_localisation)
        btnSendProduct = rootView.findViewById(R.id.btn_send_product)





        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1)
        activity?.let { ActivityCompat.requestPermissions(it,  arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 123) }
        if (categoriesViewModel.categories.value == null) {
            categoriesViewModel.fetchCategories()
        }

        categoriesViewModel.categories.observe(viewLifecycleOwner, Observer {
            it.dropWhile { x -> x.name == "All" }
                .forEach { cat ->
                    adapter.add(cat.name.toString())
                    categoriesHashMap[cat.name.toString()] = cat.id.toString()
                }
        })

        autoCompleteCategories = view!!.findViewById(ca.uqam.ucycle.R.id.post_category_autocomplete)
        autoCompleteCategories.setAdapter(adapter)


        autoCompleteCategories.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                var selectedCategoryName = parent.getItemAtPosition(position)
                categoryId = categoriesHashMap[selectedCategoryName]!!
                Log.i("Founded Category", categoryId)
            }


        btnPicGallery.setOnClickListener {
            showFileChooser()
        }

        btnPicCamera.setOnClickListener {
            showCamera()
        }

        btnLocalisation.setOnClickListener {
            val gt = activity?.applicationContext?.let { it1 -> GpsTracker(it1) }
            var l = gt?.location
            if( l == null){
                Toast.makeText(activity?.applicationContext,"GPS unable to get Value",Toast.LENGTH_SHORT).show()
            }else {
                // test with montréal: 45.511963, -73.589755
                var lat = l.latitude
                var lon = l.longitude

                var geoCoder = Geocoder(activity, Locale.getDefault())
                var addresses = geoCoder.getFromLocation(lat.toDouble(),lon.toDouble(), 1)
                var adress: String = addresses[0].getAddressLine(0)
                var myCity =  addresses[0].locality
                var arrp = adress.toString().split(",")
                productLocalisationInput.setText(arrp[1])

            }
        }

        btnSendProduct.setOnClickListener {
            val product = Product(
                title = productTitleInput.text.toString(),
                categoryId = categoryId,
                description = productDescriptionInput.text.toString(),
                localisation = productLocalisationInput.text.toString(),
                urlImage = ""
            )
            productsViewModel.addProduct(product, categoryId, filePath)

            productsViewModel.result.observe(viewLifecycleOwner, Observer {
                if (it == null) {
                    Toast.makeText(
                        activity!!.applicationContext,
                        "Added successfully",
                        Toast.LENGTH_LONG
                    ).show()

                } else {
                    Toast.makeText(activity!!.applicationContext, it.message, Toast.LENGTH_LONG)
                        .show()

                }
            })

        }
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data?.data != null) {
            filePath = data.data!!
            var bitmap: Bitmap =
                MediaStore.Images.Media.getBitmap(activity?.contentResolver, filePath)
            imageProduct.setImageBitmap(bitmap)
            adaptImage(imageProduct)
        }

        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            try {
                val file = File(currentPath)
                filePath = Uri.fromFile(file)
                imageProduct.setImageURI(filePath)
                adaptImage(imageProduct)
            }
            catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun adaptImage(image: ImageView){
        image.background = null
        var layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.setMargins(0, 0, 0, 0)
        imageProduct.layoutParams = layoutParams
    }


    private fun showFileChooser() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,
            PICK_IMAGE_REQUEST
        )
    }

    @RequiresApi(Build.VERSION_CODES.FROYO)
    private fun showCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(activity!!.packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImage()
            }catch (e: IOException) {
                e.printStackTrace()
            }

            if (photoFile != null) {
                var photoUri = context?.let {
                    FileProvider.getUriForFile(it, "ca.uqa.ucycle.fileprovider", photoFile) }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(intent, TAKE_PICTURE)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.FROYO)
    private fun createImage(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageName = "JPEG_" + timeStamp + "_"
        var storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var image = File.createTempFile(imageName, ".jpg", storageDir)
        currentPath = image.absolutePath
        return image
    }


    companion object {
        fun newInstance(): PostProductFragment =
            PostProductFragment()
        private const val PICK_IMAGE_REQUEST = 123
        private const val TAKE_PICTURE = 1
    }
}