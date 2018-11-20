package lab2Baranauskas;

import java.awt.image.BandCombineOp;
import laborai.studijosktu.Ks;
import laborai.studijosktu.AvlSetKTUx;
import laborai.studijosktu.SortedSetADTx;
import laborai.studijosktu.SetADT;
import laborai.studijosktu.BstSetKTUx;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import laborai.studijosktu.BstSetKTU;

public class VaisiuTestai {
    
    static Vaisius[] VaisiuKrepšys;
    static SortedSetADTx<Vaisius> aSerija = new BstSetKTUx<>(new Vaisius(), Vaisius.pagalPavadinima);

    public static void main(String[] args) throws CloneNotSupportedException {
        Locale.setDefault(Locale.US);
        aibesTestas();
    }
    
    static SortedSetADTx generuotiAibe(int kiekis, int generN)
    {
        VaisiuKrepšys = new Vaisius[generN];
        for (int i = 0; i < generN; i++)
        {
            VaisiuKrepšys[i] = new Vaisius.Builder().buildRandom();
        }
        Collections.shuffle(Arrays.asList(VaisiuKrepšys));
        aSerija.clear();
        for (int i = 0; i < kiekis; i++)
        {
            aSerija.add(VaisiuKrepšys[i]);
        }
        return aSerija;       
    }
    
    public static void aibesTestas() throws CloneNotSupportedException
    {
        Vaisius a1 = new Vaisius("Raudona", "Obuolys", "34697", 0.50, 0.20, 3);
        Vaisius a2 = new Vaisius.Builder()
                .spalva("Geltona")
                .Pavadinimas("Bananas")
                .BarKodNum("89384")
                .svoris(0.40)
                .kaina(0.63)
                .kiekis(2)
                .build();   
        Vaisius a3 = new Vaisius.Builder().buildRandom();
        Vaisius a4 = new Vaisius("Oranžinė Apelsinas 61483 0.30 0.40 1");
        Vaisius a5 = new Vaisius("Žalia Kivis 87134 0.20 0.46 3");
        Vaisius a6 = new Vaisius("Geltona Ananasas 18948 1.80 0.99 2");
        Vaisius a7 = new Vaisius("Raudona Braškė 68184 0.10 0.53 16");
        Vaisius a8 = new Vaisius("Žalia Vynuogė 33481 0.05 0.48 21");
        Vaisius a9 = new Vaisius("Raudona Vyšnia 38948 0.07 0.98 13");
        
        Vaisius[] vaisiuMasyvas = {a1, a2, a3, a4, a5, a6, a7, a8, a9};
        
        Ks.oun("Vaisiu Aibe:");
        SortedSetADTx<Vaisius> vaisiuAibe = new BstSetKTUx(new Vaisius());
        for (Vaisius v : vaisiuMasyvas) 
        {
            vaisiuAibe.add(v);
            Ks.oun(v);
        }
        Ks.oun("");
        BstSetKTUx<Vaisius> vaisiai = new BstSetKTUx(new Vaisius());
        BstSetKTUx<Vaisius> vaisiai2 = new BstSetKTUx(new Vaisius());
        BstSetKTUx<Vaisius> vaisiai3 = new BstSetKTUx(new Vaisius());
        
        vaisiai.add(a1); vaisiai2.add(a1);
        vaisiai.add(a2); vaisiai2.add(a2);
        vaisiai.add(a3); vaisiai2.add(a3);
        vaisiai.add(a4); vaisiai2.add(a4);
        vaisiai.add(a5); vaisiai2.add(a5);
        vaisiai.add(a6); vaisiai2.add(a6);
        vaisiai.add(a7); vaisiai2.add(a7); 
        vaisiai.add(a9); vaisiai2.add(a8);
        vaisiai.add(a8);
        
        Ks.oun("PollLast pašalina aukščiausia elementa: " + vaisiai.pollLast(a2));      
        Ks.oun("");

        Ks.oun("Ar masyvas Vaisiai turi visus elementus kaip masyvas Vaisiai2: " + vaisiai.containsAll(vaisiai2));
        Ks.oun("");
        
        Ks.oun("Į naują masyvą pridedame visus Vaisiai masyvo elementus: ");
        vaisiai3.addAll(vaisiai);
        Ks.oun("");
        for (Vaisius a : vaisiai3) {
            Ks.oun(a);
        }
        Ks.oun("");
        
        Ks.oun("Higher metodas ieško aukštesnio elemento (pagal Barkodą ir Spalva): " + vaisiai3.higher(a1));
        Ks.oun("");
        
        Ks.oun("Vaisiu masyvo SubSet: \n" + vaisiai3.subSet(a3, true, a8, true).toString());
        Ks.oun("");
        
        Ks.oun("Vaisiu masyvo TailSet: \n" + vaisiai3.tailSet(a1).toString());
        Ks.oun("");
        
        Ks.oun("Medžio charakteristika - medžio aukštis: " + vaisiai.height());
        Ks.ounn(vaisiai.toVisualizedString(""));
        Ks.oun("");
        
        SetADT vaisiaiSum = vaisiai.allLeftLeaves();
        Ks.oun("Sukurta medžio funkcija - Kairių lapų suma:\n" + vaisiaiSum.toString());
        Ks.oun("");
             
        /*
             
        for (Vaisius a : vaisiuMasyvas)
        {
            vaisiuAibe.add(a);
            Ks.oun("Aibe papildoma: " + a + ". Jos dydis: " + vaisiuAibe.size());
        }
        Ks.oun("");
        Ks.oun(vaisiuAibe.toVisualizedString(""));
        
        Ks.oun("");
        
        
        
        SortedSetADTx<Vaisius> vaisiuAibeKopija
                = (SortedSetADTx<Vaisius>) vaisiuAibe.clone();
        
        vaisiuAibeKopija.add(a2);
        vaisiuAibeKopija.add(a3);
        vaisiuAibeKopija.add(a4);
        
        Ks.oun("Papildyta vaisiu aibes kopija");
        Ks.oun(vaisiuAibeKopija.toVisualizedString(""));
        
        a9.setSvoris(1.50);
        
        Ks.oun("Originalas:");
        Ks.ounn(vaisiuAibe.toVisualizedString(""));
        
        Ks.oun("Ar elementai egzistuoja aibėje?");
        for (Vaisius a : vaisiuMasyvas)
        {
            Ks.oun(a + ": " + vaisiuAibe.contains(a));
        }
        Ks.oun(a2 + ": " + vaisiuAibe.contains(a2));
        Ks.oun(a3 + ": " + vaisiuAibe.contains(a3));
        Ks.oun(a4 + ": " + vaisiuAibe.contains(a4));
        Ks.oun("");
        
        Ks.oun("Elementu šalinimas iš kopijos. Aibės dydis prieš šalinimą:  " +
                vaisiuAibeKopija.size());
        for (Vaisius a : new Vaisius[]{a2, a1, a9, a8, a5, a3, a4, a2, a7, a6, a7, a9})
        {
            vaisiuAibeKopija.remove(a);
            Ks.oun("Iš vaisiu aibės kopijos šalinama: " + a + ". Jos dydis: " + vaisiuAibeKopija.size());           
        }
        Ks.oun("");
        
        Ks.oun("Vaisiu aibė su iteratoriumi:");
        Ks.oun("");
        for (Vaisius a : vaisiuAibe)
        {
            Ks.oun(a);
        }
        
        Ks.oun("");
        Ks.oun("Vaisiu aibė AVL-medyje:");
        SortedSetADTx<Vaisius> vaisiuAibe3 = new AvlSetKTUx(new Vaisius());
        for (Vaisius a : vaisiuMasyvas)
        {
            vaisiuAibe3.add(a);
        }
        Ks.ounn(vaisiuAibe3.toVisualizedString(""));
        
        Ks.oun("Vaisiu aibė su iteratoriumi:");
        Ks.oun("");
        for (Vaisius a : vaisiuAibe3)
        {
            Ks.oun(a);
        }
        
        Ks.oun("");
        Ks.oun("Vaisiu aibė su atvirkštiniu iteratoriumi:");
        Ks.oun("");
        Iterator iter = vaisiuAibe3.descendingIterator();
        while (iter.hasNext())
        {            
            Ks.oun(iter.next());
        }
        
        Ks.oun("");
        Ks.oun("Vaisiu aibės toString() metodas:");
        Ks.ounn(vaisiuAibe3);
        
        
        vaisiuAibe.clear();
        vaisiuAibe3.clear();
        
        Ks.oun("");
        Ks.oun("Vaisiu aibė DP-medyje:");
        vaisiuAibe.load("Duomenys\\ban.txt");
        Ks.ounn(vaisiuAibe.toVisualizedString(""));
        
        Ks.oun("");
        Ks.oun("Vaisiu aibė AVL-medyje:");
        vaisiuAibe3.load("Duomenys\\ban.txt");
        Ks.ounn(vaisiuAibe3.toVisualizedString(""));
        
        SetADT<String> vaisiuAibe4 = VaisiuApskaita.VaisiuSpalvos(vaisiuMasyvas);
        Ks.oun("Pasikartojantys vaisiai:\n"+ vaisiuAibe4.toString());

*/

    }
}
