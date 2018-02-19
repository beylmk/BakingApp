package udacity.maddie.bakingapp;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Spinner;

import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;

/**
 * Created by rfl518 on 2/18/18.
 */

public class RecipeWidgetConfigActivity extends Activity {

    private static final String TAG = RecipeWidgetConfigActivity.class.getSimpleName();

    private int mAppWidgetId;

    private Spinner recipesSpinner;

    private Button okButton;

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
        setUpSpinner();

        okButton = findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedRecipeName = recipesSpinner.getSelectedItem().toString();
                int selectedRecipeIndex = RecipeUtils.getRecipeIndexByName(selectedRecipeName);
                showAppWidget(selectedRecipeIndex);
            }
        });
    }

    private void setUpSpinner() {
        recipesSpinner = findViewById(R.id.recipes_spinner);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
        adapter.addAll(RecipeUtils.getSpinnerNameArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recipesSpinner.setAdapter(adapter);
    }

    private void showAppWidget(int recipeIndex) {

        if (mAppWidgetId == INVALID_APPWIDGET_ID) {
            Log.i(TAG, "invalid widget id");
            finish();
        }

        RecipeWidgetProvider.setSelectedRecipeIndex(recipeIndex);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.recipe_widget);
        appWidgetManager.updateAppWidget(mAppWidgetId, views);

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);

        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE, null, this, RecipeWidgetProvider.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{mAppWidgetId});
        sendBroadcast(intent);
        
        finish();

    }
}
