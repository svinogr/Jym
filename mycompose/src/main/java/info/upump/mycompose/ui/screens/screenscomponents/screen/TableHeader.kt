package info.upump.mycompose.ui.screens.screenscomponents.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import info.upump.mycompose.R
import info.upump.mycompose.ui.theme.MyTextLabel16
import info.upump.mycompose.ui.theme.MyTextTitleLabel16



enum class GuidelineSets(val offset: Float){
    ONE(0.05f), TWO(0.25f), THREE(0.45f), FOUR(0.75f)

}
@Composable
fun TableHeader(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ConstraintLayout(modifier = modifier) {
            val textNumber = createRef()
            val textWeight = createRef()
            val textPasWeight = createRef()
            val textReps = createRef()
            val guiOne = createGuidelineFromStart(GuidelineSets.ONE.offset)
            val guiTwo = createGuidelineFromStart(GuidelineSets.TWO.offset)
            val guiThree = createGuidelineFromStart(GuidelineSets.THREE.offset)
            val guiFour = createGuidelineFromStart(GuidelineSets.FOUR.offset)
            val modifierOneThree = Modifier.padding(start = 2.dp)
            Text(
                text = "№",
                modifier = modifierOneThree.constrainAs(textNumber) {
                    start.linkTo(guiOne)
                }, style = MyTextTitleLabel16
            )

            Text(
                text = stringResource(id = R.string.label_weight_set),
                modifier = modifierOneThree.constrainAs(textWeight) {
                    start.linkTo(guiTwo)
                }, style = MyTextTitleLabel16
            )

            Text(
                text = stringResource(id = R.string.label_weight_set_past),
                modifier = modifierOneThree.constrainAs(textPasWeight) {
                    start.linkTo(guiThree)
                }, style = MyTextLabel16
            )

            Text(
                text = stringResource(id = R.string.label_reps_sets_short),
                modifier = modifierOneThree.constrainAs(textReps) {
                    start.linkTo(guiFour)
                }, style = MyTextTitleLabel16
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TableHeaderPreview() {
    TableHeader()
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun TableHeaderPreview2() {
    TableHeader(modifier = Modifier.background(colorResource(id = R.color.colorBackgroundChips)))
}