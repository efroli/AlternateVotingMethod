package p1_4020_802135440_192;

public class LinkedListFactory<E> implements ListFactory<E> {

	@Override
	public List<E> newInstance(int initialCapacity) {
		// We don't pre-allocate nodes, so initialCapacity isn't actually used
		return new LinkedList<E>();
	}

	@Override
	public List<E> newInstance() {
		return new LinkedList<E>();
	}

}
