package atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * Created by sunpengwei on 2016/10/4.
 */
public class AtomicReferenceFieldUpdaterTest {
	static class A {
		volatile String stringValue = "abc";
	}


	final static AtomicReferenceFieldUpdater<A ,String> ATOMIC_REFERENCE_FIELD_UPDATER = AtomicReferenceFieldUpdater.newUpdater(A.class, String.class, "stringValue");

	public static void main(String[] args) {
		final A a = new A();

		boolean flag = ATOMIC_REFERENCE_FIELD_UPDATER.compareAndSet(a,"abc","123");
		System.out.println(flag);
		for(int i = 0 ; i < 100 ; i++) {
			final int num = i;
			new Thread() {
				public void run() {
					if(ATOMIC_REFERENCE_FIELD_UPDATER.compareAndSet(a, "spw", "abc")) {
						System.out.println("我是线程：" + num + " 我对对应的值做了修改！");
						System.out.println(ATOMIC_REFERENCE_FIELD_UPDATER.get(a));
					}

				}
			}.start();
		}

	}
}
