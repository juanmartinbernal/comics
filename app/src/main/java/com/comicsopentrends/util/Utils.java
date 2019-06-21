package com.comicsopentrends.util;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.comicsopentrends.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Asus on 20/10/2017.
 */

public class Utils {

    /**
     * Método encargado de reemplazar un fragmneto
     *
     * @param fragment
     */
    public static void replaceFragment(Fragment fragment, int container, FragmentManager fragmentManager) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(container, fragment);
        ft.commit();
    }


    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Método encargado de mostrar un dialogo con el previo de una imagen
     * @param activity
     * @param url
     * @param name
     */
    public static void showDialogPreviewImage(FragmentActivity activity, String url, String name) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_photo_profile);

        // set the custom dialog components - text, image and button
        TextView text = dialog.findViewById(R.id.text);
        final ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
        text.setText(name);
        ImageView image = dialog.findViewById(R.id.image);
        progressBar.setVisibility(View.VISIBLE);
        Picasso.get().load(url).into(image, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.GONE);
                image.setImageResource(R.drawable.verse_logo);
            }
        });

        dialog.show();
    }
}
