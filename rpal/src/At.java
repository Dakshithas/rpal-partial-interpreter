

public class At extends MultipolarNode implements Istandardizable {
    public At(Node parent) {
        super(parent);
        type = "@";
    }

    @Override
    public void standardize() {
        Node E1 = this.children.get(0);
        Node N =  this.children.get(1);
        Node E2 = this.children.get(2);
        

        InternalNode par = (InternalNode) getParent();
        int index = par.getChildren().indexOf(this);

        disconnected();

        Gamma gamma = new Gamma(par);
        par.addChild(gamma, index);

        Gamma secondgamma = new Gamma(gamma);
        
        gamma.addChild(secondgamma);
        gamma.addChild(E2);
        
        E2.setParent(gamma);

        secondgamma.addChild(N);
        secondgamma.addChild(E1);
        
        N.setParent(secondgamma);
        E1.setParent(secondgamma);


        myrpal.addST(gamma);
        myrpal.addST(secondgamma);
        isStandardized = true;
    }
}