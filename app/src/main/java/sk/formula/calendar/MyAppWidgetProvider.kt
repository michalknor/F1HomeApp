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
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
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

        val action = intent.action ?: ""

        if (context == null || action != WIDGET_CLICK) {
            return;
        }

        val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

        for (n in 0..10) {
            prefs.edit().putString(
                "widgetText",
                ((prefs.getString("widgetText", "0") ?: "0").toInt() + 1).toString()
            ).apply()

            updateWidgets(context)

            Thread.sleep(1000)
        }
    }

    private fun updateWidgets(context: Context) {
        val manager = AppWidgetManager.getInstance(context)
        val ids = manager.getAppWidgetIds(ComponentName(context, javaClass))

        ids.forEach { id -> updateAppWidget(context, manager, id) }
    }


    private fun pendingIntent(
        context: Context?,
        action: String,
        appWidgetId: Int
    ): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = action

        return PendingIntent.getBroadcast(
            context,
            appWidgetId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        val widgetText = prefs.getString("widgetText", "0")
        val views = RemoteViews(context.packageName, R.layout.widget_layout)

        val randomNumber = Random.nextInt(0, 100_000)
        val rng = "Random: $randomNumber"

        views.setTextViewText(R.id.t11, rng)
        views.setOnClickPendingIntent(
            R.id.button, pendingIntent(
                context, WIDGET_CLICK, appWidgetId
            )
        )

        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}