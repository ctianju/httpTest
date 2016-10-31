package ctj.httptest.upload;

import android.content.Entity;
import android.os.Environment;
import android.util.Log;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 作者：Administrator on 2016/10/31 16:38
 */
public class UploadThread extends Thread {
    private String fileName;
    private String url;

    public UploadThread(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
    }

    @Override
    public void run() {
        uploadHttpClient();
    }

    private void uploadHttpClient(){
        //使用httpClient第三方来实现的上传文件
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        MultipartEntity muti = new MultipartEntity();
        File file = new File(Environment.getExternalStorageDirectory(),"timg.jpg");
        FileBody fileBody = new FileBody(file);
        muti.addPart("file",fileBody);
        post.setEntity(muti);
        try {
            HttpResponse response = client.execute(post);
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                Log.i("TAG", "uploadHttpClient: "+ EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void httpSelfUpload(){//自己拼接的上传格式
        String boundary = "---------------------------7e037fc30456";
        String preFix = "--";
        String end = "\r\n";
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.setDoOutput(true);//允许想服务器写
            connection.setDoInput(true);//允许向服务器读取数据
            connection.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(preFix+boundary+end);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""+"timg.jpg"+"\""+end);
            outputStream.writeBytes(end);
            FileInputStream inputStream = new FileInputStream(new File(fileName));
            byte[] b = new byte[1024*4];
            int len;
            while ((len = inputStream.read(b))!= -1){
                outputStream.write(b,0,len);

            }
            outputStream.writeBytes(end);
            outputStream.writeBytes(preFix+boundary+preFix+end);
            outputStream.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = reader.readLine())!=null){
                sb.append(str);
            }
            Log.i("TAG", "Response: "+sb.toString());
            if(outputStream!=null){
                outputStream.close();
            }
            if(reader!=null){
                reader.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
