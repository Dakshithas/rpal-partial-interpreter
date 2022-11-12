import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javax.sound.sampled.SourceDataLine;

public class myrpal {
    
    static ArrayList<String> inputLines;
    static ArrayList<String[]> childArray;
    static ArrayList<Node> AST, ST;
    static HashMap<Integer, Node> nodeCurrent;

    public static void main(String[] args) {
        //check whether the file name is given
            if(args.length == 0){
                System.out.println("Input filename should be provided");
                System.exit(0);
            }
            inputLines = readFile(args[0]);  //read the input file

            childArray = new ArrayList<>();
            nodeCurrent = new HashMap<>();
            AST = new ArrayList<>(); //abstract syntax tree - store give input file to tree
            ST = new ArrayList<>(); // standardize tree
            nodeCurrent.put(-1, new Root());
            countDots();
            createTree();
            if(Arrays.asList(args).contains("-ast")){
                System.out.println(AST.get(0).print()); //print abstract syntax tree
            }
            Standardizing(AST);
            if(Arrays.asList(args).contains("-st")){
                System.out.println(ST.get(0).print()); //print standardize tree
            }
            CSEmachine.getInstance().Evalutate(ST); //evalute ST
        
    }

    //read file
    public static ArrayList<String> readFile(String filepath) {
        File file = new File(filepath);
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                lines.add(st);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    
    // private static void printArray(ArrayList<String[]> arr){
    //     for(String[] array:arr){
    //         System.out.println(Arrays.toString(array));
    //     }
    // }

    //convert input file to tree
    private static void countDots() {
        for (String s : inputLines) {
            int count = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != '.') {
                    break;
                } else {
                    count += 1;
                    s = s.substring(1);
                    i -= 1;
                }
            }
            childArray.add(new String[]{s.strip(), String.valueOf(count)});
        }
    }

   //check whether the given input line is a child node
    private static boolean isToken(String s){
        if(s.startsWith("<") & s.endsWith(">")){
            return true;
        }
        return false;
    }
    
    private static void createTree() {
        for (String[] x : childArray) {
            Node parent = nodeCurrent.get(Integer.parseInt(x[1]) - 1);
            Node child = null;
            if(isToken(x[0])){ // process with child nodes
                String[] arr=x[0].replaceAll("[<>]","").split(":");
                switch(arr[0]){
                    case "ID":
                        child = new IDnode(parent, arr[1]);
                        break;
                    case "INT":
                        child = new INTnode(parent, Integer.parseInt(arr[1]));
                        break;
                    case "STR":
                        child = new STRnode(parent, arr[1].replaceAll("^\'|\'$", ""));
                        break;
                    case "true":
                        child = new BooleanNode(parent,true);
                        break;
                    case "false":
                        child = new BooleanNode(parent,false);
                        break;
                    default:
                        child = new NilNode(parent);
                        break;
                }
            }else{
                child = createNode(x[0], parent);
            }
            if (nodeCurrent.containsKey(Integer.parseInt(x[1]))) {
                // System.out.printf("node current is %s\n",child.type);
                nodeCurrent.replace(Integer.parseInt(x[1]), child);
            } else {
                nodeCurrent.put(Integer.parseInt(x[1]), child);
            }
            AST.add(child);
            setChildRelations(parent, child);
        }
    }

    //create internal nodes
    private static Node createNode(String x, Node parent) {
        // System.out.println(x);
        Node returnNode;
        if (Objects.equals(x, "let")) {
            returnNode = new Let(parent);
        } else if (Objects.equals(x, "lambda")) {
            returnNode = new Lambda(parent);
        } else if (Objects.equals(x, "where")) {
            returnNode = new Where(parent);
        } else if (Objects.equals(x, "tau")) {
            returnNode = new Tau(parent);
        } else if (Objects.equals(x, "aug")) {
            returnNode = new Aug(parent);
        } else if (Objects.equals(x, "->")) {
            returnNode = new Arrow(parent);
        } else if (Objects.equals(x, "or")) {
            returnNode = new Or(parent);
        } else if (Objects.equals(x, "&")) {
            returnNode = new bAnd(parent);
        } else if (Objects.equals(x, "not")) {
            returnNode = new Not(parent);
        } else if (Objects.equals(x, "gr")) {
            returnNode = new Greater(parent);
        } else if (Objects.equals(x, "ge")) {
            returnNode = new GreatEqual(parent);
        } else if (Objects.equals(x, "ls")) {
            returnNode = new Lesser(parent);
        } else if (Objects.equals(x, "le")) {
            returnNode = new LessEqual(parent);
        } else if (Objects.equals(x, "eq")) {
            returnNode = new bEqual(parent);
        } else if (Objects.equals(x, "ne")) {
            returnNode = new NotEqual(parent);
        } else if (Objects.equals(x, "+")) {
            returnNode = new Plus(parent);
        } else if (Objects.equals(x, "-")) {
            returnNode = new Minus(parent);
        } else if (Objects.equals(x, "neg")) {
            returnNode = new Negative(parent);
        } else if (Objects.equals(x, "*")) {
            returnNode = new Star(parent);
        } else if (Objects.equals(x, "/")) {
            returnNode = new Slash(parent);
        } else if (Objects.equals(x, "**")) {
            returnNode = new DoubleStar(parent);
        } else if (Objects.equals(x, "@")) {
            returnNode = new At(parent);
        } else if (Objects.equals(x, "gamma")) {
            returnNode = new Gamma(parent);
        } else if (Objects.equals(x, "true")) {
            returnNode = new BooleanNode(parent, true);
        } else if (Objects.equals(x, "false")) {
            returnNode = new BooleanNode(parent, false);
        } else if (Objects.equals(x, "nil")) {
            returnNode = new NilNode(parent);
        } else if (Objects.equals(x, "dummy")) {
            returnNode = new DummyNode(parent);
        } else if (Objects.equals(x, "within")) {
            returnNode = new Within(parent);
        } else if (Objects.equals(x, "and")) {
            returnNode = new And(parent);
        } else if (Objects.equals(x, "rec")) {
            returnNode = new Rec(parent);
        } else if (Objects.equals(x, "=")) {
            returnNode = new Equal(parent);
        } else if (Objects.equals(x, "function_form")) {
            returnNode = new FunctionForm(parent);
        } else if (Objects.equals(x, "()")) {
            returnNode = new ParenthesisNode(parent);
        } else if (Objects.equals(x, ",")) {
            returnNode = new Comma(parent);
        } else {
            returnNode = new DummyNode(parent);
        }
        return returnNode;
    }

    //add relevent children to each nodes children arraylist
    private static void setChildRelations(Node parent, Node child) {
        if (parent != null && child != null && (parent instanceof InternalNode)) {
            ((InternalNode) parent).addChild(child);
        }
    }

    public static void addST(Node node) {
        ST.add(node);
    }

    //standadizing until all node get standardize
    private static void Standardizing(ArrayList<Node> currentTree) {
        boolean t = true;
        ArrayList<Node> TempTree = currentTree;
        while (t) {
            ST = new ArrayList<>();
            t = Standardization(TempTree);
            TempTree = new ArrayList<>(ST);
        }
    }

    
    private static boolean Standardization(ArrayList<Node> tree) { //standardize each node
        boolean didAnyStandardized = false;
        for (Node n : tree) {
//            System.out.println(n);
            if ((n instanceof Istandardizable) && (!((InternalNode) n).isStandardized())) {
                ((Istandardizable) n).standardize();
                didAnyStandardized = didAnyStandardized || ((InternalNode) n).isStandardized();
            } else if (!(n instanceof Istandardizable) && (n.connected)) {
                addST(n);
            }
        }
        return didAnyStandardized;
    }
}

