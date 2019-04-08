import java.awt.*;
import java.util.Random;

public class EnemyBossBullet extends GameObject {

    Random r = new Random();

    private Handler handler;

    public EnemyBossBullet(float x, float y, ID id, Handler handler) {
        super(x, y, id);

        this.handler = handler;

        velX = (r.nextInt(5 - -5 )+ -5);
        velY = 5;
    }
    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y, 10,10);
    }


    public void tick() {
        x+=velX;
        y+=velY;

        if(y>Game.HEIGHT) handler.removeObject(this);

      //  if(y <=0 || y >= Game.HEIGHT - 32) velY *= -1;
      //  if(x <=0 || x>= Game.WIDTH - 16) velX *= -1;

        handler.addObject(new Trail((int)x,(int)y,ID.Trail, Color.red,10,10,0.02f,handler));

    }

    public void render(Graphics g) {

        g.setColor(Color.red);
        g.fillRect((int)x,(int)y, 10, 10);
    }
}
