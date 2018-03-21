package info.upump.jym.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

import info.upump.jym.MainActivity;
import info.upump.jym.activity.exercise.ExerciseDetailActivity;
import info.upump.jym.R;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.Sets;

/**
 * Created by explo on 14.03.2018.
 */

public class ExerciseViewHolder extends RecyclerView.ViewHolder {
    private ImageView image;
    private TextView title;
    private TextView setInfo, type;
    private Context context;
    private View mItemView;
    private Exercise exercise;


    public ExerciseViewHolder(View itemView) {
        super(itemView);
        this.mItemView = itemView;
        context = itemView.getContext();
        image = itemView.findViewById(R.id.exercise_card_layout_image);
        title = itemView.findViewById(R.id.exercise_card_layout_title);
        type = itemView.findViewById(R.id.exercise_card_layout_info_type);
        setInfo = itemView.findViewById(R.id.exercise_card_layout_info_sets);
        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity();
            }
        });
    }

    private void startActivity() {
        Intent intent = ExerciseDetailActivity.createIntent(context, exercise);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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

    public void bind(Exercise exercise) {
        this.exercise = exercise;
        setPic();
        title.setText(exercise.getTitle());

        if (exercise.getSetsList() == null && exercise.getSetsList().size() > 0) {

            setInfo.setText(createInfo(exercise.getSetsList()));
        }
        if (!exercise.isDefaultType()) {
            type.setText(context.getResources().getString(R.string.exercise_card_type_exrcise));
        } else type.setText("");
    }

    private int createInfo(List<Sets> setsList) {
        return 0;
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
