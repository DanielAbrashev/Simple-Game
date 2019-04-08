import com.sun.xml.internal.bind.WhiteSpaceProcessor;
import sun.audio.AudioPlayer;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {


    private static final long serialVersionUID = -1849738768769853897L;

    public static final float WIDTH = 640, HEIGHT = WIDTH / 12 * 9;

    private Thread thread;
    private boolean running = false;

    public static boolean pause = false ;


    private Random r;
    private Handler handler;
    private HUD hud;
    private Spawner spawner;
    private Menu menu;

    public enum STATE {
        Menu,
        Help,
        Game,
        End,

    }

    public static STATE gameState = STATE.Menu;

    public Game() {
        handler = new Handler();
        hud = new HUD();
        menu = new Menu(this, handler, hud);
        this.addKeyListener(new KeyInput(handler,this));
        this.addMouseListener(menu);
        Audio.load();
        Audio.getMusic("music").loop();

        new Window(WIDTH, HEIGHT, "Let's build a game!", this);

        spawner = new Spawner(handler, hud);

        r = new Random();

        if (gameState == STATE.Game) {
            handler.addObject(new Player((int) WIDTH / 2 - 32, (int) HEIGHT / 2 - 32, ID.Player, handler));
            handler.addObject(new BasicEnemy(r.nextInt((int) Game.WIDTH), r.nextInt((int) Game.HEIGHT), ID.BasicEnemy, handler));
        } else {
            for (int i = 0; i < 10; i++) {
                handler.addObject(new MenuParticle(r.nextInt((int) WIDTH), r.nextInt((int) HEIGHT), ID.MenuParticle, handler));

            }
        }

    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfClicks = 60.0;
        double ns = 1000000000 / amountOfClicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //  System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();

    }

    private void tick() {
        if (gameState == STATE.Game) {
            if(!pause) {
                hud.tick();
                spawner.tick();
                handler.tick();

                if (HUD.HEALTH <= 0) {
                    HUD.HEALTH = 100;
                    gameState = STATE.End;
                    handler.clearEnemies();
                    for (int i = 0; i < 10; i++) {
                        handler.addObject(new MenuParticle(r.nextInt((int) WIDTH), r.nextInt((int) HEIGHT), ID.MenuParticle, handler));

                    }
                }
            }
        } else if (gameState == STATE.Menu || gameState == STATE.End) {
            menu.tick();
            handler.tick();

        }
    }


    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;


        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect((int) 0, (int) 0, (int) WIDTH, (int) HEIGHT);

        handler.render(g);

        if(pause){
            g.setColor(Color.white);
            g.drawString("PAUSED", 100,100);
        }

        if (gameState == STATE.Game) {
            hud.render(g);
        } else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End) {
            menu.render(g);
        }
        g.dispose();
        bs.show();
    }

    public static float clamp(float var, float min, float max) {
        if (var >= max)
            return var = max;
        else if (var <= min)
            return var = min;
        else
            return var;
    }

    public static void main(String[] args) {
        new Game();
    }
}
