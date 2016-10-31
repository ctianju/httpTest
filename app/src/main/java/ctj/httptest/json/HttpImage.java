package ctj.httptest.json;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 作者：Administrator on 2016/10/28 16:39
 */
public class HttpImage extends Thread {
    private ImageView mImageView;
    private String urlS;
    private Handler mHandler;

    public HttpImage(ImageView imageView, String url, Handler handler) {
        mImageView = imageView;
        this.urlS = url;
        mHandler = handler;
    }

    @Override
    public void run() {
        URL url = null;
        try {
            url = new URL(urlS);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            InputStream inputStream = connection.getInputStream();
            final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mImageView.setImageBitmap(bitmap);
                }
            });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
