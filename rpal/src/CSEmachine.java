

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class CSEmachine {

    private Stack<Node> valueStack;
    private Stack<Node> controlStack;
    private ArrayList<Node> StructuredTree;
    private ArrayList<Delta> deltas;
    private Delta rootDelta;
    private Delta currentDelta;
    private Environment primitiveEnvironment;
    private Environment currentEnvironment;
    private int envCount = 0;

    private static CSEmachine instance = null;

    private CSEmachine(){}

    public static CSEmachine getInstance() {
        if (instance == null){
            instance = new CSEmachine();
        }
           return instance;
    }

    public void Evalutate(ArrayList<Node> ST) {
        StructuredTree = ST;
        controlStack = new Stack<>();
        valueStack = new Stack<>();
        deltas = new ArrayList<>();
        primitiveEnvironment = new Environment(null, 0);
        envCount++;
        rootDelta = new Delta(null);
        rootDelta.setBindings(null);
        deltas.add(rootDelta);
        Node root = null;
        for (Node b : ST) {
            if (b.getParent() instanceof Root) {
                root = b;
                break;
            }
        }
        traverseTree(root, rootDelta);
//        System.out.println(deltas);
        evaluateMachine();
    }

    public void traverseTree(Node root, Delta delta) {
        switch(root.type){
            case "lambda":
                Delta curDelta = new Delta(delta);
                
                Lambda lambdaRoot = (Lambda) root;
                if (lambdaRoot.getLeftChild() instanceof Comma) {
                    Comma com = (Comma) lambdaRoot.getLeftChild();
                    int varCount = com.getChildren().size();
                    for (int j = 0; j < varCount; j++) {
                        curDelta.setBindings(com.getChildren().get(j));
                    }
                } else {
                    curDelta.setBindings(lambdaRoot.getLeftChild());
                }
                deltas.add(curDelta);
                traverseTree(lambdaRoot.getRightChild(), curDelta);
                delta.pushValue(curDelta);
                break;
            case "->":
//                Delta falseDelta = new Delta(root);
//        traverseTree(((Arrow) root).getElseClause(), falseDelta);
//        delta.pushValue(falseDelta);
//        Delta trueDelta = new Delta(root);
//        traverseTree(((Arrow) root).getThenClause(), trueDelta);
//        delta.pushValue(trueDelta);
//        delta.pushValue(new Beta(root));
//        traverseTree(((Arrow) root).getIfStatement(), delta);
                Delta thenDelta = new Delta(delta);
                Delta elseDelta = new Delta(delta);
                Beta beeta = new Beta(root);

                delta.pushValue(thenDelta);
                delta.pushValue(elseDelta);
                delta.pushValue(beeta);
                
                traverseTree(((Arrow) root).getIfStatement(), delta);
                traverseTree(((Arrow) root).getThenClause(), thenDelta);
                traverseTree(((Arrow) root).getElseClause(), elseDelta);
                
                
           
                break;
            default:
                delta.pushValue(root);
                if(root instanceof InternalNode){
                    ArrayList<Node> children=((InternalNode) root).getChildren();
                    for (Node child : children) {
                        traverseTree(child, delta);
                    }
                }
        }
    }


    private void AddDeltaToControlStack(Delta delta) {
        for (int f = 0; f < delta.getValuestack().size(); f++) {
            controlStack.push(delta.getValuestack().elementAt(f));
        }
    }

    //Start eveluating the flattened deltas
    private void evaluateMachine() {
        currentDelta = rootDelta;
        currentEnvironment = primitiveEnvironment;
        controlStack.push(currentEnvironment);
        valueStack.push(currentEnvironment);
        AddDeltaToControlStack(rootDelta);
        while (controlStack.size() > 0) {
            evaluateRound();
        }
        System.out.println("");
    }

    private void evaluateRound() {
//        System.out.printf("%-70s %70s\n",controlStack,valueStack);
//        System.out.printf("control stack%s - \n",controlStack);
//        System.out.printf("value stack%s - \n",valueStack);
        Node m = controlStack.pop();
//        System.out.println(m);
        if (m instanceof Operator) {
            performOperation(m);
        } else if (m instanceof IDnode) {
//            System.out.println("00000000000000000000000000000000000000");
            handleIdentifier(m);
        } else if (m instanceof Gamma) {
            applyGamma(m);
        } else if (m instanceof Beta) {
            applyArrowFunction(m);
        } else if (m instanceof Tau) {
            createTuple(m);
        } else if (m instanceof NilNode) {
            valueStack.push(new Tuple(m));
        } else if (m instanceof Environment) {
            removeEnvironment(m);
        } else if (m instanceof Delta) {
            ((Delta) m).setLinkedEnv(currentEnvironment);
            valueStack.push(m);
        } else {
            valueStack.push(m);
        }
    }
    
        private void performOperation(Node operator){
        String operatorType = operator.type;
        Node value1 = valueStack.pop();
        Node node=null;
        switch(operatorType){
            case "not":
                BooleanNode bool = (BooleanNode) value1;
                node=new BooleanNode(operator.getParent(), !bool.getBooleanValue());
                break;
            case "neg":
                INTnode inti = (INTnode) value1;
                node=new INTnode(operator.getParent(), (inti.getInt() * (-1)));
                break;
            default:
                Node value2 = valueStack.pop();
                if(value1 instanceof INTnode && value2 instanceof INTnode){
                    INTnode val1 = (INTnode) value1;
                    INTnode val2 = (INTnode) value2;
                    switch(operatorType){
                        case "+":
                            node=new INTnode(operator.getParent(), val1.getInt() + val2.getInt());
                            break;
                        case "-":
                            node=new INTnode(operator.getParent(), val1.getInt() - val2.getInt());
                            break;
                        case "*":
                            node=new INTnode(operator.getParent(), val1.getInt() * val2.getInt());
                            break;
                        case "/":
                            node=new INTnode(operator.getParent(), val1.getInt() / val2.getInt());
                            break;
                        case "**":
                            node=new INTnode(operator.getParent(), (int)Math.pow(val1.getInt(),val2.getInt()));
                            break;
                        case "ge":
                            node=new BooleanNode(operator.getParent(), val1.getInt() >= val2.getInt());
                            break;
                        case "gr":
                            node=new BooleanNode(operator.getParent(), val1.getInt() > val2.getInt());
                            break;
                        case "le":
                            node=new BooleanNode(operator.getParent(), val1.getInt() <= val2.getInt());
                            break;
                        case "ls":
                            node=new BooleanNode(operator.getParent(), val1.getInt() < val2.getInt());
                            break;
                        case "eq":
//                            System.out.println(val1.getInt() == val2.getInt());
                            node=new BooleanNode(operator.getParent(), val1.getInt() == val2.getInt());
                            
                            break;
                        case "ne":
                            node=new BooleanNode(operator.getParent(), val1.getInt() != val2.getInt());
                            break;
                    }

                }else if(value1 instanceof STRnode && value2 instanceof STRnode){
                    STRnode val1 = (STRnode) value1;
                    STRnode val2 = (STRnode) value2;
                    switch(operatorType){
                        case "eq":
                            node=new BooleanNode(operator.getParent(), val1.getString() == val2.getString());
                            break;
                        case "ne":
                            node=new BooleanNode(operator.getParent(), val1.getString() != val2.getString());
                            break;
                    }
                }else if(value1 instanceof BooleanNode && value2 instanceof BooleanNode){
                    BooleanNode val1 = (BooleanNode) value1;
                    BooleanNode val2 = (BooleanNode) value2;
                    switch(operatorType){
                        case "&":
                            node=new BooleanNode(operator.getParent(), val1.getBooleanValue() && val2.getBooleanValue());
                            break;
                        case "or":
                            node=new BooleanNode(operator.getParent(), val1.getBooleanValue() || val2.getBooleanValue());
                            break;
                    }
                    
                }else if(operatorType.equals("aug")){
                    node = (Tuple) value1;
                    ((Tuple)node).addValue(value2);
                }else {
                System.out.println("Some error is Operations");
            }
        }
        if(node!=null){
            valueStack.push(node);
        }
    }

    private void handleIdentifier(Node identifier) {
//        System.out.printf("control stack%s - \n",controlStack);
//        System.out.printf("value stack%s - \n",valueStack);
//        System.out.println(identifier);

        String name = ((IDnode) identifier).getLeafValue();
        Node node = null;
        node = goThroughEnvironments(name, currentEnvironment);
        if (node == null) {
            Node rand = valueStack.pop();
            Node gamma = controlStack.pop();
            switch (name) {
                case "Isinteger":
                    node = new BooleanNode(rand, rand instanceof INTnode);
                    break;
                case "Istruthvalue":
                    node = new BooleanNode(rand, rand instanceof BooleanNode);
                    break;
                case "Isstring":
                    node = new BooleanNode(rand, rand instanceof STRnode);
                    break;
                case "Istuple":
                    node = new BooleanNode(rand, rand instanceof Tuple);
                    break;
                case "Isfunction":
                    node = new BooleanNode(rand, rand instanceof Delta);
                    break;
                case "Isdummy":
                    node = new BooleanNode(rand, rand instanceof DummyNode);
                    break;
                case "Stem":
                    node = new STRnode(rand, ((STRnode) rand).getLeafValue().substring(0, 1));
                    break;
                case "Stern":
                    node = new STRnode(rand, ((STRnode) rand).getLeafValue().substring(1));
                    break;
                default:
                    name = name.substring(0, 1).toUpperCase() + name.substring(1);
                    switch (name) {
                        case "Print":
                            if (rand instanceof ValueNode) {
                                String printText=((ValueNode) rand).getLeafValue().replace("\\n","\n");
                                System.out.print(printText);
                            } else if (rand instanceof Tuple) {
                                printTuple((Tuple) rand);
                            }
                            node = new DummyNode(rand);
                            break;
                        case "Conc":
                            Node secondLetter = valueStack.pop();
                            controlStack.pop();
                            node = new STRnode(rand, ((STRnode) rand).getLeafValue().concat(((STRnode) secondLetter).getLeafValue()));
                            break;
                        case "Order":
                            node = new INTnode(rand, ((Tuple) rand).getCount());
                            break;
                    }

            }
            if (node == null) {
                valueStack.push(rand);
                controlStack.push(gamma);
            }
        }
        if (node != null) {
            valueStack.push(node);
        } 
//        else {
////            valueStack.push(rand);
////            controlStack.push(gamma);
//            lookUpEnvironment(identifier);
//        }
    }
    private void lookUpEnvironment(Node m) {
        IDnode iDnode = (IDnode) m;
        Node value = goThroughEnvironments(iDnode.getLeafValue(), currentEnvironment);
        if (value != null) {
            valueStack.push(value);
        } else {
            System.out.println("Error: " + iDnode.getLeafValue() + " not found");
        }

    }

    private Node goThroughEnvironments(String s, Environment env) {
        if (env.hasValue(s)) {
            return env.getValue(s);
        } else if (env.getParent() == null) {
            return null;
        } else {
            return goThroughEnvironments(s, (Environment) env.getParent());
        }
    }

    private void applyGamma(Node m) {
        Node rator = valueStack.pop();
        if (rator instanceof Delta) {
            Delta del = (Delta) rator;
            currentDelta = del;
            Delta currentDelta = (Delta) rator;
            currentEnvironment = new Environment(currentDelta.getLinkedEnv(), envCount);
            envCount++;
            int bindingcount = currentDelta.getBindingcount();

            if (bindingcount < 2) {
                Node r = valueStack.pop();
//                System.out.println("__________________________________________");
//                System.out.println(r);
                currentEnvironment.addValue(((ValueNode) currentDelta.getBindingAt(0)).getLeafValue(), r);
//                System.out.println("__________________________________________");
            } else {
                Tuple tup = (Tuple) valueStack.pop();
                for (int h = 0; h < bindingcount; h++) {
                    currentEnvironment.addValue(((ValueNode) currentDelta.getBindingAt(h)).getLeafValue(), tup.getValueAt(h + 1));
                }
            }
            valueStack.push(currentEnvironment);
            controlStack.push(currentEnvironment);
            AddDeltaToControlStack(currentDelta);
        } else if (rator instanceof YStarNode) {
            Delta del = (Delta) valueStack.pop();
            valueStack.push(new Eta(rator, del));
        } else if (rator instanceof Tuple) {
            Tuple tup = (Tuple) rator;
            INTnode index = (INTnode) valueStack.pop();
            Node retrieved = tup.getValueAt(index.getInt());
            valueStack.push(retrieved);
        } else if (rator instanceof Eta) {
            controlStack.push(m);
            controlStack.push(new Gamma(m));
            Delta del = ((Eta) rator).getLinkedDelta();
            valueStack.push(rator);
            valueStack.push(del);
        } else {
            System.out.println("Error in gamma operation");
        }

    }

    private void applyArrowFunction(Node beta) {
        Node truthVal = valueStack.pop();
        if (truthVal instanceof BooleanNode) {
            boolean val = ((BooleanNode) truthVal).getBooleanValue();
            Delta falseDel = (Delta) controlStack.pop();
            Delta trueDel = (Delta) controlStack.pop();
            if (val) {
                AddDeltaToControlStack(trueDel);
            } else {
                AddDeltaToControlStack(falseDel);
            }
        } else {
            System.out.println("Error in Arrow Function operation");
        }
    }
    
    private void createTuple(Node m) {
        Tau t = (Tau) m;
        int count = t.getElementCount();
//        System.out.println(t.getChildren());
//        System.out.println("++++++++++++++++++++++++++++++++++");
//        System.out.println(count);
        Tuple tup = new Tuple(t);
        for (int v = 0; v < count; v++) {
            Node n = valueStack.pop();
            tup.addValue(n);
        }
        valueStack.push(tup);
    }
    private void removeEnvironment(Node m) {
        Node valueBeforeEnv = valueStack.pop();
        Node stackEnv = valueStack.pop();
        if (m == stackEnv) {
            valueStack.push(valueBeforeEnv);
            if (currentDelta != rootDelta) {
                currentDelta = (Delta) currentDelta.getParent();
            }
            if (currentEnvironment != primitiveEnvironment) {
                currentEnvironment = findPreviousEnvironment();
            }
        } else {
            System.out.println("Error in Remove Env operation");
        }
    }
    private Environment findPreviousEnvironment() {
        Environment pre = null;
        for (int h = valueStack.size() - 1; h >= 0; h--) {
            Node m = valueStack.elementAt(h);
            if ((m instanceof Environment) && m != currentEnvironment) {
                pre = (Environment) m;
                break;
            }
        }
        return pre;
    }
    private void printTuple(Tuple rand) {
        System.out.print("(");
        int count = rand.getCount();
        String[] elements = new String[count];
        for(int u = 0; u<count; u++){
            elements[u] = (((ValueNode)rand.getValueAt(u+1)).getLeafValue());
        }
        String out = String.join(", ",elements);
        System.out.print(out);
        System.out.print(")");
        System.out.print("\n");
    }

}