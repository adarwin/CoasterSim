package com.adarwin.coaster;

public class LinkedPoint<T> {
    final T item;
    final protected int x, y;
    final boolean isStartingPoint;
    volatile protected LinkedPoint<T> previous, next;

    public LinkedPoint(T item, int x, int y, boolean isStartingPoint) {
        //this(item);
        this.item = item;
        this.x = x;
        this.y = y;
        this.isStartingPoint = isStartingPoint;
        previous = null;
        next = null;
    }
    public LinkedPoint(T item, T previous, T next) {
        this(item);
        this.previous = new LinkedPoint<T>(previous);
        this.next = new LinkedPoint<T>(next);
    }
    private LinkedPoint(T item) {
        this(item, 0, 0, false);
    }

    /*
    public void setStartingPoint(boolean startingPoint) {
        this.startingPoint = startingPoint;
    }
    */
    public boolean isStartingPoint() { return isStartingPoint; }
    /*
    public void add(LinkedPoint<T> newLinkedPoint) {
        if (next == null) {
            setNext(newLinkedPoint);
            newLinkedPoint.setPrevious(this);
        } else {
            next.add(newLinkedPoint);
        }
    }
    */
    /*
    public void add(T newItem) {
        add(newItem, 0, 0);
    }
    */

    public synchronized LinkedPoint<T> add(T newItem, int x, int y) {
        if (next == null) {
            LinkedPoint<T> newLinkedPoint = new LinkedPoint<T>(newItem, x, y,
                                                               false);
            setNext(newLinkedPoint);
            newLinkedPoint.setPrevious(this);
            return newLinkedPoint;
        } else {
            return next.add(newItem, x, y);
        }
    }

    public synchronized void addFinalLink(T newItem, int x, int y) {
        LinkedPoint<T> newPoint = add(newItem, x, y);
        newPoint.setNext(this);
        setPrevious(newPoint);
    }


    public LinkedPoint<T> getNext() { return next; }

    public LinkedPoint<T> getPrevious() { return previous; }

    private void setPrevious(LinkedPoint<T> previous) {
        this.previous = previous;
    }
    private void setNext(LinkedPoint<T> next) {
        this.next = next;
    }
}
