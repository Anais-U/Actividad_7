package cl.bootcamp.actividad_7;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetCounterProvider extends AppWidgetProvider {

    private static int counter = 0;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            // Configurar el layout del widget
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_counter);

            // Configurar la acción del botón de incremento
            Intent incrementIntent = new Intent(context, WidgetCounterProvider.class);
            incrementIntent.setAction("INCREMENT_COUNTER");
            PendingIntent incrementPendingIntent = PendingIntent.getBroadcast(context, 0, incrementIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.buttonCounter, incrementPendingIntent);

            // Configurar la acción del botón de reinicio
            Intent resetIntent = new Intent(context, WidgetCounterProvider.class);
            resetIntent.setAction("RESET_COUNTER");
            PendingIntent resetPendingIntent = PendingIntent.getBroadcast(context, 0, resetIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.buttonReset, resetPendingIntent);

            // Actualizar el widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if ("INCREMENT_COUNTER".equals(intent.getAction())) {
            counter++;
            updateCounterWidget(context);
            Toast.makeText(context, "Contador: " + counter, Toast.LENGTH_SHORT).show();
        } else if ("RESET_COUNTER".equals(intent.getAction())) {
            counter = 0;
            updateCounterWidget(context);
            Toast.makeText(context, "Contador reiniciado", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCounterWidget(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_counter);
        views.setTextViewText(R.id.textCounter, String.valueOf(counter));

        // Actualizar todos los widgets de este tipo
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName widget = new ComponentName(context, WidgetCounterProvider.class);
        appWidgetManager.updateAppWidget(widget, views);
    }
}
