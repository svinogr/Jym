package info.upump.jym.adapters.holders;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import info.upump.jym.R;
import info.upump.jym.activity.exercise.ExerciseDetailTemplateActivity;
import info.upump.jym.entity.Exercise;


public abstract class AbstractExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected List<Exercise> exerciseList;
    protected Context context;
    protected ImageView image;
    protected TextView title, type, setInfo;


    protected Exercise exercise;

    public AbstractExerciseViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        image = itemView.findViewById(R.id.exercise_card_layout_image);
        title = itemView.findViewById(R.id.exercise_card_layout_title);
        type = itemView.findViewById(R.id.exercise_card_layout_info_type);
        setInfo = itemView.findViewById(R.id.exercise_card_layout_info_sets);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity();
    }

//    abstract public Intent createIntent();

    public void bind(Exercise exercise) {
        this.exercise = exercise;
        setPic();
        title.setText(exercise.getExerciseDescription().getTitle());
        showExercise();
        setInfo();
    }

    protected void showExercise() {
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ExerciseDetailTemplateActivity.createIntent(context, exercise);
                context.startActivity(intent);
            }
        });
    }
    abstract public void setInfo();

    abstract void startActivity() ;
   /*     Intent intent = createIntent();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View sharedViewIm = image;
            String transitionNameIm = "exercise_activity_create_image";
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
                            getAnimationContext(),
                    Pair.create(sharedViewIm, transitionNameIm));
            context.startActivity(intent, transitionActivityOptions.toBundle());
        } else context.startActivity(intent);*/
//    }

    private void setPic() {

        RequestOptions options = new RequestOptions()
                .transforms(new RoundedCorners(50))
                .centerCrop()
//                .error(R.color.colorTextLabel)
                .error(R.drawable.iview_place_erore_exercise_50)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        int ident=0;
        if (exercise.getExerciseDescription().getDefaultImg() != null) {
            ident = itemView.getContext().getApplicationContext().getResources().getIdentifier(exercise.getExerciseDescription().getDefaultImg(), "drawable", itemView.getContext().getPackageName());
        }

        if(ident !=0){
            Glide.with(itemView.getContext()).load(ident).apply(options).into(image);
        } else {
            Glide.with(itemView.getContext()).load(Uri.parse(exercise.getExerciseDescription().getImg())).apply(options).into(image);

        }
    }

    protected Context getAnimationContext() {

        if (context instanceof Activity) {
            context = (Activity) context;
        } else context = ((ContextThemeWrapper) context).getBaseContext();
        return context;
    }
}
