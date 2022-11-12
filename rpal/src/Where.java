
import java.util.Objects;

public class Where extends BipolarNode implements Istandardizable {
    public Where(Node parent) {
        super(parent);
        type = "where";
    }

    @Override
    public void standardize() {

        if (Objects.equals(getRightChild().type, "=")) {
            InternalNode parent = (InternalNode) getParent();
            int index = parent.getChildren().indexOf(this);

            Node X = ((BipolarNode) getRightChild()).getLeftChild();
            Node E = ((BipolarNode) getRightChild()).getRightChild();
            Node P = getLeftChild();

            // _TrunkNode par = (_TrunkNode) getParent();
            // int thisIndex = par.getChildren().indexOf(this);
            // int level = getLevel();

            getRightChild().disconnected();
            disconnected();

            Gamma gamma = new Gamma(parent);
            Lambda lambda = new Lambda(gamma);
            parent.addChild(gamma, index);
            gamma.addChild(lambda);
            lambda.addChild(X);
            lambda.addChild(P);
            X.setParent(lambda);
            P.setParent(lambda);
            gamma.addChild(E);
            E.setParent(gamma);
            myrpal.addST(gamma);
            myrpal.addST(lambda);
            isStandardized = true;

        } else {
            myrpal.addST(this);
        }
    }

}