package adt;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

public class CustomLinkedList<T> implements Iterable<T> {

    private Node<T> head;

    public void add(T value) {
        // Implement add logic
        Node<T> newNode = new Node<>(value);
        newNode.next = head;
        head = newNode;
    }

//    public CustomIterator<T> customIterator() {
//        return new CustomLinkedListIterator();
//    }

    public Node<T> getHead() {
        return head;
    }

    public void setHead(Node<T> newHead) {
        head = newHead;
    }

//    public CustomIterator<T> CustomIterator() {
//        return new CustomLinkedListIterator();
//    }

    public boolean isEmpty() {
        return head == null; // The list is empty if the head node is null
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomLinkedListIterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public Spliterator<T> spliterator() {
        return Iterable.super.spliterator(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

//    public CustomLinkedList<T> values() {
//        CustomLinkedList<T> valuesList = new CustomLinkedList<>();
//
//        Node<T> currentNode = head;  // Start from the head of the list
//        while (currentNode != null) {
//            valuesList.add(currentNode.getData()); // Add the data to the valuesList
//            currentNode = currentNode.getNextNode(); // Move to the next node
//        }
//
//        return valuesList;
//    }

    public static class Node<T> {

        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }

        public T getData() {
            return data;
        }

        public Node<T> getNextNode() {
            return next;
        }

        public void setNextNode(Node<T> nextNode) {
            next = nextNode;
        }
    }

    private class CustomLinkedListIterator implements Iterator<T> {

        private Node<T> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T data = current.getData();
            current = current.getNextNode();
            return data;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

    }
}
