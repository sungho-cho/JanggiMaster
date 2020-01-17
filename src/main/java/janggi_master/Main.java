package janngi_master;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Main class that controls two frames: settingFrame and gameFrame
 * First runs settingFrame for choosing a mode,
 * Then runs gameFrame to actually run Janggi game
 */
public class Main {
    private static JFrame settingFrame;
    private static JFrame gameFrame;

    /**
     * psvm main method that first runs settingFrame for initial user input.
     * Closing settingFrame is handled by WindowListener, which starts gameFrame
     * @param args input arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowSettingWindow);
    }

    private static void createAndShowSettingWindow() {
        // Create and set-up the window.
        settingFrame = new JFrame("Setting");
        settingFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        SettingPanel settingPanel = new SettingPanel();
        settingPanel.setOpaque(true);
        settingFrame.setContentPane(settingPanel);
        settingFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                String numPlayersInput = settingPanel.getNumPlayersInput();
                int numPlayers = Integer.parseInt(numPlayersInput);

                createAndShowGameWindow(numPlayers);
                return;
            }
        });

        // Display the window.
        displayFrame(settingFrame);
    }

    private static void createAndShowGameWindow(int numPlayers) {
        // Create and set-up the window.
        gameFrame = new JFrame("Carcassone Game");
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        CarcassonneSystem game = new CarcassonneSystem(numPlayers);
        GamePanel gamePanel = new GamePanel(game);
        gamePanel.setOpaque(true);
        gameFrame.setContentPane(gamePanel);

        // Display the window.
        displayFrame(gameFrame);
    }

    private static void displayFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getContentPane().getPreferredSize();

        int frameWidth = frameSize.width;
        int frameHeight = frameSize.height;
        frame.setLocation(screenSize.width/2 - frameWidth/2, screenSize.height/2 - frameHeight/2);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();
    }
}
