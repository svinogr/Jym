//package info.upump.jym.adapters.holders;
//
//import android.app.Activity;
//import android.app.ActivityOptions;
//import android.content.Intent;
//import android.os.Build;
//import android.util.Pair;
//import android.view.View;
//
//import info.upump.jym.activity.exercise.ExerciseDetail;
//import info.upump.jym.activity.exercise.ExerciseDetailDefaultActivity;
//import info.upump.jym.fragments.cycle.CRUD;
//
///*
//*  holder for exercise in defult cycle  template exercise activity
//*/
//
//public class ExerciseWithInfoDefaultViewHolder extends ExerciseWithInfoViewHolder {
//    public ExerciseWithInfoDefaultViewHolder(View itemView, CRUD crud) {
//        super(itemView, crud);
//    }
//   /* @Override
//    public Intent createIntent() {
//        Intent intent = ExerciseDetailDefaultActivity.createIntent(context, exercise);
//        return intent;
//    }*/
//
//    @Override
//    void startActivity() {
//        Intent intent = ExerciseDetailDefaultActivity.createIntent(context, exercise);
//
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            View sharedViewIm = image;
//            String transitionNameIm = "exercise_activity_create_image";
//            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
//                            getAnimationContext(),
//                    Pair.create(sharedViewIm, transitionNameIm));
//            context.startActivity(intent, transitionActivityOptions.toBundle());
//        } else context.startActivity(intent);
//    }
//}
