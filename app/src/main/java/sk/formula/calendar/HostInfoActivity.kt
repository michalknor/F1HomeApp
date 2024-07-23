package sk.formula.calendar

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import sk.formula.calendar.ui.theme.F1CalendarTheme
import androidx.compose.ui.platform.LocalContext

class HostInfoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            F1CalendarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HostScreen(
                        SharedPreferences.getIp(this),
                        SharedPreferences.getPort(this)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostScreen(ip: String, port: String) {
    val context = LocalContext.current
    var ipRemember by remember {
        mutableStateOf(ip)
    }
    var portRemember by remember {
        mutableStateOf(port)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Host",
            fontSize = TextUnit(20f, TextUnitType.Sp),
            color = Color.Black,
            modifier = Modifier.padding(top = 100.dp, bottom = 20.dp)
        )
        OutlinedTextField(
            value = ip,
            onValueChange = { ipRemember = it },
            label = { Text("IP") },
            modifier = Modifier.width(200.dp),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = TextUnit(
                    20f,
                    TextUnitType.Sp
                )
            )
        )
        OutlinedTextField(
            value = port,
            onValueChange = { portRemember = it },
            label = { Text("Port") },
            modifier = Modifier.width(200.dp),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = TextUnit(
                    20f,
                    TextUnitType.Sp
                )
            )
        )
        Button(
            onClick = {
                SharedPreferences.saveHost(context, ip, port)
                Toast.makeText(context, "Host saved", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(text = "Save", fontSize = TextUnit(15f, TextUnitType.Sp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    F1CalendarTheme {
        HostScreen("192.168.1.1", "8080")
    }
}