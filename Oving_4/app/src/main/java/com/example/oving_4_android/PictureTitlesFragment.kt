package com.example.oving_4_android

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment

open class PictureTitlesFragment : ListFragment() {

    interface SendIndex {
        /**
         * @param position The index position of the clicked item
         */
        fun sendIndex(position: Int)
    }

    private var sendData: SendIndex? = null
    private var list: Array<String> = arrayOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list = resources.getStringArray(R.array.pictureTitles)
        listAdapter = activity?.let {
            ArrayAdapter(
                it, android.R.layout.simple_list_item_1,
                list
            )
        }
    }


    override fun onListItemClick(
        l: ListView, v: View, position: Int, id:
        Long
    ) {
        super.onListItemClick(l, v, position, id)
        Log.e("INDEX", position.toString())
        /*
        Sends the index to the main activity, which then sends it to ImageViewActivity
         */
        sendData?.sendIndex(position)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sendData = try {
            activity as SendIndex?
        } catch (e: ClassCastException) {
            throw ClassCastException("Error in retrieving data. Please try again")
        }
    }
}
