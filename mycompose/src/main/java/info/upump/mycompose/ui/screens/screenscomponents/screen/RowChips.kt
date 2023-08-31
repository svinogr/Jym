package info.upump.mycompose.ui.screens.screenscomponents.screen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R

data class Chips(val title:String, val icon: Int, val action: ()-> Unit)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowChips(modifier: Modifier, vararg chips: Chips,) {
    val scrollState = rememberScrollState()
    Row(modifier =  modifier.padding(start = 4.dp, end = 4.dp).horizontalScroll(scrollState)) {
        chips.forEach {it->
            AssistChip(
                onClick = { it.action() },
                label = { Text(it.title) },
                modifier = Modifier.padding(end = 4.dp, start = 4.dp),
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = colorResource(id = R.color.colorBackgroundChips)
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = it.icon),
                        " ",
                        Modifier.size(AssistChipDefaults.IconSize)
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RowChipsPreview() {
    RowChips(modifier = Modifier,
        Chips("Коментарий", R.drawable.ic_info_black_24dp, ::println),
        Chips("Коментарий2", R.drawable.ic_add_black_24dp, ::println)
        )

}