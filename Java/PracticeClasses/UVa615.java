import java.util.*;

class Main {
    // UVa Online Judge problem nr. 615
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // ID - ParentID pair
        HashMap<Integer, Integer> nodes = new HashMap<>();

        // Number of every test case
        int testCase = 1;

        // Set of all nodes
        HashSet<Integer> allNodes = new HashSet<>();
        boolean tree = true;

        while(true) {
            if (in.hasNextInt()) {
                int n1 = in.nextInt();
                int n2 = in.nextInt();

                if (n1 == 0 && n2 == 0) {
                    // Variable for holding the root key
                    int root = -1;
                    if(tree) {
                        // Check roots count (node without a parent)
                        int roots = 0;
                        for (int key: allNodes) {
                            if (!nodes.containsKey(key)){
                                roots++;
                                root = key;
                            }
                        }
                        if (roots != 1 && nodes.size() > 0) {
                            tree = false;
                        }
                    }
                    // Check if you can go to root from each node
                    if(tree && root > 0) {
                        mainloop:
                        for (int key: nodes.keySet()){
                            HashSet<Integer> visitedNodes = new HashSet<>();
                            while (key != root) {
                                if (visitedNodes.contains(key)){
                                    tree = false;
                                    break mainloop;
                                } else {
                                    visitedNodes.add(key);
                                    key = nodes.get(key);
                                }
                            }
                        }
                    }
                    // Verdict
                    if(tree){
                        System.out.println("Case " + testCase + " is a tree.");
                    } else {
                        System.out.println("Case " + testCase +
                                " is not a tree.");
                    }
                    // Start with new test case
                    testCase++;
                    tree = true;
                    nodes.clear();
                    allNodes.clear();
                } else if (n1 == -1 && n2 == -1) {
                    break;
                } else if (tree) {
                    if (nodes.containsKey(n2)){
                        tree = false;
                    } else {
                        allNodes.add(n1);
                        allNodes.add(n2);
                        nodes.put(n2, n1);
                    }
                }
            } else {
                in.next();
            }
        }
    }
}