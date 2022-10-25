import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {
    static final int width = 1280;
    static final int height = 720;

    static final int unit =60;
    // frequency and place where food will spawn randomly
    Timer timer;
    Random random;

    int fx,fy;
    int foodEaten;

    // initial body length of snake
    int bodyLength = 3;

    boolean game_flag =false;
    char dir='R';

    static final int delay = 160;

    static final int gsize = (width*height)/(unit*unit);
    final int x_snake[] = new int[gsize];
    final int y_snake[] = new int[gsize];

    Panel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.gray);
        this.addKeyListener(new MyKey());
        this.setFocusable(true);
        random = new Random();
        Game_Start();
    }
    public void Game_Start() {
        newFoodPosition();
        game_flag = true;
        timer=new Timer(delay,this);
        timer.start();
    }

    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        draw(graphic);
    }
    public void draw(Graphics graphic) {
        if(game_flag) {
            graphic.setColor(Color.orange);
            graphic.fillOval(fx,fy,unit,unit);
            for(int i=0;i<bodyLength;i++) {
                if(i==0) {
                    graphic.setColor(Color.green);
                    graphic.fillRect(x_snake[i],y_snake[i],unit,unit);
                }
                else {
                    graphic.setColor(new Color(50,80,0));
                    graphic.fillRect(x_snake[i],y_snake[i],unit,unit);
                }
            }
            graphic.setColor(Color.green);
            graphic.setFont(new Font("comic sans",Font.BOLD,40));
            FontMetrics font_me = getFontMetrics(graphic.getFont());
            graphic.drawString("Score: "+foodEaten,(width-font_me.stringWidth("score"+foodEaten))/2, graphic.getFont().getSize());

        }
        else {
            gameOver(graphic);
        }
    }

    public void move() {
        // updating the whole body of snake apart from its head
        for(int i=bodyLength;i>0;i--) {
            x_snake[i]=x_snake[i-1];
            y_snake[i]=y_snake[i-1];
        }

        // updating head of snake according the direction
        switch(dir) {
            case'U'-> {
                y_snake[0]=y_snake[0]-unit;
            }
            case'L'-> {
                x_snake[0]=x_snake[0]-unit;
            }
            case'D'-> {
                y_snake[0]=y_snake[0]+unit;
            }
            case'R'-> {
                x_snake[0]=x_snake[0]+unit;
            }
        }
    }
    public void gameOver(Graphics graphic) {

        // display the score
        graphic.setColor(Color.green);
        graphic.setFont(new Font("comic sans",Font.BOLD,40));
        FontMetrics font_me = getFontMetrics(graphic.getFont());
        graphic.drawString("Score: "+foodEaten,(width-font_me.stringWidth("Score: "+foodEaten))/2,graphic.getFont().getSize()+50);

        // display game over text
        graphic.setColor(Color.red);
        graphic.setFont(new Font("comic sans",Font.BOLD,40));
        FontMetrics font_me1 = getFontMetrics(graphic.getFont());
        graphic.drawString("Game Over!",(width-font_me1.stringWidth("Game Over!"))/2,height/2-150);

        // to display prompt to replay
        graphic.setColor(Color.pink);
        graphic.setFont(new Font("comic sans",Font.BOLD,80));
        FontMetrics font_me2 = getFontMetrics(graphic.getFont());
        graphic.drawString("Press R to replay",(width-font_me.stringWidth("Press R to replay"))/2-150,height/2);
    }
    public void newFoodPosition() {
        // to set display of food at random coordinates

        fx=random.nextInt((int)(width/gsize))*unit;
        fy=random.nextInt((int)(height/gsize))*unit;
    }
    public void checkHit() {
        // checking collision of snake with itself and walls
        for (int i = bodyLength; i > 0; i--) {
            if((x_snake[0]==x_snake[i]) && (y_snake[0]==y_snake[i]) ) {
                game_flag=false;
            }
        }

        if(x_snake[0]<0) {
            game_flag=false;
        }
        else if(x_snake[0]>width) {
            game_flag=false;
        }
        else if(y_snake[0]<0) {
            game_flag=false;
        }
        else if(y_snake[0]>height) {
            game_flag=false;
        }
        if(!game_flag) {
            timer.stop();
        }
    }
    public void eat() {
        if(x_snake[0]==fx && y_snake[0]==fy) {
            bodyLength++;
            foodEaten++;
            newFoodPosition();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(game_flag) {
            move();
            eat();
            checkHit();
        }
        repaint();
    }

    public class MyKey extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    if(dir!='R') {
                        dir='L';
                    }
                }
                case KeyEvent.VK_RIGHT -> {
                    if(dir!='L') {
                        dir='R';
                    }
                }
                case KeyEvent.VK_UP -> {
                    if(dir!='D') {
                        dir='U';
                    }
                }
                case KeyEvent.VK_DOWN -> {
                    if(dir!='U') {
                        dir='D';
                    }
                }
                case KeyEvent.VK_R -> {
                    if(!game_flag) {
                        foodEaten=0;
                        bodyLength=3;
                        dir='R';
                        Arrays.fill(x_snake,0);
                        Arrays.fill(y_snake,0);
                        Game_Start();
                    }
                }
            }
        }
    }
}
