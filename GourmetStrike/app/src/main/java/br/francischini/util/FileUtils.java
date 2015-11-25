package br.francischini.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by gabriel on 11/24/15.
 */
public class FileUtils {
    public static String loadJSONFromAsset(String filePath, Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filePath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
