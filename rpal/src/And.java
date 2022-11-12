
import java.util.ArrayList;

public class And extends MultipolarNode implements Istandardizable {
    public And(Node parent) {
        super(parent);
        type="and";
    }

    public ArrayList<Node> getElements() {
        return children;
    }

    public int getElementCount() {
        return children.size();
    }

    @Override
    public void standardize() {

        boolean validate = true;
        ArrayList<Node> elements = getElements();
        for (Node el : elements) {
            if (el.type != "=") {
                validate = false;
                break;
            }
        }

        if (validate) {
            InternalNode parent = (InternalNode) getParent();
            int index = parent.getChildren().indexOf(this);

            disconnected();
            for (Node el : elements) {
                el.disconnected();
            }

            Equal equal = new Equal(parent);
            parent.addChild(equal, index);

            Comma comma = new Comma(equal);
            equal.addChild(comma);

            Tau tau = new Tau(equal);
            equal.addChild(tau);

            for (Node eq : elements) {
                Node X = ((BipolarNode) eq).getLeftChild();
                Node E = ((BipolarNode) eq).getRightChild();
                comma.addChild(X);
                tau.addChild(E);
                X.setParent(comma);
                E.setParent(tau);
            }

            myrpal.addST(equal);
            myrpal.addST(comma);
            myrpal.addST(tau);
            isStandardized = true;
        }
        else {
            myrpal.addST(this);
        }
    }
}
