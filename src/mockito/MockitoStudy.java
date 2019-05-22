/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package mockito;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 *
 * @author pengwei.sun
 * @version $Id: MockitoStudy.java, v 0.1 2019年03月28日 12:33 AM pengwei.sun Exp $
 */
public class MockitoStudy {

    @Test
    public void verify_behaviour(){
        //模拟创建一个List对象
        List mock = mock(List.class);
        //使用mock的对象
        mock.add(1);
        mock.clear();
        //验证add(1)和clear()行为是否发生
        verify(mock).add(1);
        verify(mock).clear();


        // mock 方法不仅可以 Mock 接口类, 还可以 Mock 具体的类型.
        HashMap mockMap = mock(HashMap.class);

        Assert.assertTrue(mockMap instanceof Map);

        Assert.assertTrue(mockMap instanceof HashMap);


    }

    @Test
    public void mockConfig(){
        Map map = mock(Map.class);
        //当调用map.size 的方法时候返回100
        when(map.size()).thenReturn(100);

        when(map.put(1,1)).thenReturn(true);
    }







}