//package info.upump.jym.adapters.holders;
//
//import android.app.Activity;
//import android.app.ActivityOptions;
//import android.content.Intent;
//import android.os.Build;
//import android.util.Pair;
//import android.view.View;
//
//import info.upump.jym.activity.workout.WorkoutDetailActivity;
//import info.upump.jym.fragments.cycle.CRUD;
//
///*
//* fot my workout in workout fragment
//*/
//
//public class WorkoutTemplateViewHolder extends AbstractWorkoutViewHolder {
//    protected CRUD crud;
//
//    public WorkoutTemplateViewHolder(View itemView, CRUD crud) {
//        super(itemView);
//        this.crud = crud;
//    }
//
//    @Override
//    public void setVariablyField() {
//    }
//
//    @Override
//    public void onClick(View v) {
//        //Intent intent = WorkoutDetailActivity.createIntent(context,workout);
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            View sharedViewIm = imageView;
//            String transitionNameIm = "workout_card_layout_image";
//            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
//                            getAnimationContext(),
//                    Pair.create(sharedViewIm, transitionNameIm));
//          //  context.startActivity(intent, transitionActivityOptions.toBundle());
//            crud.createIntentForResult(transitionActivityOptions, workout);
//        } else crud.createIntentForResult(null, workout);
//
//    }
//
//    @Override
//    void startActivity() {
//
//    }
//}
