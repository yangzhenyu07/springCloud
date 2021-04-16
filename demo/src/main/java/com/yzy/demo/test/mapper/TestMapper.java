package com.yzy.demo.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzy.demo.test.doMain.TestDomain;
import com.yzy.demo.test.vo.TestQuert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * test mapper
 * @author yangzhenyu
 * */
@Mapper
public interface TestMapper  extends BaseMapper<TestDomain> {
    /**
     * test  mapper
     * */
    List<TestDomain> queryData(@Param("params") TestQuert vo);

}
