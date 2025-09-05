import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ShutdownScheduler extends JFrame {

    //Definição de objetos


    //Construtor: definições da tela
    public ShutdownScheduler() {

    }
    private boolean isWindows(){
        String os = System.getProperty("os.name","").toLowerCase();
        return os.contains("win");
    }

    // Execução do programa: lançar a "janela"
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ShutdownScheduler().setVisible(true);
        });
    }

}
