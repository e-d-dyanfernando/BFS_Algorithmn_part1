// IIT ID - 20200473

// UoW ID - w1839054

// Student Name - E. D. Dyan Fernando

package Algorithms_CW;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Maze mazeClass = new Maze("maze10_1.txt");
            mazeClass.printPattern();
            mazeClass.findThePath();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
}