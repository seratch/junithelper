package org.junithelper.core.filter;

import org.junithelper.core.filter.impl.TrimAnnotationFilter;
import org.junithelper.core.filter.impl.TrimCommentFilter;
import org.junithelper.core.filter.impl.TrimInsideOfBraceFilter;
import org.junithelper.core.filter.impl.TrimQuotationFilter;

public class TrimFilterUtil {

    private static TrimFilterManager SINGLETON = new TrimFilterManager();

    static {
        SINGLETON.addFilter(new TrimCommentFilter(), new TrimInsideOfBraceFilter(), new TrimQuotationFilter(),
                new TrimAnnotationFilter());
    }

    public static String doAllFilters(String src) {
        return SINGLETON.doTrimAll(src);
    }

}
