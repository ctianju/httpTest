package ctj.httptest.json;

import java.util.List;

/**
 * 作者：Administrator on 2016/10/27 17:38
 */
public class Result {
    private int result;
    private List<Person> mPersons;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<Person> getPersons() {
        return mPersons;
    }

    public void setPersons(List<Person> persons) {
        mPersons = persons;
    }
}
