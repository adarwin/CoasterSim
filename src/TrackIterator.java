package com.adarwin.coaster;

import java.util.Iterator;

public class TrackIterator implements Iterator<RailSection> {
    Track track;
    int x = 0, y = 0;

    public TrackIterator(Track track) {
        this.track = track;
    }

    public boolean hasNext() {
        return false;
    }

    public RailSection next() {
        if (x < 3 && y == 0) {
            x++;
        } else if (x == 3 && y < 3) {
            y++;
        } else if (x > 0 && y == 3) {
        } else if (x == 0 && y > 0) {
        }
        return null;
    }

    public void remove() {
    }
}
