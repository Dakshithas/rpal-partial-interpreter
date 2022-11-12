
import java.util.ArrayList;

public class MultiLambda extends MultipolarNode implements Istandardizable {
    public MultiLambda(Node parent) {
        super(parent);
        type = "multilambda";
    }


    public Node getExpression(){
        return children.get(children.size()-1);
    }

    public int getVariableCount() {
        return children.size() - 2;
    }

    public ArrayList<Node> getVariables() {
        return new ArrayList<>(children.subList(1, children.size() - 1));
    }

    @Override
    public void standardize() {

        Node E = getExpression();
        ArrayList<Node> Vs = getVariables();
        int varCount = getVariableCount();

        InternalNode par = (InternalNode) getParent();
        int thisIndex = par.getChildren().indexOf(this);
        int level = getLevel();

        disconnected();

        Node lambda = standerizeMultiLambda(varCount, par, level, new ArrayList<>(Vs.subList(0, Vs.size())),E);

        par.addChild(lambda, thisIndex);
        isStandardized = true;
    }

    private Node standerizeMultiLambda(int count, Node parent, int lev, ArrayList<Node> vars, Node E) {
        Lambda lambda = new Lambda(parent);
        myrpal.addST(lambda);
        lambda.addChild(vars.get(0));

        if (count < 2) {
            lambda.addChild(E);
            E.setParent(lambda);
            return lambda;
        } else {
            Node k = standerizeMultiLambda(count - 1, lambda, lev + 1, new ArrayList<>(vars.subList(1, vars.size())),E);
            lambda.addChild(k);
            return lambda;
        }
    }
}
