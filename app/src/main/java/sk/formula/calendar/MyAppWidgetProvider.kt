package sk.formula.calendar

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import kotlin.random.Random


class MyAppWidgetProvider : AppWidgetProvider() {
    companion object {
        private const val WIDGET_CLICK = "widgetsclick"
        private const val WIDGET_ID_EXTRA = "widget_id_extra"
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (WIDGET_CLICK == intent.action) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val views = RemoteViews(context.packageName, R.layout.widget_layout)
            val randomNumber = Random.nextInt(0, 100_000)
            val lastUpdate = "Random: $randomNumber"
            val appWidgetId = intent.getIntExtra(WIDGET_ID_EXTRA, 0)
            views.setTextViewText(R.id.widget_title, lastUpdate)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.widget_layout)
        val randomNumber = Random.nextInt(0, 100_000)
        val lastUpdate = "Random: $randomNumber"
        views.setTextViewText(R.id.widget_title, lastUpdate)
        views.setOnClickPendingIntent(R.id.reload, getPendingSelfIntent(context, appWidgetId, WIDGET_CLICK))
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun getPendingSelfIntent(context: Context, appWidgetId: Int, action: String): PendingIntent {
        val intent = Intent(context, javaClass)
        intent.action = action
        intent.putExtra(WIDGET_ID_EXTRA, appWidgetId)
        return PendingIntent.getBroadcast(context, appWidgetId, intent, 0)
    }




    /*

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        Log.e("Mamamaevu", "Mamamaevu")
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (intent?.action == "ACTION_WIDGET_CLICKED") {
            // Handle widget click event here
            // For now, let's just log it
            println("Widget clicked!")
            Log.e("Mamamaevu", "Mamamaevu")
        }
    }

    companion object {
        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
//            val remoteViews = RemoteViews(context.packageName, R.layout.widget_layout)
//
//            val randomNumber = Random.nextInt(0, 100_000);
//
//            // Update your widget's content
//            remoteViews.setTextViewText(R.id.widget_title, "rng: $randomNumber")
//
//            // Update the widget
//            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)

            val views = RemoteViews(context.packageName, R.layout.widget_layout)

            // Set up a click listener
            val intent = Intent(context, MyAppWidgetProvider::class.java)
            intent.action = "ACTION_WIDGET_CLICKED"

            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setOnClickPendingIntent(R.id.reload, pendingIntent)

            val randomNumber = Random.nextInt(0, 100_000);
            views.setTextViewText(R.id.widget_title, "rng: $randomNumber")

            // Update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }*/
}