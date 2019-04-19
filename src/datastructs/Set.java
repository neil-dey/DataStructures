package datastructs;

public class Set<E extends Comparable<E>> {
    private List<Node> forest;
    Dictionary<E, Node> locations;

    public Set() {
        this.forest = new ArrayList<Node>();
        locations = new HashTable<E, Node>();
    }

    public void makeSet(E element) {
        forest.add(new Node(element));
    }

    public E find(E element) {
        Node n = locations.lookUp(element);
        return n.find(n).value;
    }

    public void union(E e1, E e2) {
        Node n1 = locations.lookUp(e1);
        Node n2 = locations.lookUp(e2);
        n1 = n1.find(n1);
        n2 = n2.find(n2);
        n1.union(n2);
    }

    public int getNumInSet(E e) {
        Node n = locations.lookUp(e);
        return n.find(n).count;
    }

    private class Node {
        E value;
        Node parent;
        int count;

        public Node(E value) {
            this.value = value;
            this.count = 1;
            this.parent = this;
            locations.insert(value, this);
        }

        private Node find(Node p) {
            if (p.parent != p) {
                p.parent = find(p.parent);
            }
            return p.parent;
        }

        public void union(Node s) {
            if (s.count > this.count) {
                s.count = s.count + this.count;
                this.parent = s;
                forest.remove(this);
            } else {
                this.count = this.count + s.count;
                s.parent = this;
                forest.remove(s);
            }
        }

        // public String toString() {
        // return "Val: " + this.value + " Cnt:" + this.count;
        // }
    }
}
