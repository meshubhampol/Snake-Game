import javax.swing.JFrame;

public class Frame extends JFrame {
    // constructor
    Frame() {
        this.add(new Panel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        // ensuring system preferred size is set
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}