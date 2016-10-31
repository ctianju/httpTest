package ctj.httptest.json;

import java.util.List;

/**
 * 作者：Administrator on 2016/10/27 17:32
 */
public class Person {
    private String name;
    private int age;
    private String url;
    private List<SchoolInfo> mSchoolInfo;

    public List<SchoolInfo> getSchoolInfo() {
        return mSchoolInfo;
    }

    public void setSchoolInfo(List<SchoolInfo> schoolInfo) {
        mSchoolInfo = schoolInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
