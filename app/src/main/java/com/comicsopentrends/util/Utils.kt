package com.comicsopentrends.util

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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
     * @param context
     * @param url
     * @param name
     */
    fun showDialogPreviewImage(context: Context, url: String?, name: String?) {
        val dialog = Dialog(context)
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
