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
 * @version $Id: MockitoStudy.java, v 0.1 2019��03��28�� 12:33 AM pengwei.sun Exp $
 */
public class MockitoStudy {

    @Test
    public void verify_behaviour(){
        //ģ�ⴴ��һ��List����
        List mock = mock(List.class);
        //ʹ��mock�Ķ���
        mock.add(1);
        mock.clear();
        //��֤add(1)��clear()��Ϊ�Ƿ���
        verify(mock).add(1);
        verify(mock).clear();


        // mock ������������ Mock �ӿ���, ������ Mock ���������.
        HashMap mockMap = mock(HashMap.class);

        Assert.assertTrue(mockMap instanceof Map);

        Assert.assertTrue(mockMap instanceof HashMap);


    }

    @Test
    public void mockConfig(){
        Map map = mock(Map.class);
        //������map.size �ķ���ʱ�򷵻�100
        when(map.size()).thenReturn(100);

        when(map.put(1,1)).thenReturn(true);
    }







}