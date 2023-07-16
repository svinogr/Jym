package info.upump.mycompose.ui.theme.customtheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color


class CustomColors(
    colorPrimary: Color,
    colorPrimaryNet: Color,
    colorPrimaryDark: Color,
    colorPrimaryDarkNet: Color,
    colorAccentNet: Color,
    colorTextLabel: Color,
    colorText: Color,
    colorBackgroundRecyclerView: Color,
    colorBackgroundCardView: Color,
    colorBackgroundTabLayout: Color,
    colorTextTab: Color,
    colorPastSet: Color,
    colorBackgroundConstrateLayout: Color,
    colorBackgroundAppBar: Color,
    colorTestExercise: Color,
    isLight: Boolean,
) {
    var colorPrimary by mutableStateOf(colorPrimary)
        private set
    var colorPrimaryNet by mutableStateOf(colorPrimaryNet)
        private set
    var colorPrimaryDark by mutableStateOf(colorPrimaryDark)
        private set
    var colorPrimaryDarkNet by mutableStateOf(colorPrimaryDarkNet)
        private set
    var colorAccentNet by mutableStateOf(colorAccentNet)
        private set
    var colorTextLabel by mutableStateOf(colorTextLabel)
        private set
    var colorText by mutableStateOf(colorText)
        private set
    var colorBackgroundRecyclerView by mutableStateOf(colorBackgroundRecyclerView)
        private set
    var colorBackgroundCardView by mutableStateOf(colorBackgroundCardView)
        private set
    var colorBackgroundTabLayout by mutableStateOf(colorBackgroundTabLayout)
        private set
    var colorTextTab by mutableStateOf(colorTextTab)
        private set
    var colorPastSet by mutableStateOf(colorPastSet)
        private set
    var colorBackgroundConstrateLayout by mutableStateOf(colorBackgroundConstrateLayout)
        private set
    var colorBackgroundAppBar by mutableStateOf(colorBackgroundAppBar)
        private set
    var colorTestExercise by mutableStateOf(colorTestExercise)
        private set
    var isLight by mutableStateOf(isLight)
        private set

    fun copy(
        colorPrimary: Color = this.colorPrimary,
        colorPrimaryNet: Color = this.colorPrimaryNet,
        colorPrimaryDark: Color = this.colorPrimaryDark,
        colorPrimaryDarkNet: Color = this.colorPrimaryDarkNet,
        colorAccentNet: Color = this.colorAccentNet,
        colorTextLabel: Color = this.colorTextLabel,
        colorText: Color = this.colorText,
        colorBackgroundRecyclerView: Color = this.colorBackgroundRecyclerView,
        colorBackgroundCardView: Color = this.colorBackgroundCardView,
        colorBackgroundTabLayout: Color = this.colorBackgroundTabLayout,
        colorTextTab: Color = this.colorTextTab,
        colorPastSet: Color = this.colorPastSet,
        colorBackgroundConstrateLayout: Color = this.colorBackgroundConstrateLayout,
        colorBackgroundAppBar: Color = this.colorBackgroundAppBar,
        colorTestExercise: Color = this.colorTestExercise,
        isLight: Boolean = this.isLight
    ) = CustomColors(
        colorPrimary = colorPrimary,
        colorPrimaryNet = colorPrimaryNet,
        colorPrimaryDark = colorPrimaryDark,
        colorPrimaryDarkNet = colorPrimaryDarkNet,
        colorAccentNet = colorAccentNet,
        colorTextLabel = colorTextLabel,
        colorText = colorText,
        colorBackgroundRecyclerView = colorBackgroundRecyclerView,
        colorBackgroundCardView = colorBackgroundCardView,
        colorBackgroundTabLayout = colorBackgroundTabLayout,
        colorTextTab = colorTextTab,
        colorPastSet = colorPastSet,
        colorBackgroundConstrateLayout = colorBackgroundConstrateLayout,
        colorBackgroundAppBar = colorBackgroundAppBar,
        colorTestExercise = colorTestExercise,
        isLight = isLight
    )

    fun updateColorsFrom(other: CustomColors) {
        colorPrimary = other.colorPrimary
        colorPrimaryNet = other.colorPrimaryNet
        colorPrimaryDark = other.colorPrimaryDark
        colorPrimaryDarkNet = other.colorPrimaryDarkNet
        colorAccentNet = other.colorAccentNet
        colorTextLabel = other.colorTextLabel
        colorText = other.colorText
        colorBackgroundRecyclerView = other.colorBackgroundRecyclerView
        colorBackgroundCardView = other.colorBackgroundCardView
        colorBackgroundTabLayout = other.colorBackgroundTabLayout
        colorTextTab = other.colorTextTab
        colorPastSet = other.colorPastSet
        colorBackgroundConstrateLayout = other.colorBackgroundConstrateLayout
        colorBackgroundAppBar = other.colorBackgroundAppBar
        colorTestExercise = other.colorTestExercise
        isLight = other.isLight
    }


}