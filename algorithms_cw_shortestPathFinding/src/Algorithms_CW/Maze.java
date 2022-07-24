package Algorithms_CW;

import java.io.*;
import java.util.*;

public class Maze {
    private final String Files;
    private char[][] arrayOne2D;

    private static int m = 0;
    private static int n = 0;

    static List<List<String>> grid = new ArrayList<>();
    static List<List<Boolean>> visited= new ArrayList<>();

    ArrayList<String> duration = new ArrayList<>();

    private Node startingNode;

    public Maze(String file) throws IOException {
        this.Files = file;
        input_rows(file);
    }

    public static void input_rows(String filename){
        String filenames = filename;

        try {
            File myObj = new File(filenames);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                grid.add(List.of(data.strip().split("")));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for(int i =0; i < grid.size(); i++){
            List<Boolean> temp= new ArrayList<>();
            for(int j=0; j < grid.get(0).size(); j++){
                temp.add(false);
            }
            visited.add(temp);
        }
        System.out.println("\nSelected maze: "+filename +"\n");
    }

    public static int[] find_location(String symbol){
        m = grid.size();         // row size
        n = grid.get(0).size();  // column size

        int[] temp = new int[2];

        for(int i =0; i < m; i++){
            for(int j =0; j < n; j++){
                if(grid.get(i).get(j).equals(symbol)){
                    temp = new int[]{j,i};
                    break;
                }
            }
        }
        return temp;
    }

    public void printPattern() {

        for(int i = 0; i < grid.size(); i++) {
            System.out.println(grid.get(i));
        }
        System.out.println();
        int[] location = find_location("S");
        startingNode = new Node(location[0],location[1]);
    }

    //_____________________________path finding using BFS_____________________________

    public Node[] BFSPath() {

        Stack<Node> paths = new Stack<>();  //stack called paths FIF0
        Node[] shortestPath = null;         //node lisst array

        LinkedList<Node> parentNode = new LinkedList<>(); //STORE PARENTS
        Queue<Node> queue = new LinkedList<>();           //queue to add nodes
        queue.offer(startingNode);
        visited(startingNode);

        while (!queue.isEmpty()) {

            Node location = queue.poll(); //removes and return a element from the front of the queue
            parentNode.add(location);    //adding that element to the parent linkedlist

            if (!endPoint(location)) {
                //check if that element is equals to F
                Node nextBlock = new Node(location.x + 1, location.y, location, "Go right"); //moves right of the current element position

                if (isVisitable(nextBlock)) { //checks if it visitable
                    queue.offer(nextBlock); //inserts this element to the queue immeditely
                    visited(nextBlock); //makes the element visited
                }

                nextBlock = new Node(location.x, location.y + 1, location, "Go down");
                if (isVisitable(nextBlock)) {
                    queue.offer(nextBlock);
                    visited(nextBlock);
                }

                nextBlock= new Node(location.x - 1, location.y, location, "Go left");
                if (isVisitable(nextBlock)) {
                    queue.offer(nextBlock);
                    visited(nextBlock);
                }

                nextBlock = new Node(location.x, location.y - 1, location, "Go up");
                if (isVisitable(nextBlock)) {
                    queue.offer(nextBlock);
                    visited(nextBlock);
                }
            } else {
                break; //if path is found break
            }
        }

        Node position = parentNode.getLast(); //returns the last element in the parent linkedlist....finishing node is the last node

        if (position != null) {
            do {
                paths.push(position);//adding to stack
                position = position.previousPoint; // getting parent of node position

            } while (position != null);//going back parent to parent...start node has no parent node..therfoe its null
            shortestPath = new Node[paths.size()];//11

            int i = 0;
            while (!paths.isEmpty()) {
                shortestPath[i++] = paths.pop();//popping from the stack and to add to array
            }
        }
        return shortestPath;
    }

    private boolean isVisitable(Node position) {
        return (position.y >= 0 && position.x >= 0 && position.y < grid.size() && position.x < grid.get(position.y).size()) && (!visited.get(position.y).get(position.x)) && (grid.get(position.y).get(position.x).equals(".") || endPoint(position));
    }

    private boolean endPoint(Node position) {
        return grid.get(position.y).get(position.x).equals("F");
    }

    private void visited(Node position) {
        if (grid.get(position.y).get(position.x).equals(".")) {
            visited.get(position.y).set(position.x, true);
        }
    }

    public void findThePath() throws IOException {

        long startTime = System.nanoTime();

        Node[] paths = this.BFSPath();
        if (paths != null) {
            int count = 1;

            System.out.println(count + ". Start at " + "["+ (paths[0].x+1) + "," + (paths[0].y+1) + "]" );
            for(int i = 1; i < paths.length; i++) {
                count++;
                System.out.println(count + ". " +  paths[i].instruction + " to ["+ (paths[i].x+1) + "," + (paths[i].y+1) + "]" );
            }
            count++;
            System.out.println(count + ". Done!");

            long endTime   = System.nanoTime();
            long totalTime = endTime - startTime;
            double time = totalTime/ 1000000.0;

            System.out.println("\nRunning time :" + time +"ms");
        }
        else {
            System.out.println(" cannot find a path ");
        }
    }
}