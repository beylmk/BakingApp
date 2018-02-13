package udacity.maddie.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RecipeGridFragment extends Fragment {

    private RecyclerView recipeRecyclerView;

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
        setUpRecipeRecyclerView();
    }

    private void setUpRecipeRecyclerView() {

        recipeRecyclerView = getView().findViewById(R.id.recipe_recycler_view);
        int numberOfColumns = RecipeUtils.calculateNoOfColumns(getContext());
        recipeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        recipeAdapter = new RecipeAdapter(getActivity());
        recipeRecyclerView.setAdapter(recipeAdapter);
    }

    public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

        private LayoutInflater mInflater;

        public RecipeAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.recipe_grid_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecipeAdapter.ViewHolder holder, final int position) {
            Recipe recipe = Recipes.get(position);
            holder.recipeNameTextView.setText(recipe.getName());
            if (recipe.getImageUrl() != null) {
                Picasso.with(getActivity()).load(recipe.getImageUrl()).into(holder.recipeImageView);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onRecipeClick(position);
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public long getItemId(int i) {
            return Recipes.get(i).getId();
        }

        @Override
        public int getItemCount() {
            return Recipes.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView recipeImageView;
            TextView recipeNameTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                recipeImageView = itemView.findViewById(R.id.recipe_image_view);
                recipeNameTextView = itemView.findViewById(R.id.recipe_name_text_view);
            }
        }
    }
}
