package udacity.maddie.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Random;

/**
 * Created by rfl518 on 1/31/18.
 */

public class RecipeWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            String number = String.format("%03d", (new Random().nextInt(900) + 100));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.recipe_widget);
            remoteViews.setTextViewText(R.id.recipe_name_text_view_widget, number);

            Intent intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailActivity.SELECTED_RECIPE_INDEX_KEY, 0);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                0, intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.recipe_go_button_widget, pendingIntent);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
