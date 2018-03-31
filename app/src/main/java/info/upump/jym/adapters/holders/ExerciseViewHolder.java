package info.upump.jym.adapters.holders;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
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
import info.upump.jym.entity.Exercise;


public abstract class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected List<Exercise> exerciseList;
    protected Context context;
    protected ImageView image;
    protected TextView title, type, setInfo;


    protected Exercise exercise;

    public ExerciseViewHolder(View itemView) {
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

    abstract public Intent createIntent();

    public void bind(Exercise exercise) {
        this.exercise = exercise;
        setPic();
        title.setText(exercise.getTitle());
        setInfo();
        if (!exercise.isDefaultType()) {
            type.setText(context.getResources().getString(R.string.card_type_item_exercise));
        } else type.setText("");
    }

    abstract public void setInfo();

    private void startActivity() {
        Intent intent = createIntent();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View sharedViewIm = image;
            View sharedViewT = title;
            String transitionNameIm = "exercise_card_layout_image";
            String transitionNameT = "exercise_card_layout_title";
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
                            context,
                    Pair.create(sharedViewIm, transitionNameIm),
                    Pair.create(sharedViewT, transitionNameT));
            context.startActivity(intent, transitionActivityOptions.toBundle());
        } else context.startActivity(intent);
    }

    private void setPic() {
        Uri uri = null;
        if (exercise.getImg() != null) {
            uri = Uri.parse(exercise.getImg());
        }
        RequestOptions options = new RequestOptions()
                .transforms(new RoundedCorners(50))
                .centerCrop()
                .placeholder(R.drawable.ic_add_black_24dp)
                .error(R.drawable.ic_add_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(itemView.getContext()).load(uri).apply(options).into(image);
    }
}
