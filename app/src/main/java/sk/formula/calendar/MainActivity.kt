package sk.formula.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sk.formula.calendar.ui.theme.F1CalendarTheme
import kotlinx.serialization.json.*
import sk.formula.calendar.network.ApiService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            F1CalendarTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        GrandPrixLayout()
                        GrandPrixLayout()
                    }
                }
            }
        }

        val context = this

        CoroutineScope(Dispatchers.Main).launch {
            val calendar = ApiService.fetchCalendar(context, 2024)
            if (calendar != null) {
                setContent {
                    F1CalendarTheme {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            //TODO
                        }
                    }
                }
            }

        }
    }
}

@Composable
@Preview
fun GrandPrixLayout() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .paint(painterResource(id = R.drawable.monaco))
            .padding(16f.dp)
    ) {
        Row(
            Modifier.padding(start = 8f.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.flag_bahrain),
                contentDescription = null,
                modifier = Modifier
                    .size(50f.dp)
                    .width(100f.dp),
                contentScale = ContentScale.Fit
            )
            OutlineText(
                height = 50f,
                text = "1. Bahrain GP",
                fontSize = 24f,
                outlineColor = Color.Black,
                textColor = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        ScheduleRow("Practice 1", "29. 2.", "12:30 - 13:30")
        ScheduleRow("Practice 2", "29. 2.", "15:30 - 16:30")
        ScheduleRow("Practice 3", "1. 3.", "11:00 - 12:00")
        ScheduleRow("Qualifying", "1. 3.", "15:00")
        ScheduleRow("Race", "2. 3.", "14:00")
    }
    Spacer(modifier = Modifier.height(5f.dp))
}

@Composable
fun ScheduleRow(session: String, date: String, time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2f.dp)
    ) {
        OutlineText(
            text = session,
            fontSize = 20f,
            percentageWidth = 0.4f
        )
        OutlineText(
            text = date,
            fontSize = 20f,
            percentageWidth = 0.4f
        )
        OutlineText(
            text = time,
            fontSize = 20f,
            percentageWidth = 1f
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun OutlineText(
    height: Float = 30f,
    text: String = "asd",
    fontSize: Float = 50f,
    outlineColor: Color = Color.Black,
    textColor: Color = Color.White,
    fontWeight: FontWeight = FontWeight.Normal,
    percentageWidth: Float = 1f
) {

    BoxWithConstraints(
        contentAlignment = Alignment.Center, modifier = Modifier.height(height.dp)
    ) {
        val maxWidth = constraints.maxWidth

        Text(
            text = text,
            color = outlineColor,
            fontSize = TextUnit(fontSize, TextUnitType.Sp),
            fontWeight = fontWeight,
            modifier = Modifier.width((maxWidth * percentageWidth).pxToDp()),
            style = TextStyle.Default.copy(
                fontSize = TextUnit(fontSize, TextUnitType.Sp),
                drawStyle = Stroke(
                    miter = 10f,
                    width = 8f,
                    join = StrokeJoin.Round
                )
            )
        )

        // Main text in the center
        Text(
            text = text,
            color = textColor,
            fontSize = TextUnit(fontSize, TextUnitType.Sp),
            fontWeight = fontWeight,
            modifier = Modifier
                .width((maxWidth * percentageWidth).pxToDp())
                .align(Alignment.Center),
            style = TextStyle.Default.copy()
        )
    }
}

@Composable
fun Float.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }