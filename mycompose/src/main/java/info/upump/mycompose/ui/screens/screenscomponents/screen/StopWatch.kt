package info.upump.mycompose.ui.screens.screenscomponents.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R
import info.upump.mycompose.ui.theme.MyTextTitleLabel16
import info.upump.mycompose.ui.theme.MyTextTitleLabel20

enum class StopWatchState {
    STOP, RESUME, PAUSE
}

@Composable
fun StopWatch(
    hour: Int,
    minute: Int,
    second: Int,
    stopwatchState: StopWatchState,
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
        Column(modifier = modifier.fillMaxWidth()) {
            Row(modifier = Modifier.align(CenterHorizontally)) {
                Text(
                    style = MyTextTitleLabel20,
                    text = hour.toString(),
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                )
                Text(
                    text = ":", modifier = Modifier
                        .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                        .align(CenterVertically)
                )
                Text(
                    style = MyTextTitleLabel20,
                    text = minute.toString(),
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                )
                Text(
                    text = ":", modifier = Modifier
                        .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                        .align(CenterVertically)
                )
                Text(
                    style = MyTextTitleLabel20,
                    text = second.toString(),
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                )

            }
            Row {
                if (stopwatchState == StopWatchState.STOP) {
                    OutlinedButton(
                        onClick = {},
                        modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(style = MyTextTitleLabel16, text = "start", modifier = Modifier)
                    }
                }

                if (stopwatchState == StopWatchState.PAUSE) {
                    OutlinedButton(
                        onClick = {},
                        modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f)
                    ) {
                        Text(style = MyTextTitleLabel16, text = "stop", modifier = Modifier)
                    }
                    OutlinedButton(
                        onClick = {},
                        modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f)
                    ) {
                        Text(style = MyTextTitleLabel16, text = "start", modifier = Modifier)
                    }
                }

                if (stopwatchState == StopWatchState.RESUME) {
                    OutlinedButton(
                        onClick = {},
                        modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f)
                    ) {
                        Text(style = MyTextTitleLabel16, text = "stop", modifier = Modifier)
                    }
                    OutlinedButton(
                        onClick = {},
                        modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f)
                    ) {
                        Text(style = MyTextTitleLabel16, text = "pause", modifier = Modifier)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StopWatchStopPreview() {
    StopWatch(10, 2, 30, StopWatchState.STOP)
}

@Preview(showBackground = true)
@Composable
fun StopWatchPausePreview() {
    StopWatch(10, 2, 30, StopWatchState.PAUSE)
}

@Preview(showBackground = true)
@Composable
fun StopWatchResumePreview() {
    StopWatch(10, 2, 30, StopWatchState.RESUME)
}
