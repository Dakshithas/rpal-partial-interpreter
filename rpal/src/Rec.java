
import java.util.Objects;

public class Rec extends UnipolarNode implements Istandardizable {
    public Rec(Node parent) {
        super(parent);
        type = "rec";
    }

    @Override
    public void standardize() {
        if (Objects.equals(getChild().type, "=")) {
            Node X = ((BipolarNode) getChild()).getLeftChild();
            Node X2 = new IDnode(X.getParent(), ((IDnode) X).getId());

            Node E = ((BipolarNode) getChild()).getRightChild();

            InternalNode parent = (InternalNode) getParent();
            int thisIndex = parent.getChildren().indexOf(this);

            getChild().disconnected();
            disconnected();

            Equal eq = new Equal(parent);
            parent.addChild(eq, thisIndex);

            eq.addChild(X2);
            X2.setParent(eq);

            Gamma gamma = new Gamma(eq);
            eq.addChild(gamma);

            YStarNode y = new YStarNode(gamma);
            gamma.addChild(y);
            Lambda lambda = new Lambda(gamma);
            gamma.addChild(lambda);

            lambda.addChild(X);
            X.setParent(lambda);
            lambda.addChild(E);
            E.setParent(lambda);

            myrpal.addST(eq);
            myrpal.addST(X2);
            myrpal.addST(gamma);
            myrpal.addST(y);
            myrpal.addST(lambda);
            isStandardized = true;

        } else {
            myrpal.addST(this);
        }
    }
}