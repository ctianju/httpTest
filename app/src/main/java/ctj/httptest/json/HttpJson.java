package ctj.httptest.json;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ctj.httptest.json.JsonAdapter;
import ctj.httptest.json.Person;
import ctj.httptest.json.SchoolInfo;

/**
 * 作者：Administrator on 2016/10/28 14:17
 */
public class HttpJson extends Thread {
    private String urlS;
    private Context mContext;
    private ListView listView;
    private JsonAdapter adapter;
    private Handler mHandler;

    public HttpJson(String urlS, Context context, ListView listView, JsonAdapter  adapter,Handler handler) {
        this.urlS = urlS;
        mContext = context;
        this.listView = listView;
        this.adapter = adapter;
        this.mHandler = handler;
    }



    public HttpJson(String urlS, Context context) {
        this.urlS = urlS;
        mContext = context;
    }

    @Override
    public void run() {
        URL url = null;
        try {
            url = new URL(urlS);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            Log.i("TAG", "run: "+stringBuffer.toString());
            try {
                final List<Person> data = parseJson(stringBuffer.toString());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setData(data);
                        listView.setAdapter(adapter);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Person> parseJson(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        List<Person> listPersons = new ArrayList<>();
        int result = object.getInt("result");
        if (result == 1) {
            JSONArray array = object.getJSONArray("mPersons");
            for (int i = 0; i < array.length(); i++) {
                Person personObject = new Person();
                JSONObject person = array.getJSONObject(i);
                String name = person.getString("name");
                int age = person.getInt("age");
                String url = person.getString("url");
                personObject.setName(name);
                personObject.setAge(age);
                personObject.setUrl(url);
                JSONArray schools = person.getJSONArray("mSchoolInfo");
                List<SchoolInfo> schs = new ArrayList<>();
                personObject.setSchoolInfo(schs);
                listPersons.add(personObject);
                for (int j = 0; j < schools.length(); j++) {
                    JSONObject school = schools.getJSONObject(j);
                    String schoolName = school.getString("school_name");
                    SchoolInfo sc = new SchoolInfo();
                    sc.setSchool_name(schoolName);
                    schs.add(sc);
                }


            }

            for (int k = 0; k < listPersons.size() ; k++) {
                Log.i("TAG", "Name: "+listPersons.get(k).getName());
            }
            return listPersons;
        } else {
            Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
        }
        return  null;

    }
}
