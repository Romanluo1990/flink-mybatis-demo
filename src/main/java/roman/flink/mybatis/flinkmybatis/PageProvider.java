package roman.flink.mybatis.flinkmybatis;

import com.github.pagehelper.PageInterceptor;
import com.google.inject.Provider;


public class PageProvider implements Provider<PageInterceptor> {

    @Override
    public PageInterceptor get() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(new PageProperties());
        return pageInterceptor;
    }
}
