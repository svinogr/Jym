package info.upump.jym.adapters.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import info.upump.jym.R;
import info.upump.jym.activity.workout.CycleDetailActivity;
import info.upump.jym.entity.Cycle;

/**
 * Created by explo on 22.03.2018.
 */

public class CycleHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView type;
    private Cycle cycle;
    private View itemView;
    private Context context;

    public CycleHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.context = itemView.getContext();
        title = itemView.findViewById(R.id.workout_card_layout_title);
        type = itemView.findViewById(R.id.workout_card_layout_info_type);

    }
    public void bind(Cycle cycle){
        this.cycle = cycle;
        title.setText(cycle.getTitle());
        if(!cycle.isDefaultType()){
            type.setText(context.getResources().getString(R.string.exercise_card_type_exercise));
        } else type.setText("");
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity();
            }
        });

    }

    private void startActivity() {
        Intent intent = CycleDetailActivity.createIntent(context, cycle);
      /*  if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View sharedViewIm = image;
            View sharedViewT = title;
            String transitionNameIm = "exercise_card_layout_image";
            String transitionNameT = "exercise_card_layout_title";
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
                            context,
                    Pair.create(sharedViewIm, transitionNameIm),
                    Pair.create(sharedViewT, transitionNameT));
            context.startActivity(intent, transitionActivityOptions.toBundle());
        } else*/ context.startActivity(intent);

    }
}
