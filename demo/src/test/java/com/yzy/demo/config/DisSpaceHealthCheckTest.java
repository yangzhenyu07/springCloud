package com.yzy.demo.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@RunWith(PowerMockRunner.class)
@PrepareForTest({})
@PowerMockIgnore({"javax.net.ssl.*","javax.management.*"})
public class DisSpaceHealthCheckTest {

    @InjectMocks
    DisSpaceHealthCheck disSpaceHealthCheck;
    @Mock
    ApplicationContext context;
    @Mock
    Connection connection;
    @Mock
    PreparedStatement preparedStatement;
    @Mock
    ResultSet resultSet;
    @Test
    public void afterPropertiesSeText() throws Exception {
        //given
        DataSource dataSource = Mockito.mock(DataSource.class);

        ReflectionTestUtils.setField(disSpaceHealthCheck,"filePath","/");
        ReflectionTestUtils.setField(disSpaceHealthCheck,"thresholdBytes",10485750L);
        ReflectionTestUtils.setField(disSpaceHealthCheck,"result","1");
        //when
        PowerMockito.when(this.context.getBean(Mockito.anyString())).thenReturn(dataSource);
        PowerMockito.when(dataSource.getConnection()).thenReturn(connection);
        PowerMockito.when(this.connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);
        PowerMockito.when(this.preparedStatement.executeQuery(Mockito.anyString())).thenReturn(resultSet);
        PowerMockito.when(this.resultSet.next()).thenReturn(false);
        //then
        disSpaceHealthCheck.afterPropertiesSet();

    }



    @Test
    public void setApplicationContext() {
        disSpaceHealthCheck.setApplicationContext(context);
    }
}