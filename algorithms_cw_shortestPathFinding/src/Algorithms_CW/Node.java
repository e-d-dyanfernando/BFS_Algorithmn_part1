package Algorithms_CW;

public class Node {
    public int x;
    public int y;

    public Node previousPoint;
    public String instruction;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Node(int x, int y, Node previousPoint, String instruction) {
        this.x = x;
        this.y = y;
        this.previousPoint = previousPoint;
        this.instruction = instruction;
    }
}