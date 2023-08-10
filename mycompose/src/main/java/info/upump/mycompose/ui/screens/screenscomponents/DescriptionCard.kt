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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Entity
import info.upump.mycompose.ui.theme.MyTextLabel12

@Composable
fun DescriptionCard(
    entity: Entity,
    modifierCard: Modifier = Modifier
        .fillMaxWidth()
        .padding(start = 4.dp, end = 4.dp, top = 4.dp),
    modifierLabel: Modifier = Modifier.padding(start = 8.dp),
    modifierValue: Modifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, top = 4.dp, end = 8.dp, bottom = 4.dp)
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
                text = stringResource(id = R.string.label_description_cycle)
            )

            var comment by rememberSaveable {
                mutableStateOf(entity.comment)
            }

            TextField(modifier = modifierValue,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorResource(R.color.colorBackgroundCardView)
                ),
                value = comment!!,
                onValueChange = {
                    entity.comment = it
                    comment = it
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DescriptionCardPreview() {
    val description = "bla bla bla blab lab alb bla bla bla blab lab albbla bla bla blab lab alb "
    val cycle = Cycle()
    cycle.comment = description
    DescriptionCard(cycle)
}