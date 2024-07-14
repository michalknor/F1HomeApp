package sk.formula.calendar

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity

class HostInfoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_info)

        findViewById<Button>(R.id.save).setOnClickListener {
            val ip: String = findViewById<EditText>(R.id.ipEditView).text.toString()
            val port: String = findViewById<EditText>(R.id.portEditView).text.toString()

            SharedPreferences.saveHost(this, ip, port)
        }
    }
}