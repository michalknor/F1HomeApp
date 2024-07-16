package sk.formula.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import sk.formula.calendar.ui.theme.F1CalendarTheme

class HostInfoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            F1CalendarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HostScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HostScreen() {
        var ip by remember {
            mutableStateOf(SharedPreferences.getIp(this))
        }
        var port by remember {
            mutableStateOf(SharedPreferences.getPort(this))
        }

//        Column {
//            Text(text = "Host", Modifier.padding(bottom = Dp(20f)))
//            Text(text = "Ip")
//            TextField(value = ip, onValueChange = {
//                ip = it
//            })
//            Text(text = "Port", Modifier.background(color = Color.Blue))
//            TextField(value = port, onValueChange = {
//                port = it
//            })
//            Button(onClick = { SharedPreferences.saveHost(super.getBaseContext(), ip, port) }) {
//                Text(text = "Save")
//            }
//        }

        BoxWithConstraints {
            Text(text = "Host", modifier = Modifier.align(Alignment.Center))
        }
    }


}