import java.util.Random;

public class Spawner {
    private Handler handler;
    private HUD hud;
    private Random r = new Random();

    private float scoreKeep = 0;

    public Spawner(Handler handler, HUD hud) {
        this.handler = handler;
        this.hud = hud;
    }

    public void tick() {
        scoreKeep++;
        if (scoreKeep >= 100) {
            scoreKeep = 0;
            hud.setLevel((int) (hud.getLevel() + 1));
            if (hud.getLevel() == 2) {
                handler.addObject(new BasicEnemy(r.nextInt((int) Game.WIDTH), r.nextInt((int) Game.HEIGHT), ID.BasicEnemy, handler));
            } else if (hud.getLevel() == 3) {
                handler.addObject(new FastEnemy(r.nextInt((int) Game.WIDTH), r.nextInt((int) Game.HEIGHT), ID.FastEnemy, handler));
            } else if (hud.getLevel() == 4) {
                handler.addObject(new BasicEnemy(r.nextInt((int) Game.WIDTH), r.nextInt((int) Game.HEIGHT), ID.BasicEnemy, handler));
            } else if (hud.getLevel() == 5) {
                handler.addObject(new SmartEnemy(r.nextInt((int) Game.WIDTH), r.nextInt((int) Game.HEIGHT), ID.SmartEnemy, handler));
            } else if (hud.getLevel() == 6) {
                handler.addObject(new FastEnemy(r.nextInt((int) Game.WIDTH), r.nextInt((int) Game.HEIGHT), ID.FastEnemy, handler));
            } else if (hud.getLevel() == 7) {
                handler.addObject(new BasicEnemy(r.nextInt((int) Game.WIDTH), r.nextInt((int) Game.HEIGHT), ID.BasicEnemy, handler));
            } else if (hud.getLevel() == 8) {
                handler.addObject(new BasicEnemy(r.nextInt((int) Game.WIDTH), r.nextInt((int) Game.HEIGHT), ID.BasicEnemy, handler));
            } else if (hud.getLevel() == 9) {
                handler.addObject(new FastEnemy(r.nextInt((int) Game.WIDTH), r.nextInt((int) Game.HEIGHT), ID.FastEnemy, handler));
            } else if (hud.getLevel() == 10) {
                handler.clearEnemies();
                handler.addObject(new EnemyBoss((int) (Game.WIDTH / 2) - 54, -110, ID.EnemyBoss, handler));
            }
        }


    }


}
