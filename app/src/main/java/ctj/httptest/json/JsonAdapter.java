package ctj.httptest.json;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ctj.httptest.R;

/**
 * 作者：Administrator on 2016/10/28 14:17
 */
public class JsonAdapter extends BaseAdapter {
    private List<Person> mList;
    private Context mContext;
    private LayoutInflater mInflater;
    private Handler hanlder=new Handler();

    public JsonAdapter(List<Person> list, Context context) {
        mList = list;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public JsonAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }
    public void setData(List<Person> data){
        this.mList = data;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup group) {
        Holder holder =null;
        if(view ==null){
            view = mInflater.inflate(R.layout.json_item,group,false);
            holder = new Holder(view);
            view.setTag(holder);
        }else {
            holder = (Holder) view.getTag();
        }
        Person person = mList.get(i);

        holder.name.setText(person.getName());
        holder.age.setText(String.valueOf(person.getAge()));
        List<SchoolInfo> schools = person.getSchoolInfo();
        SchoolInfo schoolinfo1 = schools.get(0);
        SchoolInfo schoolInfo2 = schools.get(1);

        holder.school1.setText(schoolinfo1.getSchool_name());
        holder.school2.setText(schoolInfo2.getSchool_name());
        new HttpImage(holder.header,person.getUrl(),hanlder).start();
        return view;
    }

    class Holder{
        private TextView name;
        private TextView age;
        private TextView school1;
        private TextView school2;
        private ImageView header;

        public Holder(View view) {
            this.name = (TextView) view.findViewById(R.id.name);
            this.age = (TextView) view.findViewById(R.id.age );
            this.school1 = (TextView) view.findViewById(R.id.school1);
            this.school2 = (TextView) view.findViewById(R.id.school2);
            this.header = (ImageView) view.findViewById(R.id.headerIcon);
        }
    }
}
