package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R
import info.upump.mycompose.ui.theme.MyTextTitleLabelWithColor

@Composable
fun DescriptionCardWithEdit(
    textComment: String,
    updateComment: (String)->Unit,
    modifier: Modifier = Modifier
) {
    val modifierCard = modifier
        .fillMaxWidth()
        .padding(start = 4.dp, end = 4.dp, top = 4.dp)
    val modifierValue = Modifier
        .fillMaxWidth()
        .padding(top = 4.dp, end = 8.dp, bottom = 4.dp)

    Card(
        modifier = modifierCard,
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            colorResource(id = R.color.colorBackgroundCardView)
        )
    ) {
        Box(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
            TextField(modifier = modifierValue,
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    backgroundColor = colorResource(R.color.colorBackgroundCardView)
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

