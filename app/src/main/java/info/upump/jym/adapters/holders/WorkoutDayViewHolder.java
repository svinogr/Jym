//package info.upump.jym.adapters.holders;
//
//import android.app.Activity;
//import android.app.ActivityOptions;
//import android.content.Intent;
//import android.os.Build;
//import android.util.Pair;
//import android.view.View;
//
//import info.upump.jym.R;
//import info.upump.jym.activity.workout.WorkoutDetailActivity;
//import info.upump.jym.fragments.cycle.CRUD;
//
//
//public class WorkoutDayViewHolder extends AbstractWorkoutViewHolder {
//    protected CRUD crud;
//
//    public WorkoutDayViewHolder(View itemView, CRUD crud) {
//        super(itemView);
//        this.crud = crud;
//    }
//
//    @Override
//    void setVariablyField() {
//        if(workout.isWeekEven()){
//            week.setText(R.string.week_even);
//        } else week.setText("");
//        variably.setText(workout.getDay().getName());
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            View sharedViewIm = imageView;
//            String transitionNameIm = "workout_card_layout_image";
//            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
//                            getAnimationContext(),
//                    Pair.create(sharedViewIm, transitionNameIm));
//            crud.createIntentForResult(transitionActivityOptions,workout);
//        } else   crud.createIntentForResult(null,workout);
//    }
//
//    @Override
//    void startActivity() {
//
//    }
//
//}
