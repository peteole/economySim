package multipleLayerLinkedSorting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.Queue;

public class LinkedOrderedList<T extends SortValueGetter> implements Queue<T> {
	private long orderSteps = 0;
	private ArrayList<Double> avStepsOnLevel = new ArrayList<Double>();
	private ArrayList<Double> integral = new ArrayList<Double>();
	private int size = 0;
	private boolean countSteps = false;
	private int[] counter = new int[0];
	private SortableContainer<T> firstSortable = new SortableContainer<T>(null,Double.NEGATIVE_INFINITY);
	private SortableContainer<T> lastSortable = new SortableContainer<T>(null, Double.POSITIVE_INFINITY);
	public LinkedOrderedList() {
		firstSortable.addNextSortable(lastSortable);

		avStepsOnLevel.add(2.0);
		integral.add(0.0);
	}

	public void printStructure() {
		SortableContainer<T> current = firstSortable;
		for (int i = 0; i < size + 1; i++) {
			int level = current.getNextSortable().length;
			for (int j = 0; j < level; j++) {
				System.out.print("________|");
			}
			System.out.println(current.sortValue);
			current = current.getNextSortable(0);
		}
		
		for (int i = 0; i < getAvStepsOnLevel().size(); i++) {
			System.out.println("Level" + i + ":" + getAvStepsOnLevel().get(i) + "average steps,  Anzahl einsortierter:"
					+ getSteps(i));
		}
	}

	public ArrayList<Double> getAvStepsOnLevel() {
		return this.avStepsOnLevel;
	}

	public void setSortValue(T toChange) {
		remove(toChange);
		add(toChange);
	}

	public T poll() {
		size--;
		T toReturn = firstSortable.getNextSortable(0).object;
		System.arraycopy(firstSortable.getNextSortable(0).getNextSortable(), 0, firstSortable.getNextSortable(), 0,
				firstSortable.getNextSortable(0).getNextSortable().length);
		return toReturn;
	}
	public boolean add(T toAdd) {
		SortableContainer<T> toAddContainer=new SortableContainer<T>(toAdd,toAdd.getSortValue());
		size++;
		countSteps = false;
		int level = 0;
		updateLevels();
		if (counter.length > 0) {
			counter[0]++;
			for (; level < counter.length && counter[level] > getSteps(level); level++) {
				counter[level] = 0;
				if (counter.length > level + 1) {
					counter[level + 1]++;
				}
			}
		}
		if (level >= firstSortable.getNextSortable().length / 2) {
			countSteps = true;
		}
		SortableContainer<T> testSortable = firstSortable;
		toAddContainer.setNextSortable(new SortableContainer[level + 1]);
		int startLevel = firstSortable.getNextSortable().length - 1;
		while (level >= 0) {
			testSortable = goToPoint(toAddContainer, testSortable, level, startLevel);
			toAddContainer.setNextSortable(level, testSortable.getNextSortable(level));
			testSortable.setNextSortable(level, toAddContainer);
			level--;
			startLevel = level;
		}
		return true;
	}

	/**
	 * @param einzusortierender Sortable
	 * @param Startpunkt        zum Einsortieren
	 * @param Level,            auf dem "einzusortieren" einsortiert werden soll
	 * @param startLevel
	 * @return Sortable aus dem Level level mit dem groessten sortValue, der kleiner
	 *         als der von "einzusortiere" ist
	 */
	private SortableContainer<T> goToPoint(SortableContainer<T> einzusortieren, SortableContainer<T> start, int level, int startLevel) {
		final double toGet = einzusortieren.getSortValue();
		while (startLevel >= level) {
			if (countSteps) {
				int steps = 0;
				SortableContainer<T> nextSortable = start.getNextSortable(startLevel);
				while (nextSortable.getSortValue() <= toGet) {
					start = nextSortable;
					nextSortable = start.getNextSortable(startLevel);
					steps++;
					orderSteps++;
				}
				avStepsOnLevel.set(startLevel,
						(steps - avStepsOnLevel.get(startLevel)) * 0.1 + avStepsOnLevel.get(startLevel));

			} else {
				SortableContainer<T> nextSortable = start.getNextSortable(startLevel);
				while (nextSortable.getSortValue() <= toGet) {
					start = nextSortable;
					nextSortable = start.getNextSortable(startLevel);
					orderSteps++;
				}
			}
			startLevel--;
		}
		return start;
	}

	public long getOrderSteps() {
		return orderSteps;
	}

	public int getSteps(int level) {
		return ((int) (5.0 / avStepsOnLevel.get(level)));

	}

	private void updateLevels() {
		double maxlevelSteps = avStepsOnLevel.get(avStepsOnLevel.size() - 1);
		if (maxlevelSteps > 5) {
			addLevel();
		} else if (maxlevelSteps < 0.01) {
			// removeLevel();
		}
	}

	private void addLevel() {
		integral.add(0.0);
		avStepsOnLevel.add(5.0);
		int[] newCounter = new int[counter.length + 1];
		for (int i = 0; i < counter.length; i++) {
			newCounter[i] = counter[i];
		}
		newCounter[counter.length] = 8;
		counter = newCounter;
		firstSortable.addNextSortable(lastSortable);
	}

	private void removeLevel() {
		if (counter.length < 2) {
			return;
		}
		int[] newCounter = new int[counter.length - 1];
		for (int i = 0; i < counter.length - 1; i++) {
			newCounter[i] = counter[i];
		}
		counter = newCounter;
		// firstSortable.removeLastSortable();;
		// avStepsOnLevel.remove(counter.length);
	}

	@Override
	public boolean addAll(Collection<? extends T> arg0) {
		arg0.forEach(new Consumer<T>() {
			public void accept(T arg0) {
				add(arg0);
			}
		});
		return false;
	}

	@Override
	public void clear() {
		size=0;
		firstSortable.setNextSortable(new SortableContainer[]{lastSortable});
	}

	@Override
	public boolean contains(Object arg0) {
		SortableContainer<T> current = firstSortable.getNextSortable(0);
		for (int i = 0; i < size; i++) {
			if (current == arg0) {
				return true;
			} else {
				current = current.getNextSortable(0);
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		for (Iterator iterator = arg0.iterator(); iterator.hasNext();) {
			Object object = iterator.next();
			if (!contains(object)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size == 0;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		Iterator<T> toReturn = new Iterator<T>() {
			private SortableContainer<T> current =firstSortable;

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return current.getNextSortable(0) == lastSortable;
			}

			@Override
			public T next() {
				// TODO Auto-generated method stub
				current =  current.getNextSortable(0);
				return current.object;
			}
		};
		return toReturn;
	}

	@Override
	public boolean removeAll(Collection arg0) {
		boolean containsAll = true;
		for (Iterator iterator = arg0.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if (!remove(object)) {
				containsAll = false;
			}
		}
		return containsAll;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		Object[] toReturn = new Object[size];
		SortableContainer<T> current = firstSortable;
		for (int i = 0; i < size; i++) {
			current = current.getNextSortable(0);
			toReturn[i] = current.object;
		}
		return toReturn;
	}

	@Override
	public <T> T[] toArray(T[] toReturn) {
		// TODO Auto-generated method stubSortable current=firstSortable;
		SortableContainer<T> current = (SortableContainer<T>) firstSortable;
		for (int i = 0; i < size; i++) {
			current = current.getNextSortable(0);
			toReturn[i] = current.object;
		}
		return toReturn;
	}

	@Override
	public T element() {
		// TODO Auto-generated method stub
		return (T) firstSortable.getNextSortable(0);
	}

	@Override
	public boolean offer(T arg0) {
		// TODO Auto-generated method stub
		add(arg0);
		return true;
	}

	@Override
	public T peek() {
		// TODO Auto-generated method stub
		return firstSortable.getNextSortable(0).object;
	}

	@Override
	public T remove() {
		// TODO Auto-generated method stub
		return poll();
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return remove(arg0);
	}
}
