package file;

import com.google.common.collect.Lists;
import com.google.common.io.LineProcessor;

import java.io.IOException;
import java.util.List;

/**
 * Created by edgar on 15-6-25.
 */
public class ListLineProcessor implements LineProcessor<List<String>> {

    private List<String> lists = Lists.newArrayList();

    @Override
    public boolean processLine(String line) throws IOException {
        lists.add("[" + line + "]");
        return true;
    }

    @Override
    public List<String> getResult() {
        return lists;
    }
}
