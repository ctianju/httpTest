package ctj.httptest.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * 作者：Administrator on 2016/10/27 10:39
 */
public class HttpThread1 extends Thread {
    private String url;
    private String name;
    private String age;

    public HttpThread1(String url, String name, String age) {
        this.url = url;
        this.name = name;
        this.age = age;
    }

    private void doGet() {


        try {
            url = url + "?name=" + URLEncoder.encode("utf-8",name) + "&age=" + age;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str;
            StringBuffer stringBuffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                stringBuffer.append(str);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void doPost(){
        try {
            Properties properties = System.getProperties();
            properties.list(System.out);
            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");
            OutputStream out = connection.getOutputStream();
            String content = "name="+name+"&age="+age;
            out.write(content.getBytes());
            connection.getInputStream(); //都需要这句发送


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        doGet();
    }
}
