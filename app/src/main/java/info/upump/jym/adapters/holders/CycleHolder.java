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

import info.upump.jym.R;
import info.upump.jym.activity.workout.CycleDetailActivity;
import info.upump.jym.entity.Cycle;

/**
 * Created by explo on 22.03.2018.
 */

public class CycleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView title;
    private TextView date;
    private ImageView imageView;
    private Cycle cycle;
    private View itemView;
    private Context context;

    public CycleHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.context = itemView.getContext();
        title = itemView.findViewById(R.id.cycle_card_layout_title);
        date = itemView.findViewById(R.id.cycle_card_layout_info_date);
        imageView = itemView.findViewById(R.id.cycle_card_layout_image);
    }

    public void bind(Cycle cycle) {
        this.cycle = cycle;
        title.setText(cycle.getTitle());
        date.setText(cycle.getStartStringFormatDate());
        setPic();
        itemView.setOnClickListener(this);

    }

    private void startActivity() {
        Intent intent = CycleDetailActivity.createIntent(context, cycle);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View sharedViewIm = imageView;
           // View sharedViewT = title;
            String transitionNameIm = "cycle_card_layout_image";
         //   String transitionNameT = "exercise_card_layout_title";
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
                            context,
                    Pair.create(sharedViewIm, transitionNameIm));
            context.startActivity(intent, transitionActivityOptions.toBundle());
        } else
        context.startActivity(intent);

    }
    private void setPic() {
        Uri uri = null;
        if (cycle.getImage() != null) {
            uri = Uri.parse(cycle.getImage());
        }
        RequestOptions options = new RequestOptions()
                .transforms(new RoundedCorners(50))
                .centerCrop()
                .placeholder(R.drawable.ic_add_black_24dp)
                .error(R.drawable.ic_add_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(itemView.getContext()).load(uri).apply(options).into(imageView);
    }


    @Override
    public void onClick(View v) {
        startActivity();
    }
}
