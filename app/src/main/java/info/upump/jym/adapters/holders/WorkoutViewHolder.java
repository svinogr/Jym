package info.upump.jym.adapters.holders;

import android.content.Context;
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

abstract public   class WorkoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected ImageView imageView;
    protected TextView title, variably;
    protected View itemView;
    protected Workout workout;
    protected Context context;

    public WorkoutViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        imageView = itemView.findViewById(R.id.workout_card_layout_image);
        title = itemView.findViewById(R.id.workout_card_layout_title);
        variably = itemView.findViewById(R.id.workout_card_layout_info_variably);
        itemView.setOnClickListener(this);
        context = itemView.getContext();
    }

    public void bind(Workout workout) {
        this.workout = workout;
        System.out.println(workout);
        setPic();
        title.setText(workout.getTitle());
        setVariablyField();
    }

    abstract void setVariablyField();

    @Override
    abstract public void onClick(View v);

    protected void setPic() {
        Day day = workout.getDay();
        RequestOptions options = new RequestOptions()
                .transforms(new RoundedCorners(50))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.HIGH);

        Glide.with(itemView.getContext()).load(itemView.getResources().getDrawable(day.getColor())).apply(options).into(imageView);
    }
}