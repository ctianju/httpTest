package ctj.httptest.multi_thread_download;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ctj.httptest.R;

/**
 * 作者：Administrator on 2016/10/31 15:49
 */
public class DownLoadActivity extends AppCompatActivity {
    private TextView mTextView;
    private Button mButton;
    private String url;
    int count = 0;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int result = msg.what;
            count+=result;
            if(count==3){
                mTextView.setText("下载成功");
            }

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download);
        url = "http://192.168.0.107:8080/web/timg.jpg";
        mButton = (Button) findViewById(R.id.button_down);
        mTextView = (TextView) findViewById(R.id.down_text);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new DownLoad(mHandler).downLoadFile(url);
                    }
                }).start();

            }
        });
    }
}
