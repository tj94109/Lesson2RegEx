package com.terrance.Lesson2RegEx;

import java.util.ArrayList;
import java.util.regex.*;
import java.io.*;
import java.util.List;


/**
 * This program reads from the neighbor-dump.txt file, and looks for PAN IDs, MAC Addresses, and RF_RSSI_M and adds results to .
 * to an ArrayList.
 * @author TJ
 *
 */
public class Lesson2RegEx {

    private static ArrayList<String> textArray = new ArrayList<String>();
    private static ArrayList<String> panID = new ArrayList<String>();
    private static ArrayList<String> macAddress = new ArrayList<String>();
    private static ArrayList<String> macPlusRSSI = new ArrayList<String>();


    private static final String panPattern = "PANID\\s+=\\s+(?<pan>[0-9].*)";
    private static final String macPattern = "(?<mac>[0-9a-z]{16})\\s+BPD\\s+(false|true)\\s+[0-9]+\\s+FSK_75\\s+FSK_75\\s+(?<RSSI>[\\d-.]*)";

    public static void main(String[] args) throws IOException {
        String filename = null;

        if (args.length > 0) {
            filename = args[0];
            System.out.println(filename);
        };
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;

        while((line = br.readLine()) != null){
            textArray.add(line);
        }

        myMatcher(panPattern,"pan",panID);
        myMatcher(macPattern,"mac",macAddress);
        myMatcher(macPattern,"mac","RSSI",macPlusRSSI);

        System.out.println("- List of PAN IDs (Total of " + panID.size() +")");
        for (String s : panID){
            System.out.println("PANID = " + s);
        }
        System.out.println("- List of MAC Addresses (Total of " + macAddress.size() +")");
        for (String s : macAddress){
            System.out.println(s);
        }

        System.out.println("- List of RF_RSSI_M values for each MAC address");
        for (String s : macPlusRSSI){
            System.out.println(s);
        }
    }

    //
    public static void myMatcher(String pattern, String group, List<String> list) {
        Pattern myPattern = Pattern.compile(pattern);
        for (String s : textArray) {
            Matcher matcher = myPattern.matcher(s);
            if (matcher.find()) {
                list.add(matcher.group(group));
            }
        }
    }

    public static void myMatcher(String pattern, String group, String group2, List<String> list){
        Pattern myPattern = Pattern.compile(pattern);
        for (String s : textArray) {
            Matcher matcher = myPattern.matcher(s);
            if (matcher.find()) {
                list.add(matcher.group(group) + " " + matcher.group(group2));
            }
        }
    }

}


