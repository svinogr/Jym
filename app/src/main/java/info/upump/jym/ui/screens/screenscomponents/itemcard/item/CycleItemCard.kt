package info.upump.jym.ui.screens.screenscomponents.itemcard.item

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jym.R
import info.upump.jym.models.entity.Cycle
import info.upump.jym.ui.screens.screenscomponents.itemcard.ItemImage
import info.upump.jym.ui.theme.MyTextLabel12
import info.upump.jym.ui.theme.MyTextTitleLabel16

@Composable
fun CycleItemCard(
    cycle: Cycle,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp)
            .clickable {
                action()
            },
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(colorResource(id = R.color.colorBackgroundCardView))

        ) {

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .height(50.dp)
                    .width(50.dp)
            ) {
                ItemImage(
                    image = cycle.image,
                    defaultImage = cycle.imageDefault
                ) {}

            }
            Column(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                val modifierCol = Modifier
                    .padding(end = 8.dp)
                Text(
                    text = cycle.title!!.capitalize(),
                    style = MyTextTitleLabel16,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier.fillMaxWidth()
                )

                if (!cycle.isDefaultType) {
                    Box(
                        modifier = modifierCol
                            .align(Alignment.End)
                            .padding(top = 4.dp)
                    ) {
                        Text(
                            text = cycle.finishStringFormatDate,
                            style = MyTextLabel12,
                        )
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCycleItemCard() {
    val c = Cycle().apply {
        title = "Новая"
        imageDefault = ""
        image = ""
    }
    CycleItemCard(c, ::println)
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PreviewCycleItemCard2() {
    val c = Cycle().apply {
        title = "Новая2"
        imageDefault = ""
        image = ""
    }
    CycleItemCard(c, ::println)
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PreviewCycleItemCard3() {
    val c = Cycle().apply {
        isDefaultType = true
        title = "Новая3"
        imageDefault = "plint1"
        image = ""
    }
    CycleItemCard(c, ::println)
}