package com.adarwin.coaster;

public class LinkedPoint<T> {
    T item;
    protected int x, y;
    boolean startingPoint;
    protected LinkedPoint<T> previous, next;
    public LinkedPoint(T item) {
        this.item = item;
        previous = null;
        next = null;
    }
    public LinkedPoint(T item, int x, int y) {
        this(item);
        setCoordinates(x, y);
    }
    public LinkedPoint(T item, T previous, T next) {
        this(item);
        this.previous = new LinkedPoint<T>(previous);
        this.next = new LinkedPoint<T>(next);
    }
    public void setStartingPoint(boolean startingPoint) {
        this.startingPoint = startingPoint;
    }
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean isStartingPoint() { return startingPoint; }
    public void add(LinkedPoint<T> newLinkedPoint) {
        if (next == null) {
            setNext(newLinkedPoint);
            newLinkedPoint.setPrevious(this);
        } else {
            next.add(newLinkedPoint);
        }
    }
    public void add(T newItem) {
        add(newItem, 0, 0);
    }
    public LinkedPoint<T> add(T newItem, int x, int y) {
        if (next == null) {
            LinkedPoint<T> newLinkedPoint = new LinkedPoint<T>(newItem, x, y);
            setNext(newLinkedPoint);
            newLinkedPoint.setPrevious(this);
            return newLinkedPoint;
        } else {
            return next.add(newItem, x, y);
        }
    }
    public void addFinalLink(T newItem, int x, int y) {
        LinkedPoint<T> newPoint = add(newItem, x, y);
        newPoint.setNext(this);
        setPrevious(newPoint);
    }
    public void setNext(LinkedPoint<T> next) {
        this.next = next;
    }
    public LinkedPoint<T> getNext() { return next; }
    public void setPrevious(LinkedPoint<T> previous) {
        this.previous = previous;
    }
    public LinkedPoint<T> getPrevious() { return previous; }
}
