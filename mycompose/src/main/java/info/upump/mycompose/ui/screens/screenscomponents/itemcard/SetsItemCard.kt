package info.upump.mycompose.ui.screens.screenscomponents.itemcard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Sets
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.screens.screenscomponents.screen.GuidelineSets
import info.upump.mycompose.ui.theme.MyTextLabel16
import info.upump.mycompose.ui.theme.MyTextTitleLabel16

@Composable
fun SetsItemCard(
    sets: Sets,
    navHost: NavHostController,
    number: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navHost.navigate(NavigationItem.EditSetsNavigationItem.routeWithId(sets.id))
            },
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),

        )
    {
        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
                .padding()
                .background(
                    colorResource(id = R.color.colorBackgroundCardView)
                )
                .padding(top = 16.dp, bottom = 16.dp)
        ) {
            val textNumber = createRef()
            val textWeight = createRef()
            val textPasWeight = createRef()
            val textReps = createRef()
            val guiOne = createGuidelineFromStart(GuidelineSets.ONE.offset)
            val guiTwo = createGuidelineFromStart(GuidelineSets.TWO.offset)
            val guiThree = createGuidelineFromStart(GuidelineSets.THREE.offset)
            val guiFour = createGuidelineFromStart(GuidelineSets.FOUR.offset)
            val modifierOneThree = Modifier.padding(start = 0.dp)
            Text(
                text = "${number + 1}",
                modifier = modifierOneThree.constrainAs(textNumber) {
                    start.linkTo(guiOne)
                }, style = MyTextTitleLabel16
            )
            Text(
                text = sets.weight.toString(),
                modifier = modifierOneThree.constrainAs(textWeight) {
                    start.linkTo(guiTwo)
                }, style = MyTextTitleLabel16
            )
            Text(
                text = sets.weightPast.toString(),
                modifier = modifierOneThree.constrainAs(textPasWeight) {
                    start.linkTo(guiThree)
                }, style = MyTextLabel16
            )
            Text(
                text = sets.reps.toString(),
                modifier = modifierOneThree.constrainAs(textReps) {
                    start.linkTo(guiFour)
                }, style = MyTextTitleLabel16
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewSetItemCard() {
    val set = Sets(weight = 20.0, reps = 5, weightPast = 18.0)
    set.id = 1

    SetsItemCard(sets = set, navHost = NavHostController(LocalContext.current), number = 1)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSetItemCard2() {
    val set = Sets(weight = 20.0, reps = 5, weightPast = 18.0)
    set.id = 1

    SetsItemCard(sets = set, navHost = NavHostController(LocalContext.current), number = 1)
}
