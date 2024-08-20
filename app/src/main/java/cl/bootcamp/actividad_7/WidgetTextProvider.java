package cl.bootcamp.actividad_7;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetTextProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_text);

            Intent intent = new Intent(context, WidgetTextProvider.class);
            intent.setAction("cl.bootcamp.actividad_7.ACTION_CHANGE_TEXT");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE);

            views.setOnClickPendingIntent(R.id.textWidget, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if ("cl.bootcamp.actividad_7.ACTION_CHANGE_TEXT".equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_text);
            views.setTextViewText(R.id.textWidget, "¡Texto cambiado!");

            Toast.makeText(context, "Texto cambiado", Toast.LENGTH_SHORT).show();

            ComponentName widget = new ComponentName(context, WidgetTextProvider.class);
            appWidgetManager.updateAppWidget(widget, views);
        }
    }
}
