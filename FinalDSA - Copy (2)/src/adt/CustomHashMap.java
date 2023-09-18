package adt;

import java.util.NoSuchElementException;
import java.util.Iterator;

public class CustomHashMap<K, T> implements CustomHashMapInterface<K,T>{

    private static final int INITIAL_CAPACITY = 16;
    private CustomLinkedList<Entry<K, T>>[] buckets;
    

    public CustomHashMap() {
        buckets = new CustomLinkedList[INITIAL_CAPACITY];
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            buckets[i] = new CustomLinkedList<>(); // Initialize each bucket with a new linked list
        }
    }

    public T getValueAt(int index) {
        int count = 0;
        for (CustomLinkedList<Entry<K, T>> bucket : buckets) {
            CustomLinkedList.Node<Entry<K, T>> current = bucket.getHead();
            while (current != null) {
                if (count == index) {
                    Entry<K, T> entry = current.getData();
                    return entry.value;
                }
                current = current.getNextNode();
                count++;
            }
        }
        return null; // Index out of bounds or value not found
    }

    public void put(K key, T value) {
        int index = getIndex(key);
        CustomLinkedList<Entry<K, T>> entryList = buckets[index];

        // Check if the key already exists, and if so, update the value
        CustomLinkedList.Node<Entry<K, T>> currentNode = entryList.getHead();
        while (currentNode != null) {
            Entry<K, T> entry = currentNode.getData();
            if (entry.getKey().equals(key)) {
                entry.value = value; // Update the existing value
                return;
            }
            currentNode = currentNode.getNextNode();
        }

        // If key doesn't exist, add a new entry to the list
        Entry<K, T> newEntry = new Entry<>(key, value);
        entryList.add(newEntry);
    }

    public T get(K key) {
        int index = getIndex(key);
        CustomLinkedList<Entry<K, T>> entryList = buckets[index];

        CustomLinkedList.Node<Entry<K, T>> currentNode = entryList.getHead();
        while (currentNode != null) {
            Entry<K, T> entry = currentNode.getData();
            if (entry.getKey().equals(key)) {
                return entry.value;
            }
            currentNode = currentNode.getNextNode();
        }

        return null; // Key not found
    }

    public CustomLinkedList<T> values() {
        CustomLinkedList<T> valuesList = new CustomLinkedList<>();

        for (CustomLinkedList<Entry<K, T>> bucket : buckets) {
            CustomLinkedList.Node<Entry<K, T>> current = bucket.getHead();
            while (current != null) {
                Entry<K, T> entry = current.getData();
                valuesList.add(entry.value);
                current = current.getNextNode();
            }
        }

        return valuesList;
    }

    public CustomLinkedList<K> keys() {
        CustomLinkedList<K> keysList = new CustomLinkedList<>();

        for (CustomLinkedList<Entry<K, T>> bucket : buckets) {
            CustomLinkedList.Node<Entry<K, T>> current = bucket.getHead();
            while (current != null) {
                Entry<K, T> entry = current.getData();
                keysList.add(entry.key);
                current = current.getNextNode();
            }
        }

        return keysList;
    }

    public void remove(K key) {
        int index = getIndex(key);
        CustomLinkedList<Entry<K, T>> entryList = buckets[index];

        CustomLinkedList.Node<Entry<K, T>> previousNode = null;
        CustomLinkedList.Node<Entry<K, T>> currentNode = entryList.getHead();
        while (currentNode != null) {
            Entry<K, T> entry = currentNode.getData();
            if (entry.getKey().equals(key)) {
                if (previousNode == null) {
                    entryList.setHead(currentNode.getNextNode());
                } else {
                    previousNode.setNextNode(currentNode.getNextNode());
                }
                return; // Key removed
            }
            previousNode = currentNode;
            currentNode = currentNode.getNextNode();
        }
    }

    public boolean isEmpty() {
        for (CustomLinkedList<Entry<K, T>> bucket : buckets) {
            if (bucket != null && !bucket.isEmpty()) {
                return false; // If any non-empty bucket is found, the map is not empty
            }
        }
        return true; // If no non-empty buckets are found, the map is empty
    }

    public int size() {
        int count = 0;
        for (CustomLinkedList<Entry<K, T>> bucket : buckets) {
            if (bucket != null) {
                CustomLinkedList.Node<Entry<K, T>> current = bucket.getHead();
                while (current != null) {
                    count++;
                    current = current.getNextNode();
                }
            }
        }
        return count;
    }

    public boolean containsKey(K key) {
        int index = getIndex(key);
        CustomLinkedList<Entry<K, T>> entryList = buckets[index];

        CustomLinkedList.Node<Entry<K, T>> currentEntry = entryList.getHead();
        while (currentEntry != null) {
            Entry<K, T> entry = currentEntry.getData();
            if (entry.getKey().equals(key)) {
                return true;
            }
            currentEntry = currentEntry.getNextNode();
        }

        return false;
    }

    private int getIndex(K key) {
        int hashCode = key.hashCode();
        int index = hashCode % buckets.length;
        return index < 0 ? index + buckets.length : index;
    }

    // Inner class to represent entries in the hash map
    public static class Entry<K, T> {

        K key;
        T value;

        public Entry(K key, T value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }
    }

    // Custom iterator for CustomHashMap
    private class CustomHashMapIterator implements Iterator<Entry<K, T>> {

        private int currentBucket = 0;
        private CustomLinkedList.Node<Entry<K, T>> currentNode = null;

        public CustomHashMapIterator() {
            // Find the first non-empty bucket
            while (currentBucket < buckets.length && (buckets[currentBucket] == null || buckets[currentBucket].isEmpty())) {
                currentBucket++;
            }

            // If there's a non-empty bucket, initialize the currentNode
            if (currentBucket < buckets.length) {
                currentNode = buckets[currentBucket].getHead();
            }
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Entry<K, T> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Entry<K, T> entry = currentNode.getData();
            currentNode = currentNode.getNextNode();

            // If we've reached the end of the current bucket, move to the next non-empty bucket
            if (currentNode == null) {
                currentBucket++;
                while (currentBucket < buckets.length && (buckets[currentBucket] == null || buckets[currentBucket].isEmpty())) {
                    currentBucket++;
                }
                if (currentBucket < buckets.length) {
                    currentNode = buckets[currentBucket].getHead();
                }
            }

            return entry;
        }

        public T get(K key) {
            int index = getIndex(key);
            CustomLinkedList<Entry<K, T>> entryList = buckets[index];

            CustomLinkedList.Node<Entry<K, T>> currentNode = entryList.getHead();
            while (currentNode != null) {
                Entry<K, T> entry = currentNode.getData();
                if (entry.getKey().equals(key)) {
                    return entry.value;
                }
                currentNode = currentNode.getNextNode();
            }

            return null; // Key not found
        }
       
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
        
    }
}
