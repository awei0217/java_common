/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package jvm;

/**
 *
 * @author pengwei.sun
 * @version $Id: StackOOMTest.java, v 0.1 2019��05��09�� 9:30 AM pengwei.sun Exp $
 */
public class StackOOMTest {

    /**
     * StackOverflowError ջ�ڴ�һ��
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        StackOOMTest stackOOMTest = new StackOOMTest();

        new Thread(new Runnable() {
            @Override
            public void run() {
                stackOOMTest.recursionTest("wwwwwwwwwwwwwwwwwwwwwwwwwwwyyyyyyyyyyyyyyyyyyyyyyyyywyyyyyyyyyyyyyyyyyyy");
            }
        }).start();

        Thread.sleep(10000000);
    }


    public void  recursionTest(String msg){

        StringBuilder stringBuilder = new StringBuilder(msg);

        recursionTest(msg);

    }
}