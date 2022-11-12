
import java.util.ArrayList;

public abstract class MultipolarNode extends InternalNode {
    public MultipolarNode(Node parent) {
        super(parent);
    }
}


class Arrow extends MultipolarNode {

    public Arrow(Node parent) {
        super(parent);
        type = "->";
    }

    public Node getElseClause() {
        return children.get(2);
    }

    public Node getIfStatement() {
        return children.get(0);
    }

    public Node getThenClause() {
        return children.get(1);
    }

}
class Comma extends MultipolarNode {

    public Comma(Node parent) {
        super(parent);
        type = ",";
    }
}


class Tau extends MultipolarNode{

    public Tau(Node parent) {
        super(parent);
        type = "tau";
    }

    public ArrayList<Node> getElements() {
        return children;
    }

    public int getElementCount() {
        return children.size();
    }

}