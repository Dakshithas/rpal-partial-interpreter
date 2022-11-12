
import java.util.ArrayList;
import java.util.Iterator;


public abstract class InternalNode extends Node {
    ArrayList<Node> children;
    boolean isStandardized = false;

    public InternalNode(Node parent) {
        super(parent);
        children = new ArrayList<>();
    }
    public void addChild(Node child) {
        children.add(child);
    }
    
    public void addChild(Node child, int index) {
        children.set(index, child);
    }

    public boolean isStandardized() {
        return isStandardized;
    }
    public ArrayList<Node> getChildren() {
        return children;
    }
    protected void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(this.getType());
        buffer.append('\n');
        if(this.children!=null){
            for (Iterator<Node> it = children.iterator(); it.hasNext();) {
                Node next = it.next();
                if (it.hasNext()) {
                    next.print(buffer, childrenPrefix + "|-- ", childrenPrefix + "|   ");
                } else {
                    next.print(buffer, childrenPrefix + "|__ ", childrenPrefix + "    ");
                }
            }
        }
    }
    public String print() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }
    
}