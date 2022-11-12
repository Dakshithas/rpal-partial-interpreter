
public abstract class Node {
    String type;
    private Node parent;
    boolean connected = true;

    public String toString(){
        return this.type;
    }

    protected abstract void print(StringBuilder buffer, String prefix, String childrenPrefix);
    protected abstract String print();

    public Node(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getLevel() {
        return this.parent.getLevel() + 1;
    }

    public String getType(){
        return this.type;
    }
    
    public void disconnected(){
        connected = false;
    }
 
}

