package com.yzy.demo.design;

import com.yzy.demo.design.common.Template;
import com.yzy.demo.design.common.TemplateFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestA extends TemplateFilter {
    private static Logger log = LoggerFactory.getLogger(TestA.class);

    private String msg = "--TestA--";
    @Override
    public Template one() {
        log.info("【{}】第一步操作启动",msg);
        this.setMsg(msg);
        return this;
    }

    @Override
    public Template two() {
        log.info("【{}】第二步操作启动",msg);
        return this;
    }
}
