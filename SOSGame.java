package defaultPackage;

public class SOSGame {
    public static void main(String[] args) {
        // create the GUI and start the game
        javax.swing.SwingUtilities.invokeLater(() -> {
            SOSGameGUI gameGUI = new SOSGameGUI(3); // example: 3x3 board
            gameGUI.createAndShowGUI();
        });
    }
}
