package com.example.oving_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menu.add("Lars-Håvard")
        menu.add("Holter Bråten")
        Log.d("Leksjon" , "Menus created") /* Vises i LogCat med debug tag */
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.title.equals("Lars-Håvard")) {
            Log.w("Leksjon", item.title.toString()) /* Vises i LogCat med warning tag */
        }
        if(item.title.equals("Holter Bråten")) {
            Log.e("Leksjon", item.title.toString()) /* Vises i LogCat med error tag */
        }
        return true
    }
}