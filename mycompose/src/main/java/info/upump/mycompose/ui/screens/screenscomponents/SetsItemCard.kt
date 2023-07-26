package info.upump.mycompose.ui.screens.screenscomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import info.upump.mycompose.models.entity.Sets
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.theme.MyTextLabel12
import info.upump.mycompose.ui.theme.MyTextLabel16

@Composable
fun SetsItemCard(sets: Sets, navHost: NavHostController, number: Int) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navHost.navigate(NavigationItem.DetailSetDetailNavigationItem.routeWithId(sets.id))
            },
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val textNumber = createRef()
            val textWeight = createRef()
            val textPasWeight = createRef()
            val textReps = createRef()
            val guiOne = createGuidelineFromStart(0.05f)
            val guiTwo = createGuidelineFromStart(0.25f)
            val guiThree = createGuidelineFromStart(0.5f)
            val guiFour = createGuidelineFromStart(0.80f)
            val modifierOneThree = Modifier.padding(start = 2.dp)
            Text(text = "$number.",
                modifier = modifierOneThree.constrainAs(textNumber) {
                    start.linkTo(guiOne)
                }, style = MyTextLabel16)

            Text(text = sets.weight.toString(),
                modifier = modifierOneThree.constrainAs(textWeight) {
                    start.linkTo(guiTwo)
                }, style = MyTextLabel16)

            Text(text = sets.weightPast.toString(),
                modifier = modifierOneThree.constrainAs(textPasWeight) {
                    start.linkTo(guiThree)
                }, style = MyTextLabel16)

            Text(text = sets.reps.toString(),
                modifier = modifierOneThree.constrainAs(textReps) {
                    start.linkTo(guiFour)
                },  style = MyTextLabel16)

        }
        /* Row(
             verticalAlignment = Alignment.CenterVertically,
             modifier = Modifier.background(
                 MaterialTheme.colorScheme.background
             )
         ) {


         }*/
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewSetItemCard() {
    val set = Sets(weight = 20.0, reps = 5, weightPast = 18.0)
    set.id = 1

    SetsItemCard(sets = set, navHost = NavHostController(LocalContext.current), 1)

}
