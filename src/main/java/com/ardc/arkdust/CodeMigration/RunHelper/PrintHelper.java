package com.ardc.arkdust.CodeMigration.RunHelper;

import java.util.List;

public class PrintHelper {
    public static <T> void printList(List<T> list){
        int c = 0;
        StringBuilder output = new StringBuilder();
        for (T obj:list){
            c++;
            output.append("\n").append(c).append(":").append(obj);
        }
        System.out.println(output);
    }
}
