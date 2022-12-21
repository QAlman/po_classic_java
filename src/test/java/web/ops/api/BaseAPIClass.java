package web.ops.api;

import static web.ops.util.datautils.ConfigPropertiesUtil.getProperty;

public class BaseAPIClass {
    protected final String host = getProperty("site.host");
}
