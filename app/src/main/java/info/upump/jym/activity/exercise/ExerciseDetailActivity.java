package info.upump.jym.activity.exercise;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.net.URI;

import info.upump.jym.R;
import info.upump.jym.bd.ExerciseDao;
import info.upump.jym.entity.Exercise;

public class ExerciseDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String ID_EXERCISE = "id";
    private ImageView imageView;
    private WebView description;
    private Exercise exercise;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton fabEdit;
    private FloatingActionButton fabDelete;
    private ExerciseDao exerciseDao;
    public static final int REQUEST_OPEN_OR_CHANGE = 0;
    public static final String ACTION = "action";
    public static final int OPEN_OR_CHANGE = 1;
    public static final int DELETE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.exercise_activity_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = findViewById(R.id.exercise_activity_detail_collapsing);
        fabEdit = findViewById(R.id.exercise_activity_detail_fab_edit);
        fabDelete = findViewById(R.id.exercise_activity_detail_fab_delete);
        fabEdit.setOnClickListener(this);
        fabDelete.setOnClickListener(this);

        imageView = findViewById(R.id.exercise_activity_detail_image_view);
        description = findViewById(R.id.exercise_detail_activity_description);

        //createViewFrom();
    }

    private void createViewFrom() {
        Intent intent = getIntent();
        if (intent != null) {
            exercise = getExerciseFromIntent(intent);
        }

        if (exercise.isDefaultType()) {
            fabEdit.setVisibility(View.GONE);
            fabDelete.setVisibility(View.GONE);
        }

        if (exercise.getDescription().equals("")) {
            exercise.setDescription(getResources().getString(R.string.no_description));
        }
        collapsingToolbarLayout.setTitle(exercise.getTitle());
        setPic();
        description.loadDataWithBaseURL(null, exercise.getDescription(), "text/html", "UTF-8", null);
    }

    public static Intent createIntent(Context context, Exercise exercise) {
        Intent intent = new Intent(context, ExerciseDetailActivity.class);
        intent.putExtra(ID_EXERCISE, exercise.getId());
        return intent;
    }

    private Exercise getExerciseFromIntent(Intent intent) {
        exerciseDao = new ExerciseDao(this);
        long longExtra = intent.getLongExtra(ID_EXERCISE, 0);
        exercise = exerciseDao.getById(longExtra);
        return exercise;
    }

    private void setPic() {
        Uri uri = null;
        if(exercise.getImg()!=null) {
            uri = Uri.parse(exercise.getImg());
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_add_black_24dp)
                .error(R.drawable.ic_add_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(this).load(uri).apply(options).into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                finishAfterTransition();
            } else finish();


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exercise_activity_detail_fab_edit:
              startActivity();
                break;
            case R.id.exercise_activity_detail_fab_delete:
                Snackbar.make(v, "ТОчно удадить", Snackbar.LENGTH_LONG)
                        .setAction("сто пудов", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteItem();
                                finish();
                            }
                        }).show();
        }
    }


    private void startActivity() {
        Intent intent = ExerciseDetailActivityEdit.createIntent(this, exercise);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View sharedViewIm = imageView;
            View sharedViewT = description;
            String transitionNameIm = "exercise_card_layout_image";
            String transitionNameT = "exercise_detail_activity_description";
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
                            this,
                    Pair.create(sharedViewIm, transitionNameIm),  Pair.create(sharedViewT, transitionNameT));
            startActivity(intent, transitionActivityOptions.toBundle());
        } else startActivity(intent);

    }

    private void deleteItem() {
        if (exerciseDao.delete(exercise)) {
            Toast.makeText(this, "времен, упражнение удалено", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "времен, не возможно удалить", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createViewFrom();

    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ExerciseDetailActivityEdit.REQUEST_CODE_FOR_EDIT_EXERCISE) {
                ExerciseDao exerciseDao = new ExerciseDao(this);
                exercise.setTitle(data.getStringExtra(ExerciseDetailActivityEdit.TITLE_EXERCISE));
                exercise.setDescription(data.getStringExtra(ExerciseDetailActivityEdit.DESCRIPTION_EXERCISE));
                if (exerciseDao.update(exercise)) {
                    createViewFrom(exercise);
                }
            }
        }
    }
    */
}
