package info.upump.jym.adapters.holders;

import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Build;
import android.util.Pair;
import android.view.View;

import info.upump.jym.fragments.cycle.CRUD;

/*
* for my cycles
* */
public class CycleViewHolder extends CycleDefaultViewHolder {
    private CRUD crud;

    public CycleViewHolder(View itemView, CRUD crud) {
        super(itemView);
        this.crud = crud;
        System.out.println("12");
    }

    @Override
    void setVariableViews() {
        date.setText(cycle.getStartStringFormatDate());
    }

    @Override
    void startActivity() {
        System.out.printf(crud.toString());
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View sharedViewIm = imageView;
            String transitionNameIm = "cycle_card_layout_image";
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
                            context,
                    Pair.create(sharedViewIm, transitionNameIm));
            crud.createIntentForResult(transitionActivityOptions, cycle);
        } else crud.createIntentForResult(null, cycle);

    }
}
