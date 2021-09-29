import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

public class Main {
    public static final int FRAME_WIDTH = 900;
    public static final int FRAME_HEIGHT = 600;
    public static final int MENU_HEIGHT = 600;
    public static final int MENU_WIDTH = 200;
    private JFrame frame;

    public Main() {
        // Set the application window :
        frame = new JFrame("3iL - Graphic interfaces & 3D - TP2");
        frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set horizontal menu :
        JMenuBar horizontalMenu = new JMenuBar();
        frame.setJMenuBar(horizontalMenu);
        JMenu fileMenu = new JMenu("File");
        JMenuItem closeMenuItem = new JMenuItem("Close", new ImageIcon("images/logo-icon.png"));
        fileMenu.add(closeMenuItem);
        horizontalMenu.add(fileMenu);
        JMenu menuEdition = new JMenu("About");
        JMenuItem helpMenuItem = new JMenuItem("Help", new ImageIcon("images/botbtn2low.png"));
        menuEdition.add(helpMenuItem);
        horizontalMenu.add(menuEdition);

        // Set listeners on horizontal menu items :
        closeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                System.exit(0);
            }
        });
        helpMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "This is the TP2 about OpenGL.");
            }
        });

        // Set lateral menu :
        JPanel lateralMenu = new JPanel();
        lateralMenu.setPreferredSize(new Dimension(MENU_WIDTH, MENU_HEIGHT));
        lateralMenu.setBorder(BorderFactory.createEtchedBorder());
        frame.add(lateralMenu, BorderLayout.EAST);

        // Set buttons in lateral menu :
        JButton button1 = new JButton("Start/Stop rotation");
        lateralMenu.add(button1);

        // Set the OpenGL zone :
        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(new Square());
        canvas.requestFocus();
        frame.add(canvas, BorderLayout.CENTER);

        // Set an animator manager :
        Animator animator = new Animator(canvas);
        animator.start();

        // Set listeners on lateral menu buttons :
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (animator.isStarted()) {
                    animator.stop();
                } else {
                    animator.start();
                }
            }
        });

        // Show the JFrame :
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Application entry point.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}