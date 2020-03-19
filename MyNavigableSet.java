import java.util.*;
import java.util.function.Consumer;

public class MyNavigableSet<T> extends AbstractSet<T> implements NavigableSet<T>{
    private Comparator<? super T> comparator;
    private ArrayList<T> list;
    private Iterator<T> iterator;
    private int size;

    public MyNavigableSet(Collection<? extends T> coll, Comparator<? super T> comp) {
        list = new ArrayList<T>();
        list.addAll(coll);
        this.comparator = comp;
        list.sort(comp);
        this.iterator = list.iterator();
        this.size = list.size();
    }

    /*
    constructor containing comparator
     */
    public MyNavigableSet(Comparator<? super T> a){
        list = new ArrayList<T>();
        this.comparator = a;
    }

    /*
    constructor based on sortedSet
    */
    public MyNavigableSet(SortedSet<T> b) {
        list = new ArrayList<>();
        list.addAll(b);
        this.comparator = b.comparator();
    }
    /*
    adding element to Navigable set.
    according to results boolean value is returned.
     */

    public boolean add(T elem){
        if(list.contains(elem)){
            return false;
        }
        if(list.size() == 0) {
            list.add(elem);
            return true;
        }
        for(T t : list) {
            if(comparator.compare(t,elem)>0){
                list.add(list.indexOf(t),elem);
                return true;
            }
        }
        list.add(elem);
        return true;
    }
    /*
    getting an element based on index.
     */
    public T get(int index) {
        return list.get(index);
    }

    @Override

    /*
    Returns the greatest element in this set strictly less than the given element, or null if there is no such element.
     */
    public T lower(T t) {
            for (int i = list.size() - 1; i >= 0; i--) {
                if (comparator.compare(list.get(i), t) < 0) return list.get(i);
            }
        return null;
    }

    @Override
    /*
     Returns the greatest element in this set less than or equal to the given element, or null if there is no such element.
     */
    public T floor(T t) {
            for (int i = list.size() - 1; i >= 0; i--) {
                if (comparator.compare(list.get(i), t) <= 0) return list.get(i);
            }
        return null;
    }


    @Override
    /*
    Returns the least element in this set greater than or equal to the given element, or null if there is no such element.
     */
    public T ceiling(T t) {
            for (T e: list) {
                if (comparator.compare(e, t) >= 0){
                    return e;
                }
            }
            return null;
        }

    @Override
    /*
    Returns the least element in this set strictly greater than the given element, or null if there is no such element.
     */
    public T higher(T t) {
        for (T e: list) {
            if (comparator.compare(e, t) > 0){
                return e;
            }
        }
        return null;
    }

    @Override
    /*
    Retrieves and removes the first (lowest) element, or returns null if this set is empty.
     */
    public T pollFirst() {
        if (list.size() == 0) return null;
        T el = list.get(0);
        list.remove(0);
        return el;
    }

    @Override
    /*
    Retrieves and removes the last (highest) element, or returns null if this set is empty.
     */
    public T pollLast() {
        if (list.size() == 0) return null;
        T el = list.get(list.size()-1);
        list.remove(list.size()-1);
        return el;
    }

    @Override
    /*
    returns an iterator over arraylist
     */
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    /*
    Returns a reverse order view of the elements contained in this set.
     */
    public NavigableSet<T> descendingSet() {
        ArrayList<T> descendingArr = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            descendingArr.add(list.get(size() - 1 - i));
        }
        return new MyNavigableSet<T>(descendingArr,comparator.reversed());
    }

    @Override
    /*
    Returns an iterator over the elements in this set, in descending order.
     */
    public Iterator<T> descendingIterator() {
        return descendingSet().iterator();
    }

    @Override
    /*
    Returns a view of the portion of this set whose elements range from fromElement to toElement.
     */
    public NavigableSet<T> subSet(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive) {
        MyNavigableSet<T> subList = new MyNavigableSet<T>(tailSet(fromElement,fromInclusive));
        return subList.headSet(toElement,toInclusive);

    }

    @Override
    /*
    Returns a view of the portion of this set whose elements are less than (or equal to, if inclusive is true) toElement.
     */
    public NavigableSet<T> headSet(T toElement, boolean inclusive) {
        MyNavigableSet<T> head= (MyNavigableSet<T>) headSet(toElement);
        if (!inclusive) return head;
        head.add(head.ceiling(toElement));
        return head;
    }

    @Override
    /*
    Returns a view of the portion of this set whose elements are greater than (or equal to, if inclusive is true) fromElement.
     */
    public NavigableSet<T> tailSet(T fromElement, boolean inclusive) {
        MyNavigableSet<T> tail = (MyNavigableSet<T>) tailSet(fromElement);
        if (!inclusive) return tail;
        tail.add(floor(fromElement));
        return tail;
    }

    @Override
    public Comparator<? super T> comparator(){
        return comparator;
    }

    @Override
    /*
    Returns a view of the portion of this set whose elements range from fromElement, inclusive, to toElement, exclusive.
     */
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return subSet(fromElement, true, toElement,false);
    }

    @Override
    /*
    Returns a view of the portion of this set whose elements are strictly less than toElement.
     */
    public SortedSet<T> headSet(T toElement) {
        ArrayList<T> l = new ArrayList<>();
        for(T e : list) {
            if(comparator.compare(e,toElement)<0){
                l.add(toElement);
            }
        }
        return new MyNavigableSet<T>(l,comparator);
    }

    @Override
    /*
    Returns a view of the portion of this set whose elements are greater than or equal to fromElement.
     */
    public SortedSet<T> tailSet(T fromElement) {
        ArrayList<T> l1 = new ArrayList<>();
        for(T e : list) {
            if(comparator.compare(e,fromElement)>0) {
                l1.add(e);
            }
        }
        return new MyNavigableSet<>(l1,comparator);
    }

    @Override
    /*
    returns the first element of navi set
     */
    public T first() {
        return list.get(0);
    }

    @Override
    /*
    returns last element of navi set
     */
    public T last() {
        return list.get(list.size()-1);
    }

    @Override
    /*
    returns the size of navi set
     */
    public int size() {
        return list.size();
    }
}
