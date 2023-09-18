/*
Author: Oo Kai Jie
*/

package adt;

public interface QueueInterface<T> {
    void enqueue(T newEntry);
    T dequeue();
    T peek();
    public T getFront(); // retrieve entry at the front without removing it
    public boolean isEmpty(); 
    public void clear();
    public Iterator<T> getIterator(); 
    public T getEntry(int entry);
}
