import java.awt.Point;
public class Cell {
public int x, y;
public Cell parent;

public Cell(Point p, Cell _parent) {
   x = p.x;
   y = p.y;
   parent = _parent;
}

public Cell(int x, int y, Cell _parent) {
   this(new Point(x,y), _parent);
}


public Cell copy() {
   return new Cell(x, y, parent);
}

public Point getPoint() { return new Point(x,y); }

}