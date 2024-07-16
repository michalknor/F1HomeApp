package sk.formula.calendar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageView>(R.id.settings).setOnClickListener {
            val intent = Intent(this, HostInfoActivity::class.java)
            startActivity(intent)
        }

        fetchApiData(findViewById<TextView>(R.id.textasd))
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