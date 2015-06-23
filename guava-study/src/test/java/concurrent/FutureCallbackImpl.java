package concurrent;

import com.google.common.util.concurrent.FutureCallback;

/**
 * Created by Administrator on 2015/6/23.
 */
public class FutureCallbackImpl implements FutureCallback<String> {

    private StringBuilder builder = new StringBuilder();

    @Override
    public void onSuccess(String result) {
        System.out.println(result);
        builder.append(result).append(" successfully");
    }

    @Override
    public void onFailure(Throwable t) {
        System.out.println("failure");
        builder.append(t.toString());
    }

    public String getCallbackResult() {
        return builder.toString();
    }
}
