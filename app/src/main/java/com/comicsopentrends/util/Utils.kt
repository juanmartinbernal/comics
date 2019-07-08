package com.comicsopentrends.util

import android.app.Dialog
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.comicsopentrends.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

/**
 * Created by Asus on 20/10/2017.
 */

object Utils {

    /**
     * Método encargado de reemplazar un fragmneto
     *
     * @param fragment
     */
    fun replaceFragment(fragment: Fragment, container: Int, fragmentManager: FragmentManager) {
        val ft = fragmentManager.beginTransaction()
        ft.replace(container, fragment)
        ft.commit()
    }



    /**
     * Método encargado de mostrar un dialogo con el previo de una imagen
     * @param activity
     * @param url
     * @param name
     */
    fun showDialogPreviewImage(activity: FragmentActivity?, url: String?, name: String?) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_photo_profile)

        // set the custom dialog components - text, image and button
        val text = dialog.findViewById<TextView>(R.id.text)
        val progressBar = dialog.findViewById<ProgressBar>(R.id.progressBar)
        text.text = name
        val image = dialog.findViewById<ImageView>(R.id.image)
        progressBar.visibility = View.VISIBLE
        Picasso.get().load(url).into(image, object : Callback {
            override fun onSuccess() {
                progressBar.visibility = View.GONE
            }

            override fun onError(e: Exception) {
                progressBar.visibility = View.GONE
                image.setImageResource(R.drawable.verse_logo)
            }
        })

        dialog.show()
    }
}
