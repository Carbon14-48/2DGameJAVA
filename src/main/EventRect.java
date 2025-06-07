package main;


import java.awt.Rectangle;

public class EventRect extends Rectangle {
    int eventRectDefaultX, eventRectDefaultY;
    boolean eventDone = false;
    
    public EventRect() {
        this.eventRectDefaultX = 23;
        this.eventRectDefaultY = 23;
        this.x = eventRectDefaultX;
        this.y = eventRectDefaultY;
        this.width =48;   // Full tile size (gp.tileSize)
    this.height = 48;
    }
}

