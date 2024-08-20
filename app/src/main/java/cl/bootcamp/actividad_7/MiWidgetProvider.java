package cl.bootcamp.actividad_7;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MiWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Iterar sobre todos los widgets activos
        for (int widgetId : appWidgetIds) {
            // Crear un intent para lanzar MainActivity
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE);

            // Obtener el layout del widget y configurar el botón para abrir MainActivity
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.widget_button, pendingIntent);

            // Configurar una acción de ejemplo para el TextView
            Intent toastIntent = new Intent(context, MiWidgetProvider.class);
            toastIntent.setAction("TOAST_ACTION");
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_text, toastPendingIntent);

            // Actualizar el widget
            appWidgetManager.updateAppWidget(widgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if ("TOAST_ACTION".equals(intent.getAction())) {
            Toast.makeText(context, "Texto del widget presionado", Toast.LENGTH_SHORT).show();
        }
    }
}
