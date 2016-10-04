package controller;

import data.Direction;
import data.Position;

/**
 * @author nilstes
 */
public class Find {
    private int index;
    private Direction dir;
    private Position position;

    Find(Direction direction, int index, Position position) {
        this.dir = direction;
        this.index = index;
        this.position = position;
    }

    public int getIndex() {
        return index;
    }

    public Direction getDirection() {
        return dir;
    }

    public Position getPosition() {
        return position;
    }
    
    
}
