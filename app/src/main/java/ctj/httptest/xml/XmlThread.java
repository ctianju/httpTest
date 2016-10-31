package ctj.httptest.xml;

import android.os.Handler;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Administrator on 2016/10/31 14:39
 */
public class XmlThread extends Thread {

    private String url;
    private Handler mHandler;
    private TextView mTextView;

    public XmlThread(String url, Handler handler, TextView textView) {
        this.url = url;
        mHandler = handler;
        mTextView = textView;
    }

    @Override
    public void run() {
        try {

            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            InputStream inputStream = connection.getInputStream();

            final List<Girls> girlses = new ArrayList<>();
            Girls girl = null;
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, "utf-8");
            int eventType = parser.getEventType();//标签类型
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String data = parser.getName();//拿到标签的名字;
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if("girl".equals(data)){
                            girl = new Girls();
                        }
                        if("name".equals(data)){
                            girl.setName(parser.nextText());
                        }
                        if("age".equals(data)){
                            girl.setAge(Integer.parseInt(parser.nextText()));
                        }
                        if("school".equals(data)){
                            girl.setSchool(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if("girl".equals(data)&&girl!=null){
                            girlses.add(girl);
                        }
                        break;

                }
                eventType = parser.next();
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mTextView.setText(girlses.toString());
                }
            });



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }


}
