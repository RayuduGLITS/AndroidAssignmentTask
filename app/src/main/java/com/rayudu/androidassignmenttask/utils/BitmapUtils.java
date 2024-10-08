package com.rayudu.androidassignmenttask.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class BitmapUtils {

    // Convert Bitmap to Base64 string
    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // Compress bitmap to a byte array, use Bitmap.CompressFormat.PNG or JPEG
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        // Encode the byte array to a Base64 string
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    // Convert Base64 string back to Bitmap
    public static Bitmap stringToBitmap(String encodedString) {
        try {
            byte[] decodedBytes = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
