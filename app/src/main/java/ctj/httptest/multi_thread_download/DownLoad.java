package ctj.httptest.multi_thread_download;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 多线程下载图片演示
 */
public class DownLoad {
    private Handler mhandler;

    public DownLoad(Handler mhandler) {
        this.mhandler = mhandler;
    }

    //创建线程池
    private Executor threadPool =  Executors.newFixedThreadPool(3);

    static class DownLoadRunnable implements Runnable {
        private String url;
        private String fileName;
        private long start;
        private long end;
        private Handler mHandler;
        public DownLoadRunnable(String url, String fileName, long start, long end,Handler handler) {
            this.url = url;
            this.fileName = fileName;
            this.start = start;
            this.end = end;
            mHandler = handler;
        }

        @Override
        public void run() {
            try {
                URL httpUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(5000);
                connection.setRequestProperty("Range", "bytes=" + start + "-" + end);
                RandomAccessFile accessFile = new RandomAccessFile(new File(fileName), "rwd");
                accessFile.seek(start);
                InputStream inputStream = connection.getInputStream();
                byte[] b = new byte[1024*4];
                int len=0;
                while ((len=inputStream.read(b))!=-1){
                    accessFile.write(b,0,len);
                }
                if (accessFile!=null){
                    accessFile.close();
                }
                if (inputStream!=null){
                    inputStream.close();
                }
                Message msg = new Message();
                msg.what=1;
                mHandler.sendMessage(msg);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void downLoadFile(String url) {
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            int count = connection.getContentLength();
            int bolck = count / 3;//每个下载多少，这里是每个平均下载
            String fileName = getFileName(url);
            File parent = Environment.getExternalStorageDirectory();
            File downLoadFile = new File(parent,fileName);
            /**
             * 11/3   3
             * 第一个从0-2，
             * 第二个从3-5
             * 第三个 从6-10
             * 这样会多出一个所以当i==最后一个的时候就把end=count，这样才不会丢失
             */
            for (int i = 0; i < 3; i++) {
                long start = i*bolck;
                long end = (i+1)*bolck-1;
                if(i==2){
                    end = count;
                }
                DownLoadRunnable runnable = new DownLoadRunnable(url,downLoadFile.getAbsolutePath(),start,end,mhandler);
                threadPool.execute(runnable);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName(String url){
        return url.substring(url.lastIndexOf("/")+1);
    }
}
