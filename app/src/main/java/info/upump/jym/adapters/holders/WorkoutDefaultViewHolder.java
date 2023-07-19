//package info.upump.jym.adapters.holders;
//
//import android.app.Activity;
//import android.app.ActivityOptions;
//import android.content.Intent;
//import android.os.Build;
//import android.util.Pair;
//import android.view.View;
//
//import info.upump.jym.activity.workout.WorkoutDetailDefaultActivity;
//
//
//public class WorkoutDefaultViewHolder extends AbstractWorkoutViewHolder {
//    public WorkoutDefaultViewHolder(View itemView) {
//        super(itemView);
//    }
//
// /*   @Override
//    public void onClick(View v) {
//        Intent intent = WorkoutDetailDefaultActivity.createIntent(context,workout);
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            View sharedViewIm = imageView;
//            String transitionNameIm = "workout_card_layout_image";
//            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
//                            getAnimationContext(),
//                    Pair.create(sharedViewIm, transitionNameIm));
//            context.startActivity(intent, transitionActivityOptions.toBundle());
//        } else context.startActivity(intent);
//
//    }*/
//
//    @Override
//    void startActivity() {
//        Intent intent = WorkoutDetailDefaultActivity.createIntent(context,workout);
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            View sharedViewIm = imageView;
//            String transitionNameIm = "workout_card_layout_image";
//            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
//                            getAnimationContext(),
//                    Pair.create(sharedViewIm, transitionNameIm));
//            context.startActivity(intent, transitionActivityOptions.toBundle());
//        } else context.startActivity(intent);
//    }
//
//    @Override
//    public void setVariablyField() {
//    }
//}
