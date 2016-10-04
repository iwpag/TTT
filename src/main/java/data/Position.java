package data;

/**
 * @author nilstes
 */
public class Position {
    int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
      
    public static Position at(int x, int y) {
        return new Position(x, y);
    }

    public int x() {
        return x;
    }
    
    public int y() {
        return y;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
}
