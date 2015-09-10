package reflection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edgar on 14-11-22.
 */
@MyAnnotation(name="someName",  value = "Hello World")
public class MyClass {

    public List<String> stringList = new ArrayList<String>();

    public List<String> getStringList(){
        return this.stringList;
    }

    public void setStringList(List<String> list){
        this.stringList = list;
    }
}

