package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import android.content.Context
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Entity
import info.upump.mycompose.ui.theme.MyTextTitleLabelWithColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateCard(
    startDate: String,
    finishDate: String,
    modifierCard: Modifier = Modifier
        .fillMaxWidth()
        .padding(start = 0.dp, end = 0.dp),
    modifierValue: Modifier = Modifier
        .padding(top = 4.dp, end = 8.dp, bottom = 4.dp),
    context: Context = LocalContext.current
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

            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val text = createRef()
                val gui = createGuidelineFromStart(0.5f)
                TextField(modifier = modifierValue,
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

                TextField(modifier = modifierValue
                    .constrainAs(text)
                    {
                        start.linkTo(gui)
                    },
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