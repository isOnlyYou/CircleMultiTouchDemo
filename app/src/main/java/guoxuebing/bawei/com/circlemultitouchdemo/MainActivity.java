package guoxuebing.bawei.com.circlemultitouchdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MyCircle my_circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        my_circle = (MyCircle) findViewById(R.id.my_circle);

        my_circle.setRadius(150);

        //my_circle.setOnTouchListener(new CircleMultiTouchListener());
    }
}
