package ctj.httptest.xml;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ctj.httptest.R;

/**
 * 作者：Administrator on 2016/10/31 15:03
 */
public class XmlActivity extends AppCompatActivity {
    private String url;
    private Handler mHandler = new Handler();
    private TextView mTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml_activity);
        mTextView = (TextView) findViewById(R.id.textView);
        url = "http://192.168.0.107:8080/web/girls.xml";
        new XmlThread(url,mHandler,mTextView).start();
    }
}
