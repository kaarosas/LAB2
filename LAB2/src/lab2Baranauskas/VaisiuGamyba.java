package lab2Baranauskas;

import laborai.gui.MyException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

public class VaisiuGamyba {
    
    private static Vaisius[] vaisiai;
    private static int pradinisIndeksas = 0, galinisIndeksas = 0;
    private static boolean arPradzia = true;
    
    public static Vaisius[] generuoti(int kiekis)
    {
        vaisiai = new Vaisius[kiekis];
        for (int i = 0; i < kiekis; i++)
        {
            vaisiai[i] = new Vaisius.Builder().buildRandom();
        }
        return vaisiai;
    }
    
    public static Vaisius[] generuotiIrIsmaisyti(int aibesDydis,
            double isbarstymoKoeficentas) throws MyException
    {
        return generuotiIrIsmaisyti(aibesDydis, aibesDydis, isbarstymoKoeficentas);
    }
    
    public static Vaisius[] generuotiIrIsmaisyti(int aibesDydis,
        int aibesImtis, double isbarstymoKoeficientas) throws MyException
    {
        vaisiai = generuoti(aibesDydis);
        return ismaisyti(vaisiai, aibesImtis, isbarstymoKoeficientas);
    }
    
    public static Vaisius[] ismaisyti(Vaisius[] vaisiuKrepšys,
            int kiekis, double isbarstymoKoeficentas) throws MyException
    {
        if (vaisiuKrepšys == null)
        {
            throw new IllegalArgumentException("Vaisiu krepšys yra null");
        }
        if (kiekis <= 0)
        {
            throw new MyException(String.valueOf(kiekis), 1);
        }
        if (vaisiuKrepšys.length < kiekis)
        {
            throw new MyException(vaisiuKrepšys.length + " >= " + kiekis, 2);
        }
        if ((isbarstymoKoeficentas < 0) || (isbarstymoKoeficentas) > 1)
        {
            throw new MyException(String.valueOf(isbarstymoKoeficentas), 3);
        }
        
        int likusiuKiekis = vaisiuKrepšys.length - kiekis;
        int pradziosIndeksas = (int) (likusiuKiekis * isbarstymoKoeficentas / 2);
        
        Vaisius[] pradineVaisiuImtis = Arrays.copyOfRange(vaisiuKrepšys, pradziosIndeksas,
                pradziosIndeksas + kiekis);
        Vaisius[] likusiuVaisiuImtis = Stream
                .concat(Arrays.stream(Arrays.copyOfRange(vaisiuKrepšys, 0, pradziosIndeksas)),
                        Arrays.stream(Arrays.copyOfRange(vaisiuKrepšys, pradziosIndeksas + kiekis, vaisiuKrepšys.length)))
                .toArray(Vaisius[]::new);
        
        Collections.shuffle(Arrays.asList(pradineVaisiuImtis)
                .subList(0, (int) (pradineVaisiuImtis.length * isbarstymoKoeficentas)));
        Collections.shuffle(Arrays.asList(likusiuVaisiuImtis)
                .subList(0, (int) (likusiuVaisiuImtis.length * isbarstymoKoeficentas)));
        
        VaisiuGamyba.pradinisIndeksas = 0;
        galinisIndeksas = likusiuVaisiuImtis.length - 1;
        VaisiuGamyba.vaisiai = likusiuVaisiuImtis;
        return pradineVaisiuImtis;  
    }
    
    public static Vaisius gautiIsKrepšio() throws MyException 
    {
        if ((galinisIndeksas - pradinisIndeksas) < 0)
        {
            throw new MyException(String.valueOf(galinisIndeksas - pradinisIndeksas), 4);
        }
        arPradzia = !arPradzia;
        return vaisiai[arPradzia ? pradinisIndeksas++ : galinisIndeksas--];
    }
    
}
