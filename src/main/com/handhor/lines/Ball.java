import java.awt.*;

public class Ball extends Point {

public Color color;
public boolean alive, jumping;
public int df, radius, radius1;

public Ball(int x, int y, Color _color) {
   super(x,y);
   df = 0;
   alive = true;
   radius = 14;
   radius1 = 0;
   color = _color;  
}

public Point getPoint() {
   return new Point(x,y);
}

public void draw(Graphics g) {
   g.setColor(Color.lightGray);
   g.fillRect(x*40+3, 40+40*y+3, 34, 34);
   g.setColor(color);
   g.fillOval(40*x+6, 40+40*y+6, 28, 28);
   g.setColor(Color.black);
   g.drawOval(40*x+6, 40+40*y+6, 28, 28);
}


public void redraw(Graphics g) {
   g.setColor(Color.lightGray);
   g.fillRect(40*x+3, 40+40*y+3, 34, 34);
   if(jumping) {
      df = -1*df + 1;
      if(df==1) {
         g.setColor(color);
         g.fillOval(40*x+5, 40+40*y+8, 30, 26);
         g.setColor(Color.black);
         g.drawOval(40*x+5, 40+40*y+8, 30, 26);         
      }
      else draw(g);
   }
   else draw(g);
}

public void erase(Graphics g) {
    g.setColor(Color.lightGray);
   g.fillRect(40*x+3, 40+40*y+3, 34, 34);   
}


public void decrement(Graphics g) {
   radius--;
   g.setColor(Color.lightGray);
   g.fillRect(40*x+3, 40+40*y+3, 34, 34);
   g.setColor(color);
   g.fillOval(40*x+20-radius, 40+40*y+ 20-radius, 2*radius, 2*radius);
   g.setColor(Color.black);
   g.drawOval(40*x+ 20-radius, 40+40*y+ 20-radius, 2*radius, 2*radius); 
}

public void increment(Graphics g) {
   radius1++;
   g.setColor(Color.lightGray);
   g.fillRect(40*x+3, 40+40*y+3, 34, 34);
   g.setColor(color);
   g.fillOval(40*x+20-radius1, 40+40*y+ 20-radius1, 2*radius1, 2*radius1);
   g.setColor(Color.black);
   g.drawOval(40*x+ 20-radius1, 40+40*y+ 20-radius1, 2*radius1, 2*radius1); 
}



}
   
