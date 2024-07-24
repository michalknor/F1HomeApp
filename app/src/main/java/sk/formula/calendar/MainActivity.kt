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
import sk.formula.calendar.model.Calendar
import sk.formula.calendar.model.GrandPrix
import sk.formula.calendar.network.ApiService
import kotlinx.serialization.decodeFromString
import sk.formula.calendar.model.Event

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                            CalendarLayout(calendar)
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun CalendarLayout(calendar: Calendar) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        for ((round, grandPrix) in calendar.grandPrixes) {
            GrandPrixLayout(round, grandPrix)
        }
    }
}

@Composable
fun GrandPrixLayout(round: String, grandPrix: GrandPrix?) {
    if (grandPrix == null) {
        return
    }
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
                text = round + ". " + grandPrix.name + " GP",
                fontSize = 24f,
                outlineColor = Color.Black,
                textColor = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        
        val events: Map<String, Event> = grandPrix.events

        events.get("1")?.let { ScheduleRow(it.abbreviation, it.timeFrom, "12:30 - 13:30") }
        events.get("2")?.let { ScheduleRow(it.abbreviation, it.timeFrom, "12:30 - 13:30") }
        events.get("3")?.let { ScheduleRow(it.abbreviation, it.timeFrom, "12:30 - 13:30") }
        events.get("4")?.let { ScheduleRow(it.abbreviation, it.timeFrom, "12:30 - 13:30") }
        events.get("5")?.let { ScheduleRow(it.abbreviation, it.timeFrom, "12:30 - 13:30") }
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


@Preview
@Composable
fun PreviewCalendar() {
    val json = """
        {
          "grandPrixes": {
            "1": {
              "name": "Bahrain",
              "cancelled": false,
              "location": {
                "circuit": "Bahrain International Circuit",
                "city": "Sakhir",
                "country": {
                  "name": "Bahrain",
                  "abbreviation": "bh"
                }
              }
            }
          }
        }
    """.trimIndent()
    val calendar = Json.decodeFromString<Calendar>(json)

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        GrandPrixLayout("1", calendar.grandPrixes.get("1"))
        GrandPrixLayout("1", calendar.grandPrixes.get("1"))
        GrandPrixLayout("1", calendar.grandPrixes.get("1"))
        GrandPrixLayout("1", calendar.grandPrixes.get("1"))
    }
}

@Composable
fun Float.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }