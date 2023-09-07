package info.upump.mycompose.ui.screens.screenscomponents.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R
import info.upump.mycompose.ui.theme.MyTextTitleLabelWithColor

@Composable
fun CardDescription(
    textDescription: String,
    readonly: Boolean = true,
    modifier: Modifier = Modifier
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
            .fillMaxWidth()
            .padding(top = 4.dp, end = 0.dp, bottom = 4.dp),
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                backgroundColor = colorResource(R.color.colorBackgroundCardView)
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DescriptionCardPreview() {
    CardDescription("whagdwhgdakwjdgk wahdgawhdak")
}

@Preview(showBackground = true)
@Composable
fun DescriptionCardPreview2() {
    CardDescription("whagdwhgdakwjdgk wahdgawhdak")
}