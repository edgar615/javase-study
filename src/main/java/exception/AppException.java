package exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/11/14.
 */
public class AppException extends RuntimeException {

    private List<InfoItem> infoItems = new ArrayList<InfoItem>();

    private static class InfoItem {
        public final String context;
        public final ErrorCode code;
        public final String message;

        public InfoItem(String context, ErrorCode code,
                        String message) {

            this.context = context;
            this.code = code;
            this.message = message;
        }
    }

    public AppException(String context, ErrorCode code,
                        String message) {
        addInfo(context, code, message);
    }

    public AppException(String context, ErrorCode code,
                        String message, Throwable cause) {
        super(cause);
        addInfo(context, code, message);
    }

    public AppException addInfo(
            String cotext, ErrorCode code, String message) {
        this.infoItems.add(
                new InfoItem(cotext, code, message));
        return this;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        //append additional context information.
        for (int i = this.infoItems.size() - 1; i >= 0; i--) {
            InfoItem info =
                    this.infoItems.get(i);
            builder.append('[');
            builder.append(info.context);
            builder.append(':');
            builder.append(info.code.getNumber());
            builder.append(']');
            builder.append(info.message);
            if (i > 0) {
                builder.append('\n');
            }
        }
        //append root causes and text from this exception first.
        if (getMessage() != null) {
            builder.append('\n');
            if (getCause() == null) {
                builder.append(getMessage());
            } else if (!getMessage().equals(getCause().toString())) {
                builder.append(getMessage());
            }
        }
        appendException(builder, getCause());
        return builder.toString();
    }

    private void appendException(
            StringBuilder builder, Throwable throwable) {
        if (throwable == null) {
            return;
        }
        appendException(builder, throwable.getCause());
        builder.append(throwable.toString());
        builder.append('\n');
    }
}
