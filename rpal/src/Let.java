
import java.util.Objects;

public class Let extends BipolarNode implements Istandardizable {
    public Let(Node parent) {
        super(parent);
        type = "let";
    }

    @Override
    public void standardize() {
        if (Objects.equals(getLeftChild().type, "=")) {
            InternalNode parent = (InternalNode) getParent();
            int index = parent.getChildren().indexOf(this);

            Node X = ((BipolarNode)getLeftChild()).getLeftChild();
            Node E = ((BipolarNode)getLeftChild()).getRightChild();
            Node P = getRightChild();
            // _TrunkNode par = (_TrunkNode)getParent();
            // int thisIndex = par.getChildren().indexOf(this);
            // int level = getLevel();

            disconnected();
            getLeftChild().disconnected();

            Gamma gamma = new Gamma(parent);
            Lambda lambda = new Lambda(gamma);
            parent.addChild(gamma,index);
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

        }else {
            myrpal.addST(this);
        }
    }
}