package info.upump.jym.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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


    public ExerciseViewHolder(final View itemView) {
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
                Intent intent = ExerciseDetailActivity.createIntent(context, exercise);
                ((MainActivity)context).startActivityForResult(intent, ExerciseDetailActivity.REQUEST_OPEN_OR_CHANGE);
            }
        });
    }

    public void bind(Exercise exercise) {
        this.exercise = exercise;
        Picasso.with(itemView.getContext().getApplicationContext())
                .load(R.drawable.ic_menu_camera)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.drawable.ic_add_black_24dp)
                .into(image);

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

}
