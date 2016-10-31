package ctj.httptest.xml;

/**
 * 作者：Administrator on 2016/10/31 14:50
 */
public class Girls {
    public String name;
    public String school;
    public int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "[name="+name+"age="+age+"school="+school+"]";
    }
}
