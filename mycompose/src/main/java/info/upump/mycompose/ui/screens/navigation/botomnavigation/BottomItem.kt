package info.upump.mycompose.ui.screens.navigation.botomnavigation

import info.upump.mycompose.R


sealed class BottomItem(val title: Int, val rout: Int, val iconId: Int) {
    object MyWorkout : BottomItem(
        R.string.title_of_myworkouts_screen,
        R.string.my_workouts_rout,
        R.drawable.my_workouts
    )

    object DefaultWorkouts : BottomItem(
        R.string.title_of_defaultworkouts_screen,
        R.string.default_workouts_rout,
        R.drawable.default_workouts
    )

    object Profile :
        BottomItem(R.string.title_of_profile_screen, R.string.profile_rout, R.drawable.profile)
}