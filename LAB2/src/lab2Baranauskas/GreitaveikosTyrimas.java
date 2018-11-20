
package lab2Baranauskas;

import laborai.studijosktu.BstSetKTUx2;
import laborai.studijosktu.AvlSetKTUx;
import laborai.studijosktu.SortedSetADTx;
import laborai.studijosktu.BstSetKTUx;
import laborai.gui.MyException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

public class GreitaveikosTyrimas {

    public static final String FINISH_COMMAND = "finish";
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("laborai.gui.messages");

    private static final String[] TYRIMU_VARDAI = {"addBstRec", "addBstIte", "addAvlRec", "removeBst", "containsBst", "containsAVl"};
    private static final int[] TIRIAMI_KIEKIAI = {10000, 20000, 40000, 100000};

    private final BlockingQueue resultsLogger = new SynchronousQueue();
    private final Semaphore semaphore = new Semaphore(-1);
    private final Timekeeper tk;
    private final String[] errors;

    private final SortedSetADTx<Vaisius> aSeries = new BstSetKTUx(new Vaisius(), Vaisius.pagalKaina);
    private final SortedSetADTx<Vaisius> aSeries2 = new BstSetKTUx2(new Vaisius());
    private final SortedSetADTx<Vaisius> aSeries3 = new AvlSetKTUx(new Vaisius());

    public GreitaveikosTyrimas() {
        semaphore.release();
        tk = new Timekeeper(TIRIAMI_KIEKIAI, resultsLogger, semaphore);
        errors = new String[]{
            MESSAGES.getString("error1"),
            MESSAGES.getString("error2"),
            MESSAGES.getString("error3"),
            MESSAGES.getString("error4")
        };
    }

    public void pradetiTyrima() {
        try {
            SisteminisTyrimas();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void SisteminisTyrimas() throws InterruptedException {
        try {
            for (int k : TIRIAMI_KIEKIAI) {
                Vaisius[] autoMas = VaisiuGamyba.generuotiIrIsmaisyti(k, 1.0);
                aSeries.clear();
                aSeries2.clear();
                aSeries3.clear();
                tk.startAfterPause();
                tk.start();
                for (Vaisius a : autoMas) {
                    aSeries.add(a);
                }
                tk.finish(TYRIMU_VARDAI[0]);
                for (Vaisius a : autoMas) {
                    aSeries2.add(a);
                }
                tk.finish(TYRIMU_VARDAI[1]);
                for (Vaisius a : autoMas) {
                    aSeries3.add(a);
                }
                tk.finish(TYRIMU_VARDAI[2]);
                for (Vaisius a : autoMas) {
                    aSeries.remove(a);
                }
                tk.finish(TYRIMU_VARDAI[3]);
                for (Vaisius a : autoMas) {
                    aSeries.contains(a);
                }
                tk.finish(TYRIMU_VARDAI[4]);
                for (Vaisius a : autoMas) {
                    aSeries3.contains(a);
                }
                tk.finish(TYRIMU_VARDAI[5]);
                tk.seriesFinish();
                
            }
            tk.logResult(FINISH_COMMAND);
        } catch (MyException e) {
            if (e.getCode() >= 0 && e.getCode() <= 3) {
                tk.logResult(errors[e.getCode()] + ": " + e.getMessage());
            } else if (e.getCode() == 4) {
                tk.logResult(MESSAGES.getString("msg3"));
            } else {
                tk.logResult(e.getMessage());
            }
        }
    }

    public BlockingQueue<String> getResultsLogger() {
        return resultsLogger;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}
