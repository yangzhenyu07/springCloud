package com.yzy.demo.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzy.demo.test.doMain.TestVersionDomain;
import org.apache.ibatis.annotations.Mapper;

/**
 * TestVersion mapper
 * @author yangzhenyu
 * */
@Mapper
public interface TestVersionMapper  extends BaseMapper<TestVersionDomain> {
}
