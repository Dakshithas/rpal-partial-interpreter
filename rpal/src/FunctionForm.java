
import java.util.ArrayList;

public class FunctionForm extends MultipolarNode implements Istandardizable {
    public FunctionForm(Node parent) {
        super(parent);
        type = "function_form";
    }

    public Node getIdentifier() {
        return children.get(0);
    }

    public int getVariableCount() {
        return children.size() - 2;
    }

    public ArrayList<Node> getVariables() {
        return new ArrayList<>(children.subList(1, children.size() - 1));
    }

    public Node getExpression() {
        return children.get(children.size() - 1);
    }

    @Override
    public void standardize() {
        Node P = getIdentifier();
        ArrayList<Node> Vs = getVariables();
        int varCount = getVariableCount();
        Node E = getExpression();
        int level = getLevel();
        InternalNode par = (InternalNode) getParent();
        int thisIndex = par.getChildren().indexOf(this);

        disconnected();

        Equal eq = new Equal(par);
        eq.addChild(P);
        P.setParent(eq);
        eq.addChild(standerizeLambda(varCount, eq, level, new ArrayList<>(Vs.subList(0, Vs.size())), E));

        par.addChild(eq, thisIndex);
        myrpal.addST(eq);
        isStandardized = true;
    }

    private Node standerizeLambda(int count, Node parent, int lev, ArrayList<Node> vars, Node E) {
        Lambda lambda = new Lambda(parent);
        myrpal.addST(lambda);
        lambda.addChild(vars.get(0));
        vars.get(0).setParent(lambda);
        if (count < 2) {
            lambda.addChild(E);
            E.setParent(lambda);
            return lambda;
        } else {
            Node k = standerizeLambda(count - 1, lambda, lev + 1, new ArrayList<>(vars.subList(1, vars.size())), E);
            lambda.addChild(k);
            k.setParent(lambda);
            return lambda;
        }
    }
}