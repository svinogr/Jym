package info.upump.mycompose.ui.screens.screenscomponents.itemcard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle

@Composable
fun EmptyItem(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(colorResource(id = R.color.colorBackgroundCardView)).fillMaxWidth()

        ) {

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .height(50.dp)
                    .width(50.dp)
            ) {

            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmptyItemPreview(){
    EmptyItem()
}