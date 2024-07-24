package sk.formula.calendar.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import sk.formula.calendar.SharedPreferences
import sk.formula.calendar.model.Calendar
import java.net.URL

object ApiService {
    suspend fun fetchCalendar(context: Context, year: Int): Calendar? {
        return try {
            withContext(Dispatchers.IO) {
                val host = SharedPreferences.getHost(context)
                val apiResponse = URL("http://$host/api/calendar?currentVersion=0&year=$year").readText()

                Json.decodeFromString<Calendar>(apiResponse)
            }
        } catch (e: Exception) {
            Log.e("FetchApiData", "Exception: ${e.message}")
            null
        }
    }
}