/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package benchmark;

/**
 *
 * @author pengwei.sun
 * @version $Id: RecursionTest.java, v 0.1 2019Äê04ÔÂ09ÈÕ 11:19 AM pengwei.sun Exp $
 */
public class RecursionTest {

    public long fib(long n) {
        return n <= 2 ? 1 : fib(n - 1) + fib(n - 2);
    }

    public static void main(String[] args) {
       /* RecursionTest recursionTest = new RecursionTest();

        long start = System.currentTimeMillis();
        for (int i=0;i<100;i++){
            recursionTest.fib(45);
        }
        long end = System.currentTimeMillis();

        System.out.println(end-start);*/


        S s = new S();
        I i = s;

        long start = System.nanoTime();
        for(long j = 0; j < 1; j++){
            s.f();
        }
        long end = System.nanoTime();
        System.out.println(end-start);

    }

    interface I {
        void f();
    }

    static class S implements I {
        long a;

        @Override
        public void f() {
            a++;
        }
    }

}