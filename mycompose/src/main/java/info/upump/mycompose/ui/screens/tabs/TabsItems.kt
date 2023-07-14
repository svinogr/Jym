package info.upump.mycompose.ui.screens.tabs

import info.upump.mycompose.R

sealed class TabsItems(var title: Int = -1) {
    object TitleCycleTab : TabsItems(
        title = R.string.tab_cycle_title
    )

    object DescriptionCycleTab : TabsItems(
        title = R.string.tab_cycle_description
    )

}
