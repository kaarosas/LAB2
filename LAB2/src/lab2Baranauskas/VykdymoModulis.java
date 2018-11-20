package lab2Baranauskas;

import laborai.gui.swing.Lab2Window;
import java.util.Locale;

public class VykdymoModulis {

    public static void main(String[] args) throws CloneNotSupportedException {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus
        VaisiuTestai.aibesTestas();
        Lab2Window.createAndShowGUI();
    }
}
