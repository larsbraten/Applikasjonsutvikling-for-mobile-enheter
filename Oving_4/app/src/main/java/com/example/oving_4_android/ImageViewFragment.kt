package com.example.oving_4_android


import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

/*
Data class for the Picture object
 */
data class Picture(val url: String, val description: String)

var images = listOf(
    Picture(
        "https://img.freepik.com/free-photo/beautiful-colorful-sunset_1048-2416.jpg?size=626&ext=jpg",
        "A picture of the sun setting"
    ),
    Picture(
        "https://static01.nyt.com/images/2020/10/29/style/28MOON-01/oakImage-1603985177355-mediumSquareAt3X.jpg",
        "The Moon is Earth's only natural satellite."
    ),
    Picture(
        "https://img.freepik.com/free-photo/beautiful-tropical-beach-sea-ocean-with-white-cloud-blue-sky-copyspace_74190-8663.jpg?size=626&ext=jpg&ga=GA1.2.1958979550.1631404800",
        "A picture of the sea"
    ),
    Picture(
        "https://cdn.shopify.com/s/files/1/0278/5145/6627/products/image-0-compressed_1800x1800.jpg?v=1598979076",
        "Man’s best friend, the dog."
    ),
    Picture(
        "https://i.pinimg.com/474x/ec/a2/cf/eca2cf9ca61de74ff79c495dc240bb80.jpg",
        "Cats are highly intelligent creatures"
    )
)


class ImageViewFragment : Fragment() {

    var description: TextView? = null
    var image: ImageView? = null
    var button: Button? = null
    var button2: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_view, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        description = view.findViewById<View>(R.id.description) as TextView
        description?.text = images[0].description
        image = view.findViewById<View>(R.id.imageView) as ImageView
        image?.let { Glide.with(this).load(images[0].url).dontAnimate().centerCrop().into(it) }
        button = view.findViewById<View>(R.id.button) as Button
        button2 = view.findViewById<View>(R.id.button2) as Button
    }

    /*
    Function which displays data based on the index received from fragment 1.
     */
    fun displayReceivedData(position: Int) {
        var imageIndex = position

        button?.setOnClickListener {
            if (imageIndex != 0) {
                imageIndex -= 1
                image?.let {
                    Glide.with(this).load(images[imageIndex].url).dontAnimate().centerCrop().into(it)
                }
                description?.text = images[imageIndex].description
            } else
                imageIndex = 4
            image?.let {
                Glide.with(this).load(images[imageIndex].url).dontAnimate().centerCrop().into(it)
            }
            description?.text = images[imageIndex].description
        }

        button2?.setOnClickListener {
            if (imageIndex != 4) {
                imageIndex += 1
                image?.let {
                    Glide.with(this).load(images[imageIndex].url).dontAnimate().centerCrop().into(it)
                }
                description?.text = images[imageIndex].description
            } else
                imageIndex = 0
            image?.let {
                Glide.with(this).load(images[imageIndex].url).dontAnimate().centerCrop().into(it)
            }
            description?.text = images[imageIndex].description
        }
        image?.let {
            Glide.with(this).load(images[imageIndex].url).dontAnimate().centerCrop().into(it)
        }
        description?.text = images[imageIndex].description
    }
}
