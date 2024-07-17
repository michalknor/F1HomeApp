package sk.formula.calendar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sk.formula.calendar.ui.theme.F1CalendarTheme
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        findViewById<ImageView>(R.id.settings).setOnClickListener {
//            val intent = Intent(this, HostInfoActivity::class.java)
//            startActivity(intent)
//        }
//
//        fetchApiData(findViewById<TextView>(R.id.textasd))

        setContent {
            F1CalendarTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        GrandPrixLayout()
                        GrandPrixLayout()
                    }
                }
            }
        }
    }

    private fun fetchApiData(textView: TextView) {
        val context = this
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val host = SharedPreferences.getHost(context)
                val apiResponse = URL("http://$host/api/calendar?currentVersion=0&year=2024").readText()

                Log.e("FetchApiData", apiResponse)

                withContext(Dispatchers.Main) {
                    textView.text = apiResponse
                }

            } catch (e: Exception) {
                Log.e("FetchApiData", "Exception: ${e.message}")
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
            .padding(Dp(16f)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dp(8f)),
        ) {
            Image(
                painter = painterResource(id = R.drawable.flag_bahrain),
                contentDescription = null,
                modifier = Modifier
                    .size(Dp(50f))
                    .padding(start = Dp(16f)),
                contentScale = ContentScale.Fit
            )
            OutlineText(
                text = "1. Bahrain GP",
                fontSize = 24f,
                outlineColor = Color.Black,
                textColor = Color.White,
                outlineThickness = 1f,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = Dp(20f))
            )
        }

        ScheduleRow("Practice 1", "29. 2.", "12:30")
        ScheduleRow("Practice 2", "29. 2.", "15:30")
        ScheduleRow("Practice 3", "1. 3.", "11:00")
        ScheduleRow("Qualifying", "1. 3.", "15:00")
        ScheduleRow("Race", "2. 3.", "14:00")
    }
}

@Composable
fun ScheduleRow(session: String, date: String, time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dp(4f), horizontal = Dp(4f)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlineText(
            text = session,
            fontSize = 15f,
            outlineColor = Color.Black,
            textColor = Color.White,
            outlineThickness = 1f,
            modifier = Modifier.padding(start = Dp(20f))
        )
        OutlineText(
            text = date,
            fontSize = 15f,
            outlineColor = Color.Black,
            textColor = Color.White,
            outlineThickness = 1f,
            modifier = Modifier.padding(start = Dp(20f))
        )
        OutlineText(
            text = time,
            fontSize = 15f,
            outlineColor = Color.Black,
            textColor = Color.White,
            outlineThickness = 1f,
            modifier = Modifier.padding(start = Dp(20f))
        )
    }
}

@Composable
fun OutlineText(
    text: String = "asd",
    fontSize: Float = 50f,
    outlineColor: Color = Color.Black,
    textColor: Color = Color.White,
    outlineThickness: Float = 1f,
    fontWeight: FontWeight = FontWeight.Normal,
    modifier: Modifier = Modifier
) {

    // Function to create a text offset for the outline effect
    @Composable
    fun OutlineTextLayer(offset: DpOffset) {
        Text(
            text = text,
            color = outlineColor,
            fontSize = TextUnit(fontSize, TextUnitType.Sp),
            fontWeight = fontWeight,
            modifier = modifier.offset(x = offset.x, y = offset.y)
        )
    }

    Box {
        // Create outline by placing text at various offsets
        OutlineTextLayer(offset = DpOffset(-Dp(outlineThickness), -Dp(outlineThickness)))
        OutlineTextLayer(offset = DpOffset(Dp(outlineThickness), -Dp(outlineThickness)))
        OutlineTextLayer(offset = DpOffset(-Dp(outlineThickness), Dp(outlineThickness)))
        OutlineTextLayer(offset = DpOffset(Dp(outlineThickness), Dp(outlineThickness)))

        // Main text in the center
        Text(
            text = text,
            color = textColor,
            fontSize = TextUnit(fontSize, TextUnitType.Sp),
            fontWeight = fontWeight,
            modifier = modifier
        )
    }
}