/*
Author: Oo Kai Jie
*/

package adt;

public class CircularArrayQueue<T> implements QueueInterface<T>{

    private T[] array;
    private int frontIndex;
    private int backIndex;
    private int numberOfElements;
    private static final int DEFAULT_CAPACITY = 5;
    
    public CircularArrayQueue(){
        this(DEFAULT_CAPACITY);
    }
    
    public CircularArrayQueue(int initialCapacity){
        array = (T[]) new Object[initialCapacity + 1];
        frontIndex = 0;
        backIndex = initialCapacity;
    }    

    @Override
    public void enqueue(T newEntry) {
        if(!isFull()){
            backIndex = (backIndex + 1) % array.length;
            array[backIndex] = newEntry;
            numberOfElements++;
            
        }
    }
    
    @Override
    public T dequeue() {
        if (!isEmpty()) {
            T front = array[frontIndex];
            array[frontIndex] = null;
            frontIndex = (frontIndex + 1) % array.length;
            numberOfElements--;
            return front;
        } else {
            return null; // Or throw an exception, depending on your preference
        }
    }
    
    @Override
    public T peek() {
        if (!isEmpty()) {
            return array[frontIndex];
        } else {
            return null; // Or throw an exception, depending on your preference
        }
    }
    

    @Override
    public T getFront() {
        T front = null;
        
        if(!isEmpty()){
            front = array[frontIndex];
        }
        return front;
    }
    
    @Override
    public T getEntry(int givenPosition){
        T test = null;
        return test;
    }

    @Override
    public boolean isEmpty() {
        return frontIndex == ((backIndex + 1) % array.length);
    }

    @Override
    public void clear() {
        if(!isEmpty()){
            for(int index = frontIndex; index != backIndex; index = (index + 1) % array.length){
                array[index] = null;
            }
            array[backIndex] = null;
        }
        frontIndex = 0;
        backIndex = array.length - 1;
    }
    
    public int size(){
        return numberOfElements;
    }
    
    @Override
    public Iterator<T> getIterator() {
        return new IteratorForCircularArrayQueue();
    }
    
    private class IteratorForCircularArrayQueue implements Iterator<T>{
        private int index;

        public IteratorForCircularArrayQueue(){
            index = 0;
        }
        
        @Override
        public boolean hasNext() {
            return index < numberOfElements;
        }

        @Override
        public T next() {
            if(hasNext()){
                T element = (T) array[index++];
                return element;
            }else{
                return null;
            }
        }
    }
    
    
    public boolean isFull() {
        // Calculate the next index to where the backIndex would be if it wraps around
        int nextIndex = (backIndex + 1) % array.length;

        // If the next index is equal to the frontIndex, and there are no elements,
        // then the queue is full
        return nextIndex == frontIndex && numberOfElements > 0;
    }    

    
}
