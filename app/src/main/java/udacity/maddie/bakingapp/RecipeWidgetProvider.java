package udacity.maddie.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import java.util.Random;

/**
 * Created by rfl518 on 1/31/18.
 */

public class RecipeWidgetProvider extends AppWidgetProvider {

    private static int selectedRecipeIndex = 0;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final int count = appWidgetIds.length;
        boolean recipesLoaded = false;

        if (Recipes.getRecipes() != null) {
            recipesLoaded = true;
        }

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

            if (recipesLoaded) {

                Recipe recipe = Recipes.get(selectedRecipeIndex);

                remoteViews.setTextViewText(R.id.recipe_name_text_view_widget, recipe.getName());
                remoteViews
                    .setTextViewText(R.id.recipe_ingredients_text_view_widget, RecipeUtils.formatIngredients(recipe.getIngredients()));

                //set widget on click to go to recipe details
                Intent intent = new Intent(context, RecipeDetailActivity.class);
                intent.putExtra(RecipeDetailActivity.SELECTED_RECIPE_INDEX_KEY, selectedRecipeIndex);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                remoteViews.setOnClickPendingIntent(R.id.recipe_widget_container, pendingIntent);
            } else {
                remoteViews.setTextViewText(R.id.recipe_name_text_view_widget, "Open app to load recipes");
            }

            appWidgetManager.updateAppWidget(widgetId, remoteViews);

        }
    }

}
