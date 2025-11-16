package ru.batorfly;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int tableSize = IOUtils.scanIntTableSize();

        var table = new MultiplicationTable(tableSize);
        table.printTable();
    }
}