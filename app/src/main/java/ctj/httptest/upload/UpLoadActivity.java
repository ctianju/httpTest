package ctj.httptest.upload;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

import ctj.httptest.R;

/**
 * 作者：Administrator on 2016/10/31 17:04
 */
public class UpLoadActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_activity);
    }

    public void doUpload(View v){
        String url = "http://192.168.0.107:8080/upLoadAndroid/UpLoad";

        File parent = Environment.getExternalStorageDirectory();
        File uploadFile = new File(parent,"timg.jpg");
        String fileName = uploadFile.getAbsolutePath();
        UploadThread thread = new UploadThread(fileName,url);
        thread.start();
    }
}
