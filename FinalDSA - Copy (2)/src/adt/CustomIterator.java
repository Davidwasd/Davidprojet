/*
Author: Tneh Kai Qing
*/

package adt;

public interface CustomIterator<T> {
    boolean hasNext();
    T next();
    void remove();
}

