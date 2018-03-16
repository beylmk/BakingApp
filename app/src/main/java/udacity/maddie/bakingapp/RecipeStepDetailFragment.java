package udacity.maddie.bakingapp;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static android.text.TextUtils.isEmpty;
import static com.google.android.exoplayer2.Player.STATE_READY;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link RecipeStepDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeStepDetailFragment extends Fragment {

    private static final String TAG = RecipeStepDetailFragment.class.getSimpleName();

    private static final String EXO_PLAYER_POSITION = "exoPlayerPosition";

    private static final String EXO_PLAYER_CURRENT_STATE = "exoPlayerState";

    private static RecipeStep step;

    private static Recipe recipe;

    private ImageView stepImageView;

    private static OnRecipeStepClickListener navigationClickListener;

    private TextView longDescriptionTextView;

    private SimpleExoPlayerView exoPlayerView;

    private SimpleExoPlayer exoPlayer;

    private static int currentExoPlayerState;

    private View navigationContainer;

    private static Long exoPlayerPosition = C.TIME_UNSET;

    public RecipeStepDetailFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeGridFragment.
     */
    public static RecipeStepDetailFragment newInstance(RecipeStep inRecipeStep, Recipe inRecipe, OnRecipeStepClickListener listener) {
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        Log.e("RSDFragment", "RecipeStepDetailFragment's newInstance");
        step = inRecipeStep;
        recipe = inRecipe;
        navigationClickListener = listener;
        exoPlayerPosition = C.TIME_UNSET;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e("RSDFragment", "RecipeStepDetailFragment's onCreate with " + (savedInstanceState == null ? "null" : "non-null")
            + " savedInstanceState");
        if (savedInstanceState != null && savedInstanceState.containsKey(EXO_PLAYER_POSITION)) {
            exoPlayerPosition = (long) savedInstanceState.get(EXO_PLAYER_POSITION);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(EXO_PLAYER_CURRENT_STATE)) {
            currentExoPlayerState = (int) savedInstanceState.get(EXO_PLAYER_CURRENT_STATE);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!(RecipeUtils.isLandscape(getActivity()) && !isEmpty(step.getVideoURL()))) {
            //show text unless is landscape and has video
            longDescriptionTextView = view.findViewById(R.id.long_description_text_view);
            longDescriptionTextView.setVisibility(View.VISIBLE);
            longDescriptionTextView.setText(step.getDescription());
        }

        if (!RecipeUtils.getIsTablet(getActivity())) {
            navigationContainer = view.findViewById(R.id.navigation_container);
            getActivity().setTitle(recipe.getName() + " " + getString(R.string.details));
            setUpNavigation(view);
            if (!RecipeUtils.isLandscape(getActivity())) {
                setUpImage(view);
            }
        }

        Log.e("RSDFragment", "RecipeStepDetailFragment's onViewCreated with " + (savedInstanceState == null ? "null" : "non-null")
            + " savedInstanceState");
        setUpExoPlayer(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            navigationClickListener = (OnRecipeStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                + " must implement TextClicked");
        }
    }

    private void setUpImage(View view) {
        stepImageView = view.findViewById(R.id.recipe_step_detail_image_view);
        if (step.getThumbnailUrl() != null) {
            Picasso.with(getActivity()).load(step.getThumbnailUrl()).into(stepImageView);
            stepImageView.setVisibility(View.VISIBLE);
        } else {
            stepImageView.setVisibility(View.GONE);
        }
    }

    private void setUpExoPlayer(View view) {
        exoPlayerView = view.findViewById(R.id.video_player);
        if (!isEmpty(step.getVideoURL())) {
            initializePlayer(Uri.parse(step.getVideoURL()));
        } else {
            exoPlayerView.setVisibility(View.GONE);
        }
    }

    private void setUpNavigation(View view) {
        navigationContainer.setVisibility(View.VISIBLE);

        ImageButton backwardButton = view.findViewById(R.id.navigate_back_button);
        ImageButton forwardButton = view.findViewById(R.id.navigate_forward_button);

        if (step.getId() == 0) {
            backwardButton.setVisibility(View.GONE);
        }
        if (step.getId() == recipe.getSteps().size() - 1) {
            forwardButton.setVisibility(View.GONE);
        }

        backwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(step.getId() == 0))  {
                    //if not the first step, allow navigation backwards
                    navigationClickListener.onRecipeStepClick(step.getId() - 1);
                }
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(step.getId() == recipe.getSteps().size() - 1)) {
                    //if not the last step, allow navigation forwards
                    navigationClickListener.onRecipeStepClick(step.getId() + 1);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!isEmpty(step.getVideoURL())) {
            exoPlayerPosition = exoPlayer.getCurrentPosition();
            outState.putLong(EXO_PLAYER_POSITION, exoPlayerPosition);
            currentExoPlayerState = exoPlayer.getPlaybackState();
            outState.putInt(EXO_PLAYER_CURRENT_STATE, currentExoPlayerState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        super.onDestroy();
    }

    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            exoPlayerView.setPlayer(exoPlayer);


            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getActivity(), userAgent), new
                DefaultExtractorsFactory(), null, null);

            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);

        }

        if (exoPlayerPosition != C.TIME_UNSET) {
            exoPlayer.seekTo(exoPlayerPosition);
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

}
