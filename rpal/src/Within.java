
import java.util.Objects;

public class Within extends BipolarNode implements Istandardizable {
    public Within(Node parent) {
        super(parent);
        type = "within";
    }

    @Override
    public void standardize() {

        if (Objects.equals(getLeftChild().type, "=") && Objects.equals(getRightChild().type, "=")) {
            Node X1 = ((BipolarNode) getLeftChild()).getLeftChild();
            Node E1 = ((BipolarNode) getLeftChild()).getRightChild();
            Node X2 = ((BipolarNode) getRightChild()).getLeftChild();
            Node E2 = ((BipolarNode) getRightChild()).getRightChild();

            InternalNode par = (InternalNode) getParent();
            int thisIndex = par.getChildren().indexOf(this);
            int level = getLevel();

            disconnected();
            getLeftChild().disconnected();
            getRightChild().disconnected();

            Equal eq = new Equal(par);
            par.addChild(eq, thisIndex);
            eq.addChild(X2);
            X2.setParent(eq);

            Gamma gamma = new Gamma(eq);
            Lambda lambda = new Lambda(gamma);
            
            eq.addChild(gamma);
            gamma.setParent(eq);
            
            gamma.addChild(lambda);
            lambda.setParent(gamma);
            
            gamma.addChild(E1);
            E1.setParent(gamma);
            
            lambda.addChild(X1);
            X1.setParent(lambda);
            lambda.addChild(E2);
            E2.setParent(lambda);
            
            

            myrpal.addST(eq);
            myrpal.addST(gamma);
            myrpal.addST(lambda);
            isStandardized = true;

        } else {
            myrpal.addST(this);
        }
    }
}