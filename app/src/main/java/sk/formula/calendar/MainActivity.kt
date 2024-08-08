package sk.formula.calendar

import android.annotation.SuppressLint
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.serialization.decodeFromString
import sk.formula.calendar.ui.theme.F1CalendarTheme
import kotlinx.serialization.json.*
import sk.formula.calendar.model.Calendar
import sk.formula.calendar.model.GrandPrix
import sk.formula.calendar.network.ApiService
import sk.formula.calendar.model.Event
import sk.formula.calendar.utils.*
import java.text.Normalizer

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

@SuppressLint("DiscouragedApi")
@Composable
fun GrandPrixLayout(round: String, grandPrix: GrandPrix?) {
    if (grandPrix == null) {
        return
    }

    val context = LocalContext.current
    val flagId = remember(grandPrix.location.country.abbreviation) {
        context.resources.getIdentifier(
            grandPrix.location.country.abbreviation,
            "drawable",
            context.packageName
        )
    }
    val circuitId = remember(grandPrix.location.circuit) {
        context.resources.getIdentifier(
            Normalizer.normalize(grandPrix.location.circuit, Normalizer.Form.NFD).replace("[^\\p{ASCII}]".toRegex(), "").lowercase().replace("-", "_").replace(" ", "_"),
            "drawable",
            context.packageName
        )
    }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .paint(painterResource(id = circuitId))
            .padding(16f.dp)
    ) {
        Row(
            Modifier.padding(start = 8f.dp)
        ) {
            Image(
                painter = painterResource(id = flagId),
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

        events["1"]?.let { ScheduleRow(it.abbreviation, it.timeFrom) }
        events["2"]?.let { ScheduleRow(it.abbreviation, it.timeFrom) }
        events["3"]?.let { ScheduleRow(it.abbreviation, it.timeFrom) }
        events["4"]?.let { ScheduleRow(it.abbreviation, it.timeFrom) }
        events["5"]?.let { ScheduleRow(it.abbreviation, it.timeFrom) }
    }
    Spacer(modifier = Modifier.height(5f.dp))
}

@Composable
fun ScheduleRow(session: String, timeFrom: String) {
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
            text = formatDate(timeFrom),
            fontSize = 20f,
            percentageWidth = 0.7f
        )
        OutlineText(
            text = formatTime(timeFrom),
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
              },
              "events": {
                "1": {
                  "timeFrom": "2024-02-29T12:30:00",
                  "timeTo": "2024-02-29T13:30:00",
                  "abbreviation": "P1"
                },
                "2": {
                  "timeFrom": "2024-02-29T16:00:00",
                  "timeTo": "2024-02-29T17:00:00",
                  "abbreviation": "P2"
                },
                "3": {
                  "timeFrom": "2024-03-01T13:30:00",
                  "timeTo": "2024-03-01T14:30:00",
                  "abbreviation": "P3"
                },
                "4": {
                  "timeFrom": "2024-03-01T17:00:00",
                  "timeTo": null,
                  "abbreviation": "Q"
                },
                "5": {
                  "timeFrom": "2024-03-02T16:00:00",
                  "timeTo": null,
                  "abbreviation": "R"
                }
              }
            },
            "2": {
              "name": "Saudi Arabian",
              "cancelled": false,
              "location": {
                "circuit": "Jeddah Corniche Circuit",
                "city": "Jeddah",
                "country": {
                  "name": "Saudi Arabia",
                  "abbreviation": "sa"
                }
              },
              "events": {
                "1": {
                  "timeFrom": "2024-03-07T14:30:00",
                  "timeTo": "2024-03-07T15:30:00",
                  "abbreviation": "P1"
                },
                "2": {
                  "timeFrom": "2024-03-07T18:00:00",
                  "timeTo": "2024-03-07T19:00:00",
                  "abbreviation": "P2"
                },
                "3": {
                  "timeFrom": "2024-03-08T14:30:00",
                  "timeTo": "2024-03-08T15:30:00",
                  "abbreviation": "P3"
                },
                "4": {
                  "timeFrom": "2024-03-08T18:00:00",
                  "timeTo": null,
                  "abbreviation": "Q"
                },
                "5": {
                  "timeFrom": "2024-03-09T18:00:00",
                  "timeTo": null,
                  "abbreviation": "R"
                }
              }
            },
            "3": {
              "name": "Australian",
              "cancelled": false,
              "location": {
                "circuit": "Albert Park Circuit",
                "city": "Melbourne",
                "country": {
                  "name": "Australia",
                  "abbreviation": "au"
                }
              },
              "events": {
                "1": {
                  "timeFrom": "2024-03-22T02:30:00",
                  "timeTo": "2024-03-22T03:30:00",
                  "abbreviation": "P1"
                },
                "2": {
                  "timeFrom": "2024-03-22T06:00:00",
                  "timeTo": "2024-03-22T07:00:00",
                  "abbreviation": "P2"
                },
                "3": {
                  "timeFrom": "2024-03-23T02:30:00",
                  "timeTo": "2024-03-23T03:30:00",
                  "abbreviation": "P3"
                },
                "4": {
                  "timeFrom": "2024-03-23T06:00:00",
                  "timeTo": null,
                  "abbreviation": "Q"
                },
                "5": {
                  "timeFrom": "2024-03-24T05:00:00",
                  "timeTo": null,
                  "abbreviation": "R"
                }
              }
            },
            "4": {
              "name": "Japanese",
              "cancelled": false,
              "location": {
                "circuit": "Suzuka International Racing Course",
                "city": "Suzuka",
                "country": {
                  "name": "Japan",
                  "abbreviation": "jp"
                }
              },
              "events": {
                "1": {
                  "timeFrom": "2024-04-05T04:30:00",
                  "timeTo": "2024-04-05T05:30:00",
                  "abbreviation": "P1"
                },
                "2": {
                  "timeFrom": "2024-04-05T08:00:00",
                  "timeTo": "2024-04-05T09:00:00",
                  "abbreviation": "P2"
                },
                "3": {
                  "timeFrom": "2024-04-06T04:30:00",
                  "timeTo": "2024-04-06T05:30:00",
                  "abbreviation": "P3"
                },
                "4": {
                  "timeFrom": "2024-04-06T08:00:00",
                  "timeTo": null,
                  "abbreviation": "Q"
                },
                "5": {
                  "timeFrom": "2024-04-07T07:00:00",
                  "timeTo": null,
                  "abbreviation": "R"
                }
              }
            }
          }
        }
    """.trimIndent()
    val calendar = Json.decodeFromString<Calendar>(json)

    F1CalendarTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            CalendarLayout(calendar)
        }
    }
}

@Composable
fun Float.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }