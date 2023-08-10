package info.upump.mycompose.ui.screens.screenscomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.theme.MyTextLabel12

@Preview(showBackground = true)
@Composable
fun CardTitlePreview() {
    val cycle = Cycle()
    CardTitle(cycle)
}

@Composable
fun CardTitle(
    cycle: Cycle,
    modifierCard: Modifier = Modifier
        .fillMaxWidth()
        .padding(start = 4.dp, end = 4.dp, top = 4.dp),
    modifierLabel: Modifier = Modifier.padding(start = 8.dp),
    modifierValue: Modifier = Modifier.padding(start = 10.dp, top = 4.dp, end = 8.dp, bottom = 4.dp)
) {
    Card(
        modifier = modifierCard,
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            colorResource(id = R.color.colorBackgroundCardView)
        )
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = modifierLabel,
                style = MyTextLabel12,
                text = stringResource(id = R.string.label_title_cycle)
            )
            var title by remember {
                mutableStateOf(cycle.title!!)
            }

            TextField(modifier = modifierValue.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorResource(R.color.colorBackgroundCardView)
                ),
                value = title,
                onValueChange = {
                    title = it
                    cycle.title = it
                }, placeholder = {
                    Text(stringResource(id = R.string.title_cycle_hint))
                }
            )
        }
    }

}