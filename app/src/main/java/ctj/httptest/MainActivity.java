package ctj.httptest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ctj.httptest.httpclient.HttpClientThread;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText name;
    private EditText age;

    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.ed_name);
        age = (EditText) findViewById(R.id.ed_age);
        findViewById(R.id.submit).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String url ="http://192.168.0.107:8080/web/MyServlet" ;
        url = url+"?name="+name.getText().toString()+"&age="+age.getText().toString();
       new HttpClientThread(url,name.getText().toString(),age.getText().toString()).start();
    }
}
