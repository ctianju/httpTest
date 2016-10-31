package ctj.httptest.json;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import ctj.httptest.R;

/**
 * 作者：Administrator on 2016/10/28 14:09
 */
public class JsonActivity extends AppCompatActivity {
    private ListView mListView;
    private JsonAdapter mAdapter;
    private String url;
    private Handler mHandler =new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.json);
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new JsonAdapter(this);

        url = "http://192.168.0.107:8080/web/JsonServlet";
        new HttpJson(url,this,mListView,mAdapter,mHandler).start();
    }
}
