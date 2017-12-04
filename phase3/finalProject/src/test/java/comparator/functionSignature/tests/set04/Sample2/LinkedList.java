
package comparator.functionSignature.tests.set04.Sample2; import java.util.NoSuchElementException; public class LinkedList { public LinkedList() { first = null; last = null; length = 0; } public boolean isEmpty() { return (first == null); } public int length() { return length; } public void add(Object data) { Node<Object> nptr = new Node<Object>(data, null, null); if (first == null) { first = nptr; last = first; } else { first.setLinkPrev(nptr); nptr.setLinkNext(first); first = nptr; } length++; } public void append(int data)	{ Node<Object> nptr = new Node<Object>(data, null, null); if (first == null) { first = nptr; last = first; } else { nptr.setLinkPrev(last);last.setLinkNext(nptr); last = nptr; } length++; } public void deleteAtPos(int pos) { if (pos == 1) { if (length == 1) { first = null; last = null; length = 0; return; } first = first.getLinkNext(); first.setLinkPrev(null); length--; return; } if (pos == length) { last = last.getLinkPrev(); last.setLinkNext(null); length--; } Node<Object> ptr = first.getLinkNext(); for (int i = 2; i <= length; i++) { if (i == pos) { Node<Object> p = ptr.getLinkPrev(); Node<Object> n = ptr.getLinkNext(); p.setLinkNext(n); n.setLinkPrev(p); length--; return; } ptr = ptr.getLinkNext(); } } public Object first() { if (isEmpty()) { throw new NoSuchElementException("Empty list"); } return first.data; } public Object last() { if (isEmpty()) { throw new NoSuchElementException("Empty list"); } return last.data; } public String toString() { String ret = ""; Node<Object> current = first; while (current!= null ) { ret += current.data + " -> "; current = current.n; } return ret; } protected Node<Object> first; protected Node<Object> last; public int length; }
