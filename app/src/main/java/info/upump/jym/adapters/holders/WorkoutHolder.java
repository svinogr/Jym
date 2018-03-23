package info.upump.jym.adapters.holders;

import android.net.Uri;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import info.upump.jym.R;
import info.upump.jym.entity.Day;
import info.upump.jym.entity.Workout;

/**
 * Created by explo on 23.03.2018.
 */

public class WorkoutHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ImageView imageView;
    private TextView title, day;
    private View itemView;
    private Workout workout;

    public WorkoutHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        imageView = itemView.findViewById(R.id.workout_card_layout_image);
        title = itemView.findViewById(R.id.workout_card_layout_title);
        day = itemView.findViewById(R.id.workout_card_layout_info_day);
        itemView.setOnClickListener(this);
    }

    public void bind(Workout workout){
        this.workout = workout;
        System.out.println(workout);
        setPic();
        title.setText(workout.getTitle());
        day.setText(workout.getDay().getName());

    }

    @Override
    public void onClick(View v) {

    }
    private void setPic() {
        Day day = workout.getDay();
        RequestOptions options = new RequestOptions()
                .transforms(new RoundedCorners(50))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.HIGH);

        Glide.with(itemView.getContext()).load(itemView.getResources().getDrawable(day.getColor())).apply(options).into(imageView);
    }
}
