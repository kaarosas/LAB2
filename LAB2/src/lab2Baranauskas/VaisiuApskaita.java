package lab2Baranauskas;

import laborai.studijosktu.BstSetKTU;
import laborai.studijosktu.SetADT;

public class VaisiuApskaita {
    
    public static SetADT<String> VaisiuSpalvos(Vaisius[] vais)
    {
        SetADT<Vaisius> uni = new BstSetKTU<>(Vaisius.pagalPavadinima);
        SetADT<String> kart = new BstSetKTU<>();
        for (Vaisius a : vais)
        {
            int sizeBefore = uni.size();
            uni.add(a);
            
            if (sizeBefore == uni.size())
            {
                kart.add(a.getPav());
            }
        }
        return kart;
    }
}
