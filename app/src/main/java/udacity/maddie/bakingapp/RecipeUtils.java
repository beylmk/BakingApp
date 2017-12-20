package udacity.maddie.bakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Created by rfl518 on 10/13/17.
 */

public class RecipeUtils {

    public static final String TAG = RecipeUtils.class.getSimpleName();

    public static final String RECIPE_FILE_NAME = "recipes.json";

    public static JSONArray getRecipesArray(Context context) {
        InputStream inputStream = null;

        try {
            inputStream = context.getAssets().open(RECIPE_FILE_NAME);
            byte[] buffer = new byte[inputStream.available()];
            JSONArray recipesObject = new JSONArray(context.getString(R.string.recipes_json));
            return recipesObject;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public static boolean getIsTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
            & Configuration.SCREENLAYOUT_SIZE_MASK)
            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
