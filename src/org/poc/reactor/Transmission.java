package org.poc.reactor;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public interface Transmission {

	Receiver getReceiver();

	Generator getGenerator();

	Integer getCapacity();

	static public Transmission generate() {
		return new Impl();
	}

	final class Impl implements Transmission {

		final Assign assign = Assign.generateDoubleState(DefContainerCapacity);

		final Receiver receiver = Receiver.generate(assign);

		final Generator generator = Generator.generate(assign);

		Impl() {

		}

		@Override
		public Receiver getReceiver() {
			return receiver;
		}

		@Override
		public Generator getGenerator() {
			return generator;
		}

		@Override
		public Integer getCapacity() {
			return assign.getCapacity();
		}

	}

	final class Entity {

		int deep = 0;

		final int capacity;

		final int limit;

		final int code;

		Entity(int code, int limit, int capacity) {
			this.code = code;
			this.limit = limit;
			this.capacity = capacity;
		}

		int getNo() {
			return deep % limit;
		}

		@Override
		public String toString() {

			return "Code:" + code + ",No:" + getNo() + ",Deep:" + deep + ",Capacity:" + capacity + ",Limit:" + limit;
		}
	}

	interface Receiver {

		<T> T accept();

		<T> List<T> accept(int index);

		static Receiver generate(final Assign assign) {

			return new Receiver() {

				Assign assign;

				Entity entity;

				Container<Object> container = null;

				Receiver init(final Assign assign) {
					this.assign = assign;
					this.entity = new Entity(1, this.assign.getStateCount(), this.assign.getCapacity());
					return this;
				}

				@SuppressWarnings("unchecked")
				@Override
				public <T> T accept() {
					resetContainer(false);
					return (T) Optional.ofNullable(check()).map(c -> c.dump()).orElse(null);
				}

				Container<Object> check() {
					if (container.getCount() == 0) {
						assign.send(entity.getNo());
						entity.deep++;
						resetContainer(true);
					}
					return container;
				}

				void resetContainer(boolean isForce) {
					if (isForce) {
						container = assign.waitOn(entity.getNo(), entity.code);
					} else {
						if (container == null) {
							container = assign.waitOn(entity.getNo(), entity.code);
						}
					}
				}

				@Override
				public <T> List<T> accept(int index) {
					return null;
				}

			}.init(assign);
		}
	}

	interface Generator {

		<T> Integer accept(T value);

		void ending();

		void recovery();

		static Generator generate(final Assign assign) {
			return new Generator() {

				Assign assign;

				Entity entity;

				Container<Object> container = null;

				Generator init(final Assign assign) {
					this.assign = assign;
					this.entity = new Entity(0, this.assign.getStateCount(), this.assign.getCapacity());
					return this;
				}

				@Override
				public <T> Integer accept(T value) {

					if (getContainerFlag())
						return -2;

					if (container.getStock() <= 0) {
						assign.send(entity.getNo());
						entity.deep++;

						if (regetContainerFlag())
							return -2;

						while (container.getCount() != 0) {
							Thread.yield();
						}
					}
					container.append(value);
					return container.getCount();
				}

				boolean getContainerFlag() {
					return (container == null) ? regetContainerFlag() : container.isFinished();
				}

				boolean regetContainerFlag() {
					this.container = assign.waitOn(entity.getNo(), entity.code);

					return Optional.ofNullable(this.container).map(c -> c.isFinished()).orElse(true);
				}

				@Override
				public void ending() {
					assign.stop(entity.getNo());
				}

				@Override
				public void recovery() {
					assign.recovery();

				}

			}.init(assign);
		}
	}

	static final int DefContainerCapacity = 32768;

	interface Container<T> {

		int getCount();

		Container<T> append(T value);

		T dump(int index);

		T dump();

		int getCapacity();

		int getStock();

		void finished();

		boolean isFinished();

		void recovery();

		static public <V> Container<V> generate(int capacity) {
			return new Impl<>(capacity);
		}

		final class Impl<T> implements Container<T> {

			final Object[] arrays;
			final int capacity;

			AtomicBoolean isEnding = new AtomicBoolean(false);
			int _index = 0;

			Impl(int capacity) {
				this.capacity = capacity;
				this.arrays = new Object[this.capacity];
			}

			@Override
			public int getCount() {
				return _index;
			}

			@Override
			public Container<T> append(T value) {
				if (!isFinished()) {
					this.arrays[_index++] = value;
				}
				return this;
			}

			@SuppressWarnings("unchecked")
			@Override
			public T dump(int index) {
				_index--;
				return (T) (index < 0 ? null : arrays[index]);
			}

			@Override
			public int getCapacity() {
				return this.capacity;
			}

			@Override
			public int getStock() {
				return getCapacity() - getCount();
			}

			@Override
			public T dump() {
				return dump(getCount() - 1);
			}

			@Override
			public void finished() {
				this.isEnding.lazySet(true);
				for (int i = getCount(); i < getCapacity(); i++) {
					this.append(null);
				}
			}

			@Override
			public boolean isFinished() {
				return isEnding.get() && getStock() == 0;
			}

			@Override
			public void recovery() {
				// --------
				isEnding.lazySet(false);
			}

		}
	}

	interface Assign {

		<T> Container<T> waitOn(final int containerNo, final int state);

		void send(final int containerNo);

		int getStateCount();

		int getCapacity();

		void stop(final int containerNo);

		void recovery();

		static public Assign generateDoubleState(int capacity) {
			return new DoubleStateImpl(capacity);
		};

		final class DoubleStateImpl implements Assign {

			final int stateCount = 2;

			final Container<?>[] containers;

			final AtomicInteger[] states = new AtomicInteger[] {

					new AtomicInteger(0),

					new AtomicInteger(0)

			};

			int stopToNo = -1;

			DoubleStateImpl(int capacity) {
				this.containers = new Container[] { Container.generate(capacity), Container.generate(capacity) };
			}

			@Override
			public int getCapacity() {
				return containers[0].getCapacity();
			}

			@Override
			public <T> Container<T> waitOn(int containerNo, int state) {
				Container<T> c = getContainer(containerNo);
				while (states[containerNo].get() % getStateCount() != state) {
					if (stopToNo == containerNo) {
						return null;
					}
					Thread.yield();
				}

				return c;
			}

			@Override
			public void send(int containerNo) {
				states[containerNo].getAndIncrement();
			}

			@SuppressWarnings("unchecked")
			<T> Container<T> getContainer(int no) {
				return (Container<T>) containers[no];
			}

			@Override
			public int getStateCount() {
				return stateCount;
			}

			@Override
			public void stop(final int containerNo) {
				this.stopToNo = containerNo;
				getContainer(containerNo).finished();
			}

			@Override
			public void recovery() {
				this.stopToNo = -1;

				for (Container<?> c : containers) {
					c.recovery();
				}
			}

		}
	}
}
