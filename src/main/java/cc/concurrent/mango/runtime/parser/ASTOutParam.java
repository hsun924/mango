package cc.concurrent.mango.runtime.parser;

import cc.concurrent.mango.runtime.RuntimeContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author ash
 */
public class ASTOutParam extends SimpleNode {

    private String beanName;
    private String propertyName; // 为""的时候表示没有属性

    public ASTOutParam(int i) {
        super(i);
    }

    public ASTOutParam(Parser p, int i) {
        super(p, i);
    }

    public void setParam(String param) {
        Pattern p = Pattern.compile(":(\\w+)(\\.\\w+)*");
        Matcher m = p.matcher(param);
        checkState(m.matches());
        beanName = m.group(1);
        propertyName = param.substring(m.end(1));
        if (!propertyName.isEmpty()) {
            propertyName = propertyName.substring(1);  // .a.b.c变为a.b.c
        }
    }

    @Override
    public Object value(RuntimeContext context) {
        return context.getPropertyValue(beanName, propertyName);
    }

}
