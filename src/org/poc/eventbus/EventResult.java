package org.poc.eventbus;

/***
 * 
 * @author gaoqi3 2018.03.07
 *
 */

public interface EventResult {

	<T> T getEntity();

	boolean isSuccessful();

	String getError();

	static EventResult Ok = EventResult.Success("Ok..");

	static EventResult Lock = EventResult.Success("Lock..");

	static EventResult UnLock = EventResult.Error("UnLock..");

	static <T> EventResult generate(T arg, boolean flag, String error) {

		return new EventResult() {

			@SuppressWarnings("unchecked")
			@Override
			public T getEntity() {
				return arg;
			}

			@Override
			public String getError() {
				return error;
			}

			@Override
			public boolean isSuccessful() {
				return flag;
			}

			@Override
			public String toString() {
				return "{'successful':" + isSuccessful() + "," + "'entity':"
						+ (getEntity() != null ? getEntity().toString() : "") + "," + "error:" + getError() + "}";
			}

		};
	}

	static <T> EventResult Success(T arg) {
		return generate(arg, true, "");
	}

	static <T> EventResult Error(T arg) {
		return Error(arg, "");
	}

	static <T> EventResult Error(T arg, String error) {
		return generate(arg, false, error);
	}

}
