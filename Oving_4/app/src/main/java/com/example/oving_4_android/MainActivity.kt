package com.example.oving_4_android


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), PictureTitlesFragment.SendIndex {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * @param position The index position of the clicked item
     */
    override fun sendIndex(position: Int) {
        val f: ImageViewFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView4) as ImageViewFragment
        f.displayReceivedData(position)
    }


}

