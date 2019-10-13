package ru.otus.hw02arraylist;

import java.util.*;
import java.util.function.UnaryOperator;
import java.lang.UnsupportedOperationException;
import java.lang.IndexOutOfBoundsException;
import java.util.Iterator;

public class DIYArrayList<T> implements List<T> {

    private static final int DEFAULT_CAPACITY = 5;

    private Object[] arrayOfElements;

    private int size;

    public DIYArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public DIYArrayList(int initialCapacity) {

        if (initialCapacity > 0) {
            this.arrayOfElements = new Object[initialCapacity];
        } else {
            this.arrayOfElements = new Object[DEFAULT_CAPACITY];
        }
    }

    private void indxCheck(int index) {

        if (index < 0 || index > size )
            throw new IndexOutOfBoundsException(index);
    }

    private void grow() {

        final Object[] oldArray = arrayOfElements;
        arrayOfElements = new Object[(size * 3) >>> 1 ];

        System.arraycopy(oldArray, 0,
                         arrayOfElements,0, oldArray.length);
    }

    private void grow(int capacity) {

        final Object[] oldArray = arrayOfElements;
        arrayOfElements = new Object[capacity];

        System.arraycopy(oldArray, 0,
                arrayOfElements,0, oldArray.length);
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sort(Comparator<? super T> c)  {
        Arrays.sort((T[]) arrayOfElements, 0, size, c);
    }

    @Override
    public Spliterator<T> spliterator() throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() { return this.size; }

    @Override
    public boolean isEmpty() { return this.size == 0; }

    @Override
    public boolean contains(Object o) {

        for (int i = 0 ; i < size; i++) {
            if (o.equals(arrayOfElements[i])){ return true; }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() { return new ElementIteratror(); }

    @Override
    public Object[] toArray() {

        final Object[] newArray = new Object[this.size];
        System.arraycopy(arrayOfElements, 0, newArray, 0 , this.size);
        return newArray;
    }

    @Override
    public <T> T[] toArray(T[] a) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T element) {

        if (arrayOfElements.length == size) {
            grow();
        }

        arrayOfElements[size++] = element;
        return true;
    }

    @Override
    public void add(int index, T element) {

        indxCheck(index);

        if (arrayOfElements.length == size) {
            grow();
        }

        arrayOfElements[index] = element;
        size++;
    }

    @Override
    public boolean remove(Object o) {

        for (int i = 0 ; i < this.size; i++) {
            if (o.equals(arrayOfElements[i])) {
                this.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {

        for (Object o: c){
            if (!this.contains(o)) { return false; }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {

        for (T item: c ) {
            this.add(item);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {

        final Object[] inputArray = c.toArray();

        int arrayLen = inputArray.length;

        if (arrayLen >= (arrayOfElements.length - size)) {
            grow(size + arrayLen);
        }

        if (index > 0) {
            System.arraycopy(arrayOfElements, index,
                             arrayOfElements, index + arrayLen,
                        this.size - index);
        }

        System.arraycopy(inputArray, 0,
                         arrayOfElements, index, arrayLen);
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {

       final Object[] inputArray = c.toArray();

        for (final Object o: inputArray ) {
            this.remove(o);
        }

        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {

        arrayOfElements = new Object[1];
        size = 0;
    }

    @Override
    public T get(int index) {

        indxCheck(index);

        return (T) arrayOfElements[index];

    }

    @Override
    public T set(int index, T element) {

        indxCheck(index);

        arrayOfElements[index] = element;
        return (T) arrayOfElements[index];
    }

    @Override
    public T remove(int index) {

        indxCheck(index);

        if (size == 1) {

            return (T) arrayOfElements[size--];

        } else {

            final Object oldElement = arrayOfElements[index];

            System.arraycopy(arrayOfElements, index + 1,
                    arrayOfElements, index, this.size - index - 1);
            size--;

            return (T) oldElement;
        }
    }

    @Override
    public int indexOf(Object o) {


        for(int i = 0; i < this.size; i++) {
            if ((o == null && this.get(i) == null) || this.get(i).equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {

        for (int i = this.size-1; i != 0; i--) {
            if ((o == null && this.get(i) == null) || this.get(i).equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public ListIterator<T> listIterator()  {
        return new ListItr(0);
    }

    @Override
    public ListIterator<T> listIterator(int index){
        return new ListItr(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    private class ElementIteratror implements Iterator<T> {

        int retIndex = -1;
        int cursor;

        @Override
        public boolean hasNext() { return DIYArrayList.this.size > cursor; }

        @Override
        public T next() {

            int index = cursor;

            indxCheck(index);

            retIndex = index;
            cursor++;
            return (T) DIYArrayList.this.arrayOfElements[retIndex];
        }
    }

    private class ListItr extends ElementIteratror implements ListIterator<T> {


        ListItr(int index) {
            this.cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return this.cursor != 0;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T previous() {

            final Object[] iterData = DIYArrayList.this.arrayOfElements;

            int index = cursor - 1;

            if (index < 0 || cursor >= iterData.length ) throw new NoSuchElementException();

            cursor = index;
            retIndex = index;
            return (T) iterData[retIndex];
        }

        @Override
        public int nextIndex() { return this.cursor; }

        @Override
        public int previousIndex() { return this.cursor - 1; }

        @Override
        public void remove() throws UnsupportedOperationException{
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(T o) {

            if (retIndex < 0) throw new IllegalStateException();

            try {
                DIYArrayList.this.set(retIndex, o);
            } catch (IndexOutOfBoundsException ex) {
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public void add(T o) {

            try {
                int index = this.cursor;
                this.cursor = index + 1;
                DIYArrayList.this.add(index, o);
                retIndex= -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new IndexOutOfBoundsException();
            }

        }
    }
}
