package cl.bootcamp.actividad_7;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "prefs";
    private static final String PREF_THEME = "theme";
    private int currentTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Leer el tema guardado en preferencias
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        currentTheme = preferences.getInt(PREF_THEME, R.style.Theme_MyApp_Theme1);

        // Aplicar el tema guardado
        setTheme(currentTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mostrar mensaje de bienvenida
        Toast.makeText(this, "¡Bienvenido a mi aplicación!", Toast.LENGTH_LONG).show();

        // Configurar el botón para cambiar de tema
        Button buttonChangeTheme = findViewById(R.id.buttonChangeTheme);
        buttonChangeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Alternar el tema
                if (currentTheme == R.style.Theme_MyApp_Theme1) {
                    currentTheme = R.style.Theme_MyApp_Theme2;
                } else {
                    currentTheme = R.style.Theme_MyApp_Theme1;
                }

                // Guardar el tema en las preferencias
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(PREF_THEME, currentTheme);
                editor.apply();

                // Reiniciar la actividad para aplicar el nuevo tema
                recreate();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // Si se toca la pantalla fuera del EditText, oculta el teclado
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view != null && view instanceof android.widget.EditText) {
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                float x = ev.getRawX() + view.getLeft() - location[0];
                float y = ev.getRawY() + view.getTop() - location[1];

                if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) {
                    hideKeyboard(view);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
