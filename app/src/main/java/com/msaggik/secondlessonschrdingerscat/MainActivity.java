package com.msaggik.secondlessonschrdingerscat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // создание полей для вывода на экран нужных значений
    private TextView coordinatesOut; // окно вывода значений координат
    private float x; // задание поля для координаты X
    private float y; // задание поля для координаты Y
    private String sDown; // строка для записи координат нажатия
    private String sMove; // строка для записи координат движения
    private String sUp; // строка для записи координат отпускания

    // задание дополнительных полей координат кота Шрёдингера
    private final float xCat = 500; // задание поля для координаты X
    private final float yCat = 500; // задание поля для координаты Y
    private final float deltaCat = 50; // допустимая погрешность в нахождении кота

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // присваивание переменной активити элемента представления activity_main
        coordinatesOut = findViewById(R.id.coordinatesOut);

        // выполнение действий при касании экрана
        coordinatesOut.setOnTouchListener(listener);
    }

    // объект обработки касания экрана (слушатель)
    private View.OnTouchListener listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) { // в motionEvent пишутся координаты
            x = motionEvent.getX(); // инициализация координат X
            y = motionEvent.getY(); // инициализация координат Y

            switch (motionEvent.getAction()) { // метод getAction() считывает состояние касания (нажатие/движение/отпускание)
                case MotionEvent.ACTION_DOWN: // нажатие
                    sDown = "Нажатие: координата X = " + x + ", координата y = " + y;
                    sMove = "";
                    sUp = "";
                    break;
                case MotionEvent.ACTION_MOVE: // движение
                    sMove = "Движение: координата X = " + x + ", координата y = " + y;
                    // задание условия нахождения кота Шрёдингера
                    if (x < (xCat + deltaCat) && x > (xCat - deltaCat) && y < (yCat + deltaCat) && y > (yCat - deltaCat)) { // если пользователь коснулся места нахождения кота
                        // размещаем тост (контекст, сообщение, длительность сообщения)
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.successful_search, Toast.LENGTH_SHORT); // инициализация
                        toast.setGravity(Gravity.LEFT, (int) xCat,(int) yCat); // задание позиции на экране (положение, смещение по оси Х, смещение по оси Y)
                        // помещение тоста в контейнер
                        LinearLayout toastContainer = (LinearLayout) toast.getView();
                        // добавление в тост картинки
                        ImageView cat = new ImageView(getApplicationContext()); // создание объекта картинки (контекст)
                        cat.setImageResource(R.drawable.found_cat); // добавление картинки из ресурсов
                        toastContainer.addView(cat, 1); // добавление картинки под индексом 1 в имеющийся контейнер
                        toast.show(); // демонстрация тоста на экране
                    }
                    break;
                case MotionEvent.ACTION_UP: // отпускание
                case MotionEvent.ACTION_CANCEL: // внутрений сбой (аналогичен ACTION_UP)
                    sMove = "";
                    sUp = "Отпускание: координата X = " + x + ", координата y = " + y;
                    break;
            }

            // вывод на экран в три строки считанных значений координат
            coordinatesOut.setText(sDown + "\n" + sMove + "\n" + sUp);

            return true; // подтверждение нашей обработки событий
        }
    };
}