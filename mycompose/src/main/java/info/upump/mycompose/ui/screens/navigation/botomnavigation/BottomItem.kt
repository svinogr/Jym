package info.upump.mycompose.ui.screens.navigation.botomnavigation

import info.upump.mycompose.R


sealed class BottomItem(val title: Int, val rout: Int, val iconId: Int = -1) {

    object MyStartCycleScreen :BottomItem(
        R.string.title_of_mystartcycle_screen,
        R.string.my_startcycle_rout,
        R.drawable.my_cycle
    ) {

     /*   object MyCycle : BottomItem(
        R.string.title_of_mycycle_screen,
        R.string.my_cycle_rout,
        R.drawable.my_cycle
    )
        object MyWorkout : BottomItem(
            R.string.title_of_mycycle_screen,
            R.string.my_workout_rout,
            R.drawable.my_cycle
        )

        object Exercise : BottomItem(
            R.string.title_of_mycycle_screen,
            R.string.my_exercise_rout,
            R.drawable.my_cycle
        )*/
    }

    object DefaultCycle : BottomItem(
        R.string.title_of_defaultcycle_screen,
        R.string.default_cycle_rout,
        R.drawable.default_cycle
    )

    object Profile :
        BottomItem(R.string.title_of_profile_screen, R.string.profile_rout, R.drawable.profile)
}