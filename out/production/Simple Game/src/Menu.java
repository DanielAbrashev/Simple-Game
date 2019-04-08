import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Menu extends MouseAdapter {

    private Game game;
    private Handler handler;
    private HUD hud;
    private Random r = new Random();


    public Menu(Game game, Handler handler, HUD hud) {
        this.game = game;
        this.handler = handler;
        this.hud = hud;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (game.gameState == Game.STATE.Menu) {

            if (mouseOver(mx, my, 220, 150, 200, 64)) {
                game.gameState = Game.STATE.Game;
                handler.addObject(new Player((int) Game.WIDTH / 2 - 32, (int) Game.HEIGHT / 2 - 32, ID.Player, handler));
                handler.clearEnemies();
                handler.addObject(new BasicEnemy(r.nextInt((int) (Game.WIDTH - 50)), r.nextInt((int) (Game.HEIGHT - 50)), ID.BasicEnemy, handler));
            }
            if (mouseOver(mx, my, 220, 250, 200, 64)) {
                game.gameState = Game.STATE.Help;
            }
            if (mouseOver(mx, my, 220, 350, 200, 64)) {
                System.exit(1);
            }
        }
        if (game.gameState == Game.STATE.Help) {
            if (mouseOver(mx, my, 220, 350, 200, 64)) {
                game.gameState = Game.STATE.Menu;
                return;
            }
        }
        if (game.gameState == Game.STATE.End) {
            if (mouseOver(mx, my, 220, 350, 200, 64)) {
                game.gameState = Game.STATE.Game;
                hud.setLevel(1);
                hud.setScore(0);
                handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32, ID.Player, handler));
                handler.clearEnemies();
                handler.addObject(new BasicEnemy(r.nextInt((int) (Game.WIDTH - 50)), r.nextInt((int) (Game.HEIGHT - 50)), ID.BasicEnemy, handler));

            }
        }


    }

    public void mouseRelease(MouseEvent e) {

    }

    private boolean mouseOver(int mx, int my, int x, int y, float width, float height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                return true;
            } else return false;
        } else return false;

    }

    public void tick() {

    }

    public void render(Graphics g) {
        if (game.gameState == Game.STATE.Menu) {
            Font fnt = new Font("arial", 1, 50);
            Font fnt2 = new Font("arial", 1, 70);

            g.setFont(fnt2);
            g.setColor(Color.white);
            g.drawString("Menu", 230, 70);

            g.setFont(fnt);
            g.drawRect(220, 150, 200, 64);
            g.drawString("Play", 270, 200);

            g.setFont(fnt);
            g.drawRect(220, 250, 200, 64);
            g.drawString("Help", 270, 300);

            g.setFont(fnt);
            g.drawRect(220, 350, 200, 64);
            g.drawString("Exit", 270, 400);
        } else if (game.gameState == Game.STATE.Help) {
            Font fnt2 = new Font("arial", 1, 50);
            Font fnt1 = new Font("arial", 1, 20);

            g.setFont(fnt2);
            g.setColor(Color.white);
            g.drawString("Help", 260, 70);

            g.setFont(fnt1);
            g.setColor(Color.white);
            g.drawString("Use the UP, DOWN, LEFT, RIGHT buttons to avoid the enemies", 20, 200);

            g.setFont(fnt2);
            g.drawRect(220, 350, 200, 64);
            g.drawString("Back", 260, 400);
        } else if (game.gameState == Game.STATE.End) {
            Font fnt2 = new Font("arial", 1, 70);
            Font fnt1 = new Font("arial", 1, 40);

            g.setFont(fnt2);
            g.setColor(Color.white);
            g.drawString("Game Over", 120, 110);

            g.setFont(fnt1);
            g.setColor(Color.white);
            g.drawString("Your score is : " + hud.getScore(), 120, 230);

            g.setFont(fnt1);
            g.drawRect(220, 350, 200, 64);
            g.drawString("Try Again", 229, 400);
        }

    }

}
