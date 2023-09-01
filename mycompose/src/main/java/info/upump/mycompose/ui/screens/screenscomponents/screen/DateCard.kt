package info.upump.mycompose.ui.screens.screenscomponents.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.theme.MyTextTitleLabelWithColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateCard(
    startDate: String,
    finishDate: String,
    modifier: Modifier = Modifier
) {
    val modifierCard = modifier
        .fillMaxWidth()
        .padding(start = 0.dp, end = 0.dp, top = 0.dp)
    val modifierValue = Modifier.padding(top = 4.dp, end = 8.dp, bottom = 4.dp)
    Card(
        modifier = modifierCard,
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            colorResource(id = R.color.colorBackgroundCardView)
        )
    ) {
        Box(
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
                //,
                //verticalAlignment = Alignment.CenterVertically
            )
            {

                TextField(modifier = modifierValue.weight(1f),
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        containerColor = colorResource(R.color.colorBackgroundCardView)
                    ),
                    readOnly = true,
                    value = startDate,
                    onValueChange = {
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.label_start_cycle),
                            style = MyTextTitleLabelWithColor
                        )
                    }
                )
                TextField(modifier = modifierValue.weight(1f),
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        containerColor = colorResource(R.color.colorBackgroundCardView)
                    ),
                    readOnly = true,
                    value = finishDate,
                    onValueChange = {
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.label_finish_cycle),
                            style = MyTextTitleLabelWithColor,
                        )
                    }
                )

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DateCard() {
    val cycle = Cycle()
    DateCard(cycle.startStringFormatDate, cycle.finishStringFormatDate)
}