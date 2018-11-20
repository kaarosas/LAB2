package laborai.studijosktu;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;
import java.util.TreeMap;

/**
 * Rikiuojamos objektų kolekcijos - aibės realizacija dvejetainiu paieškos
 * medžiu.
 *
 * @užduotis Peržiūrėkite ir išsiaiškinkite pateiktus metodus.
 *
 * @author darius.matulis@ktu.lt
 *
 * @param <E> Aibės elemento tipas. Turi tenkinti interfeisą Comparable<T>, arba
 * per klasės konstruktorių turi būti paduodamas Comparator<T> interfeisą
 * tenkinantis objektas.
 */
public class BstSetKTU<E extends Comparable<E>> implements SortedSetADT<E>, Cloneable {

    // Medžio šaknies mazgas
    protected BstNode<E> root = null;
    // Medžio dydis
    protected int size = 0;
    //Kintamasis, nurodantis ar pavyko operacija su aibe
    boolean returned = true;
    // Rodyklė į komparatorių
    protected Comparator<? super E> c = null;

    /**
     * Sukuriamas aibės objektas DP-medžio raktams naudojant Comparable<T>
     */
    public BstSetKTU() {
        this.c = (e1, e2) -> e1.compareTo(e2);
    }

    /**
     * Sukuriamas aibės objektas DP-medžio raktams naudojant Comparator<T>
     *
     * @param c Komparatorius
     */
    public BstSetKTU(Comparator<? super E> c) {
        this.c = c;
    }

    /**
     * Patikrinama ar aibė tuščia.
     *
     * @return Grąžinama true, jei aibė tuščia.
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @return Grąžinamas aibėje esančių elementų kiekis.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Išvaloma aibė.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }
    
        
    public boolean addAll(BstSetKTU<? extends E> c)
    {
        for (E elem : c)
        {
            this.add(elem);
        }
        return true;
    }
    
    public boolean containsAll(BstSetKTU<?> c)
    {
        for (E elem : (BstSetKTU<E>) c) 
        {
            if (!(this.contains(elem)))
            {
                return false;
            }
        }
        return true;
    }
        
//    public SetADT<E> sumOfLeft()
//    {
//        return sumOfLeftLeaves(root);
//    }
//    
//    private SetADT<E> sumOfLeftLeaves(BstNode root)
//    {
//        if(root == null)return null;
//        SetADT<E> ls = sumOfLeftLeaves(root.left);
//        SetADT<E> rs = sumOfLeftLeaves(root.right);
//        if(root.left != null && root.left.left == null && root.left.right == null)
//            ls.add((E) root.left.element);
//        for (E a : rs)
//        {
//           ls.add(a); 
//        } 
//        return ls;
//    }
//    
    
    
    public BstSetKTU<E> allLeftLeaves()
    {
        return leftLeaves(root);
    }
    
    private boolean isLeaf(BstNode<E> node)
    {
        if (node == null)
        {
            return false;
        }
        if (node.left == null && node.right == null)
        {
            return true;
        }
        return false;
    }
    
    
    public BstSetKTU<E> leftLeaves(BstNode<E> node)
    {
        BstSetKTU<E> result = new BstSetKTU<>();
        
        if (node != null)
        {
            if (isLeaf(node.left))
            {
                result.add(node.left.element);
            }
            else 
            {
                result.addAll(leftLeaves(node.left));
            }
            result.addAll(leftLeaves(node.right));
        }
        return result;
    }
 
    public E pollLast(E e)
    {
        if (size == 0)
        {
            return null;
        }  
        Iterator<E> it = iterator();
        E result = e;
        while (it.hasNext())
        {
            E temp = it.next();
            if (temp.compareTo(result) > 1)
            {
                result = temp;
            }
        }
        
        remove(result);
        return result; 

    }
    
    public E higher(E e)
    {
        if (e == null)
        {
            throw new NullPointerException();
        }
        if (size == 0)
        {
            return null;
        }
        Iterator<E> it = iterator();
        E result = null;
        while (it.hasNext())
        {
            E temp = it.next();
            if (temp.compareTo(e) == 1)
            {
                result = temp;
                break;
            }
        }
        return result;
    }
    
    
    
    public int heightOfTree(BstNode<E> node)
    {
        if (node == null)
        {
            return 0;
        }
        else 
        {
            return 1
                    + Math.max(heightOfTree(node.left),
                    heightOfTree(node.right));
        }
    }
    
    public int height()
    {
        return heightOfTree(root);
    }

    /**
     * Patikrinama ar aibėje egzistuoja elementas.
     *
     * @param element - Aibės elementas.
     * @return Grąžinama true, jei aibėje egzistuoja elementas.
     */
    @Override
    public boolean contains(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in contains(E element)");
        }

        return get(element) != null;
    }  
    
    /**
     * Aibė papildoma nauju elementu.
     *
     * @param element - Aibės elementas.
     */
    @Override
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in add(E element)");
        }

        root = addRecursive(element, root);
    }

    

    private BstNode<E> addRecursive(E element, BstNode<E> node) {
        if (node == null) {
            size++;
            return new BstNode<>(element);
        }

        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.left = addRecursive(element, node.left);
        } else if (cmp > 0) {
            node.right = addRecursive(element, node.right);
        }

        return node;
    }

    /**
     * Pašalinamas elementas iš aibės.
     *
     * @param element - Aibės elementas.
     */
    @Override
    public void remove(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in remove(E element)");
        }

        root = removeRecursive(element, root);
    }

    private BstNode<E> removeRecursive(E element, BstNode<E> node) {
        if (node == null) {
            return node;
        }
        // Medyje ieškomas šalinamas elemento mazgas;
        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.left = removeRecursive(element, node.left);
        } else if (cmp > 0) {
            node.right = removeRecursive(element, node.right);
        } else if (node.left != null && node.right != null) {
            /* Atvejis kai šalinamas elemento mazgas turi abu vaikus.
             Ieškomas didžiausio rakto elemento mazgas kairiajame pomedyje.
             Galima kita realizacija kai ieškomas mažiausio rakto
             elemento mazgas dešiniajame pomedyje. Tam yra sukurtas
             metodas getMin(E element);
             */
            BstNode<E> nodeMax = getMax(node.left);
            /* Didžiausio rakto elementas (TIK DUOMENYS!) perkeliamas į šalinamo
             elemento mazgą. Pats mazgas nėra pašalinamas - tik atnaujinamas;
             */
            node.element = nodeMax.element;
            // Surandamas ir pašalinamas maksimalaus rakto elemento mazgas;
            node.left = removeMax(node.left);
            size--;
            // Kiti atvejai
        } else {
            node = (node.left != null) ? node.left : node.right;
            size--;
        }

        return node;
    }
    
    BstNode<E> get(E data) {
        if (data == null) {
            throw new NullPointerException("Null pointer int get");
        }
        BstNode<E> n = root;
        BstNode<E> nParent = null;
        while (n != null) {
            nParent = n;
            int cmp = (c == null)
                    ? data.compareTo(n.element)
                    : c.compare(data, n.element);
            if (cmp < 0) {
                n = n.left;
            } else if (cmp > 0) {
                n = n.right;
            } else {
                return n;
            }
        }
        return nParent;
    }

    /**
     * Pašalina maksimalaus rakto elementą paiešką pradedant mazgu node
     *
     * @param node
     * @return
     */
    BstNode<E> removeMax(BstNode<E> node) {
        if (node == null) {
            return null;
        } else if (node.right != null) {
            node.right = removeMax(node.right);
            return node;
        } else {
            return node.left;
        }
    }

    /**
     * Grąžina maksimalaus rakto elementą paiešką pradedant mazgu node
     *
     * @param node
     * @return
     */
    BstNode<E> getMax(BstNode<E> node) {
        return get(node, true);
    }

    /**
     * Grąžina minimalaus rakto elementą paiešką pradedant mazgu node
     *
     * @param node
     * @return
     */
    BstNode<E> getMin(BstNode<E> node) {
        return get(node, false);
    }

    private BstNode<E> get(BstNode<E> node, boolean findMax) {
        BstNode<E> parent = null;
        while (node != null) {
            parent = node;
            node = (findMax) ? node.right : node.left;
        }
        return parent;
    }

    /**
     * Grąžinamas aibės elementų masyvas.
     *
     * @return Grąžinamas aibės elementų masyvas.
     */
    @Override
    public Object[] toArray() {
        int i = 0;
        Object[] array = new Object[size];
        for (Object o : this) {
            array[i++] = o;
        }
        return array;
    }

    /**
     * Aibės elementų išvedimas į String eilutę Inorder (Vidine) tvarka. Aibės
     * elementai išvedami surikiuoti didėjimo tvarka pagal raktą.
     *
     * @return elementų eilutė
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (E element : this) {
            sb.append(element.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     *
     * Medžio vaizdavimas simboliais, žiūr.: unicode.org/charts/PDF/U2500.pdf
     * Tai 4 galimi terminaliniai simboliai medžio šakos gale
     */
    private static final String[] term = {"\u2500", "\u2534", "\u252C", "\u253C"};
    private static final String rightEdge = "\u250C";
    private static final String leftEdge = "\u2514";
    private static final String endEdge = "\u25CF";
    private static final String vertical = "\u2502  ";
    private String horizontal;

    /* Papildomas metodas, išvedantis aibės elementus į vieną String eilutę.
     * String eilutė formuojama atliekant elementų postūmį nuo krašto,
     * priklausomai nuo elemento lygio medyje. Galima panaudoti spausdinimui į
     * ekraną ar failą tyrinėjant medžio algoritmų veikimą.
     *
     * @author E. Karčiauskas
     */
    public String toVisualizedString(String dataCodeDelimiter) {
        horizontal = term[0] + term[0];
        return root == null ? new StringBuilder().append(">").append(horizontal).toString()
                : toTreeDraw(root, ">", "", dataCodeDelimiter);
    }

    private String toTreeDraw(BstNode<E> node, String edge, String indent, String dataCodeDelimiter) {
        if (node == null) {
            return "";
        }
        String step = (edge.equals(leftEdge)) ? vertical : "   ";
        StringBuilder sb = new StringBuilder();
        sb.append(toTreeDraw(node.right, rightEdge, indent + step, dataCodeDelimiter));
        int t = (node.right != null) ? 1 : 0;
        t = (node.left != null) ? t + 2 : t;
        sb.append(indent).append(edge).append(horizontal).append(term[t]).append(endEdge).append(
                split(node.element.toString(), dataCodeDelimiter)).append(System.lineSeparator());
        step = (edge.equals(rightEdge)) ? vertical : "   ";
        sb.append(toTreeDraw(node.left, leftEdge, indent + step, dataCodeDelimiter));
        return sb.toString();
    }

    private String split(String s, String dataCodeDelimiter) {
        int k = s.indexOf(dataCodeDelimiter);
        if (k <= 0) {
            return s;
        }
        return s.substring(0, k);
    }

    /**
     * Sukuria ir grąžina aibės kopiją.
     *
     * @return Aibės kopija.
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        BstSetKTU<E> cl = (BstSetKTU<E>) super.clone();
        if (root == null) {
            return cl;
        }
        cl.root = cloneRecursive(root);
        cl.size = this.size;
        return cl;
    }

    private BstNode<E> cloneRecursive(BstNode<E> node) {
        if (node == null) {
            return null;
        }

        BstNode<E> clone = new BstNode<>(node.element);
        clone.left = cloneRecursive(node.left);
        clone.right = cloneRecursive(node.right);
        return clone;
    }

    /**
     * Grąžinamas aibės poaibis iki elemento.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis iki elemento.
     */
    @Override
    public SetADT<E> headSet(E element) {
        if (element == null)
        {
            throw new NullPointerException();
        }
        SetADT<E> hs = new BstSetKTU();
        if (contains(element))
        {
            BstNode<E> n = get(element);
            headRecursive(hs, n);
        }
        return hs;
    }
    
    private BstNode<E> headRecursive(SetADT<E> hs, BstNode<E> nMod) 
    {
        if (nMod == null)
        {
            return null;
        }
        hs.add(nMod.element);
        headRecursive(hs, nMod.left);
        headRecursive(hs, nMod.right);
        return nMod;
    }

    /**
     * Grąžinamas aibės poaibis nuo elemento element1 iki element2.
     *
     * @param element1 - pradinis aibės poaibio elementas.
     * @param element2 - galinis aibės poaibio elementas.
     * @return Grąžinamas aibės poaibis nuo elemento element1 iki element2.
     */
    @Override
    public SetADT<E> subSet(E element1, E element2) {
        if (element1 == null || element2 == null)
        {
            throw new NullPointerException();
        }
        SetADT<E> ss = new BstSetKTU();
        if (contains(element1) && contains(element2))
        {
            BstNode<E> n = get(element1);
            subRecursive(ss, n, element2);
        }
        return ss;
    }
    
    public SortedSetADT<E> subSet(E fromElement, boolean fromInclusive,
            E toElement, boolean toInclusive)
    {
        if (fromElement == null || toElement == null)
        {
            throw new NullPointerException();
        }
        SortedSetADT<E> ss = new BstSetKTU();
        if (!fromInclusive)
        {
            Iterator<E> it = iterator();
            while (it.hasNext())
            {
                E temp = it.next();
                if (temp.compareTo(fromElement) == 1)
                {
                    if (contains(temp) && contains(toElement))
                    {
                        BstNode<E> n = get(temp);
                        subRecursive(ss, n, toElement);
                    }
                    return ss;
                }
            }
        }
        if (contains(fromElement) && contains(toElement))
        {
            BstNode<E> n = get(fromElement);
            subRecursive(ss, n, toElement);
        }
        return ss;   
    }
    
    private BstNode<E> subRecursive(SetADT<E> sSet,
            BstNode<E> n, E elem)
    {
        if (n == null)
        {
            return null;
        }
        if (n.element != elem)
        {
            sSet.add(n.element);
            subRecursive(sSet, n.left, elem);
            subRecursive(sSet, n.right, elem);
        }
        else
        {
            sSet.add(n.element);
        }
        return n;
    }
    
    
    private BstNode<E> subRecursive(SortedSetADT<E> sSet,
            BstNode<E> n, E elem)
    {
        if (n == null)
        {
            return null;
        }
        if (n.element != elem)
        {
            sSet.add(n.element);
            subRecursive(sSet, n.left, elem);
            subRecursive(sSet, n.right, elem);
        }
        else
        {
            sSet.add(n.element);
        }
        return n;
    }

    /**
     * Grąžinamas aibės poaibis iki elemento.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis nuo elemento.
     */
    @Override
    public SetADT<E> tailSet(E element) {
        if (element == null)
        {
            throw new NullPointerException();
        }
        SetADT<E> ts = new BstSetKTU();
        if (contains(element))
        {
            BstNode<E> n = root;
            subRecursive(ts, n, element);
        }
        return ts;
    }

    /**
     * Grąžinamas tiesioginis iteratorius.
     *
     * @return Grąžinamas tiesioginis iteratorius.
     */
    @Override
    public Iterator<E> iterator() {
        return new IteratorKTU(true);
    }

    /**
     * Grąžinamas atvirkštinis iteratorius.
     *
     * @return Grąžinamas atvirkštinis iteratorius.
     */
    @Override
    public Iterator<E> descendingIterator() {
        return new IteratorKTU(false);

    }

    /**
     * Vidinė objektų kolekcijos iteratoriaus klasė. Iteratoriai: didėjantis ir
     * mažėjantis. Kolekcija iteruojama kiekvieną elementą aplankant vieną kartą
     * vidine (angl. inorder) tvarka. Visi aplankyti elementai saugomi steke.
     * Stekas panaudotas iš java.util paketo, bet galima susikurti nuosavą.
     */
    private class IteratorKTU implements Iterator<E> {

        private Stack<BstNode<E>> stack = new Stack<>();
        // Nurodo iteravimo kolekcija kryptį, true - didėjimo tvarka, false - mažėjimo
        private boolean ascending;
        // Nurodo einamojo medžio elemento tėvą. Reikalingas šalinimui.
        private BstNode<E> parent = root;

        IteratorKTU(boolean ascendingOrder) {
            this.ascending = ascendingOrder;
            this.toStack(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
        public E next() {
            if (!stack.empty()) {
                // Grąžinamas paskutinis į steką patalpintas elementas
                BstNode<E> n = stack.pop();
                // Atsimenama tėvo viršunė. Reikia remove() metodui
                parent = (!stack.empty()) ? stack.peek() : root;
                BstNode node = (ascending) ? n.right : n.left;
                // Dešiniajame n pomedyje ieškoma minimalaus elemento,
                // o visi paieškos kelyje esantys elementai talpinami į steką
                toStack(node);
                return n.element;
            } else { // Jei stekas tuščias
                return null;
            }
        }

        @Override
        public void remove() {
            if (stack == null)
            {
                throw new IllegalStateException();
            }
            else
            {
                stack = null;
                parent = null;
            }
        }

        private void toStack(BstNode<E> n) {
            while (n != null) {
                stack.push(n);
                n = (ascending) ? n.left : n.right;
            }
        }
    }

    /**
     * Vidinė kolekcijos mazgo klasė
     *
     * @param <N> mazgo elemento duomenų tipas
     */
    protected class BstNode<N> {

        // Elementas
        protected N element;
        // Rodyklė į kairįjį pomedį
        protected BstNode<N> left;
        // Rodyklė į dešinįjį pomedį
        protected BstNode<N> right;

        protected BstNode() {
        }

        protected BstNode(N element) {
            this.element = element;
            this.left = null;
            this.right = null;
        }
    }
}
