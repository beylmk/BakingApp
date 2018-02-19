package udacity.maddie.bakingapp;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;

/**
 * Created by rfl518 on 2/18/18.
 */

public class RecipeWidgetConfigActivity extends Activity {

    private static final String EXTRA_APPWIDGET_ID = "appWidgetId";

    private static final String TAG = RecipeWidgetConfigActivity.class.getSimpleName();

    int mAppWidgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_config_activity);

        Intent intent = getIntent();
        mAppWidgetId = intent.getExtras().getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID);

        init();
    }

    public void init() {
        setTitle(getString(R.string.choose_widget_recipe));
//        showAppWidget();
    }

    private void showAppWidget() {

        if (mAppWidgetId == INVALID_APPWIDGET_ID) {
            Log.i(TAG, "invalid widget id");
            finish();
        }

        AppWidgetProviderInfo providerInfo = AppWidgetManager.getInstance(
            getBaseContext()).getAppWidgetInfo(mAppWidgetId);

        Intent startService = new Intent(RecipeWidgetConfigActivity.this,
            RecipeWidgetProvider.class);
        startService.putExtra(EXTRA_APPWIDGET_ID, mAppWidgetId);
        startService.setAction("FROM CONFIGURATION ACTIVITY");
        setResult(RESULT_OK, startService);
        startService(startService);

        finish();

    }
}
