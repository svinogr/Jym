package info.upump.mycompose.ui.screens.screenscomponents.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R
import info.upump.mycompose.ui.theme.MyTextTitleLabelWithColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionCardWithEdit(
    textComment: String,
    updateComment: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val modifierCard = modifier
        .fillMaxWidth()
        .padding(0.dp)
    val modifierValue = Modifier
        .fillMaxWidth()
        .padding(top = 4.dp, end = 0.dp, bottom = 4.dp)

    Card(
        modifier = modifierCard,
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            colorResource(id = R.color.colorBackgroundCardView)
        )
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp)) {
            TextField(modifier = modifierValue,
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = colorResource(R.color.colorBackgroundChips),
                    unfocusedIndicatorColor = colorResource(R.color.colorBackgroundChips),
                    disabledIndicatorColor = colorResource(R.color.colorBackgroundChips),
                    containerColor = colorResource(R.color.colorBackgroundCardView)
                ),
                value = textComment,
                onValueChange = {
                    updateComment(it)
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.label_description_cycle),
                        style = MyTextTitleLabelWithColor
                    )
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.label_description_cycle))
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DescriptionCardWithVMPreview() {
    DescriptionCardWithEdit("its comment", ::print)
}

