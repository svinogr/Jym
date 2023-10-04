package info.upump.jym.ui.screens.screenscomponents.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jym.R
import info.upump.jym.ui.theme.MyTextTitleLabelWithColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDescription(
    modifier: Modifier = Modifier,
    textDescription: String,
    readonly: Boolean = true
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 0.dp, end = 0.dp, top = 0.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            colorResource(id = R.color.colorBackgroundCardView)
        )
    ) {
        TextField(
            modifier = Modifier
                .drawCustomIndicatorLine(
                    BorderStroke(
                        DividerDefaults.Thickness,
                        colorResource(R.color.colorBackgroundChips)
                    ), 8.dp
                )
                .fillMaxWidth(),
            colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = colorResource(R.color.colorBackgroundChips),
                unfocusedIndicatorColor = colorResource(R.color.colorBackgroundCardView),
                disabledIndicatorColor = colorResource(R.color.colorBackgroundChips),
                containerColor = colorResource(R.color.colorBackgroundCardView),
            ),
            readOnly = readonly,
            value = textDescription,
            onValueChange = {
            },
            label = {
                Text(
                    text = stringResource(id = R.string.label_description_cycle),
                    style = MyTextTitleLabelWithColor
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDescriptionVariableTitle(
    modifier: Modifier = Modifier,
    title: String = "",
    textDescription: String = "",
    readonly: Boolean = true
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 0.dp, end = 0.dp, top = 0.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            colorResource(id = R.color.colorBackgroundCardView)
        )
    ) {
        TextField(modifier = Modifier
            .drawCustomIndicatorLine(
                BorderStroke(
                    DividerDefaults.Thickness,
                    colorResource(R.color.colorBackgroundChips)
                ), 8.dp
            )
            .fillMaxWidth()
            .padding(top = 0.dp, end = 0.dp, bottom = 0.dp),
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = colorResource(R.color.colorBackgroundCardView),
                unfocusedIndicatorColor = colorResource(R.color.colorBackgroundCardView),
                disabledIndicatorColor = colorResource(R.color.colorBackgroundChips),
                containerColor = colorResource(R.color.colorBackgroundCardView),
            ),
            readOnly = readonly,
            value = textDescription,
            onValueChange = {
            },
            label = {
                Text(
                    text = title,
                    style = MyTextTitleLabelWithColor
                )
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DescriptionCardPreview() {
    CardDescription(textDescription = "whagdwhgdakwjdgk wahdgawhdak")
}

@Preview(showBackground = true)
@Composable
fun DescriptionCardPreview2() {
    CardDescription(textDescription = "whagdwhgdakwjdgk wahdgawhdak")
}

@Preview(showBackground = true)
@Composable
fun DescriptionCardTitle() {
    CardDescriptionVariableTitle(title = "Описание")
}