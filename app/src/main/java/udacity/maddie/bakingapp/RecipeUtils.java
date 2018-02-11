package udacity.maddie.bakingapp;

import com.google.gson.JsonParser;

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
import java.util.ArrayList;
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
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            String json = new String(buffer, "UTF-8");

            JSONArray recipesObject = new JSONArray(json);
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

    public static String formatIngredients(ArrayList<RecipeIngredient> ingredientArrayList) {
        String ingredientsText = "";

        for (RecipeIngredient ingredient : ingredientArrayList) {
            ingredientsText += formatIngredientString(ingredient) + "\n";
        }
        return ingredientsText;
    }

    public static String formatIngredientString(RecipeIngredient ingredient) {

        Float quantityValue = ingredient.getQuantity();
        String ingredientQuantity = quantityValue != null ? String.valueOf(quantityValue) : "";

        String ingredientMeasure =
            ingredient.getMeasure() != null ? ingredient.getMeasure().toString().toLowerCase() + " ": "";

        String ingredientName = ingredient.getIngredient() != null ? ingredient.getIngredient() : "";

        return ingredientQuantity + " " + ingredientMeasure + ingredientName;
    }

    public static boolean getIsTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
            & Configuration.SCREENLAYOUT_SIZE_MASK)
            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == context.getResources().getConfiguration().ORIENTATION_LANDSCAPE;
    }
}
