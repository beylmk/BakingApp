package udacity.maddie.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RecipeGridFragment extends Fragment {

    private GridView recipeGridView;

    private RecipeAdapter recipeAdapter;

    private OnRecipeClickListener listener;

    public RecipeGridFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeGridFragment.
     */
    public static RecipeGridFragment newInstance() {
        RecipeGridFragment fragment = new RecipeGridFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof OnRecipeClickListener)) {
            Log.e(RecipeGridFragment.class.getSimpleName(), "activity does not implement OnRecipeClickListener");
        } else {
            listener = (OnRecipeClickListener) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecipeListView();
    }

    private void setUpRecipeListView() {

        recipeGridView = getView().findViewById(R.id.recipe_grid_view);
        recipeAdapter = new RecipeAdapter();
        recipeGridView.setAdapter(recipeAdapter);
    }

    public class RecipeAdapter extends BaseAdapter {

        public RecipeAdapter() {
        }

        @Override
        public int getCount() {
            return Recipes.size();
        }

        @Override
        public Object getItem(int i) {
            return Recipes.get(i);
        }

        @Override
        public long getItemId(int i) {
            return Recipes.get(i).getId();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View gridItem;

            Recipe currentRecipe = Recipes.get(position);

            if (convertView == null) {
                gridItem = LayoutInflater.from(getActivity()).inflate(R.layout.recipe_grid_item, parent, false);
            } else {
                gridItem = convertView;
            }

            TextView recipeNameTextView = gridItem.findViewById(R.id.recipe_name_text_view);
            recipeNameTextView.setText(currentRecipe.getName());

            ImageView recipeImageView = gridItem.findViewById(R.id.recipe_image_view);
            if (currentRecipe.getImageUrl() != null) {
                Picasso.with(getActivity()).load(currentRecipe.getImageUrl()).into(recipeImageView);
            }

            gridItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onRecipeClick(position);
                    notifyDataSetChanged();
                }
            });

            return gridItem;
        }
    }
}
