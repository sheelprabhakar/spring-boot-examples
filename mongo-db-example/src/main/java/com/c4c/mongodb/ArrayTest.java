package com.c4c.mongodb;

import java.util.*;

public class ArrayTest {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[][] arr = new String[n][n];
        scanner.nextLine();
        for (int row = 0; row < n; row++) {
            String line = scanner.nextLine();

            String[] elements = line.split("\\s+");
            for (int column = 0; column < n; column++) {
                arr[row][column] = elements[column];
            }
        }
        System.out.println(Arrays.deepToString(arr));

        if (arr[2][2] == "i"){
            System.out.println("There is an i");
        }
        else {
            System.out.print("No i found");
        }
    }
}