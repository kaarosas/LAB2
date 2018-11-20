
package lab2Baranauskas;

import java.util.Locale;
import javafx.application.Application;
import javafx.stage.Stage;
import laborai.gui.fx.Lab2WindowFX;

public class VykdymoModulisFX extends Application {

    public static void main(String [] args) {
        VykdymoModulisFX.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus 
        VaisiuTestai.aibesTestas();
        Lab2WindowFX.createAndShowFXGUI(primaryStage);
    }
}
