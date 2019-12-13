/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg353.lab.pkg8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author jasonsnare
 */
public class HexGenerator {
    
    public static int currReg = 0;

    public static int[] reg = new int[16]; //R0-9,J0-3(10-13),DR(14),PC(15)
    public static int[] ram = new int[256];
    public static HashMap<String, Integer> map = new HashMap<>();
    public static ArrayList<String> hexOpList = new ArrayList<String>();
    
    public static char convertReg(String s) {
        switch (s.charAt(0)) {
            case 'R':
                return s.charAt(1);
            case 'J':
                switch (s) {
                    case "J0":
                        return 'A';
                    case "J1":
                        return 'B';
                    case "J2":
                        return 'C';
                    case "J3":
                        return 'D';
                    default:
                        break;
                }
            case 'D':
                return 'E';
            case 'P':
                return 'F';
            default:
                break;
        }
        return 'x';
    }

    public static String getHex(String s) {
        StringBuilder sb = new StringBuilder(s);
        if (sb.length() > 5) {
            for (int i = 6; i < sb.length(); i++) {
                if (sb.charAt(i) == ' ') {
                    sb = sb.deleteCharAt(i);
                    i--;
                }
            }
        }
        s = sb.toString();
        String[] parse = s.split("(\\s+)|(,)");
        String hexOp = "";
        String args = "";
        int data;
        switch (parse[0]) {
            case "HALT": //HALT
                hexOp = "000";
                break;
            case "ZERO":
                hexOp = "1" + convertReg(parse[1]) + "0";
                break;
            case "SET":
                hexOp = "2" + convertReg(parse[1]) + Integer.toHexString(Integer.parseInt(parse[2]));
                break;
            case "DATA":
                if (!(map.containsKey(parse[1]))){
                    map.put(parse[1], nextLabel(parse[1]));
                    hexOp = "30"+ Integer.toHexString(map.get(parse[1]));
                    break;
                }
                data = map.get(parse[1]);
                args = Integer.toHexString(data);
                if (args.length() < 2) {
                    args = "0" + args;
                }
                hexOp = "3" + args;
                break;
            case "JUMP":
                data = map.get(parse[1]);
                args = Integer.toHexString(data);
                if (args.length() < 2) {
                    args = "0" + args;
                }
                hexOp = "F" + args;
                break;
            case "INC":
                data = Integer.parseInt(parse[2]);
                args = "";
                if (data >= 0) {
                    data = Integer.parseInt(parse[2]) - 1;
                    args = Integer.toString(data);
                } else {
                    switch (parse[2]) {
                        case "-1":
                            args = "8";
                            break;
                        case "-2":
                            args = "9";
                            break;
                        case "-3":
                            args = "A";
                            break;
                        case "-4":
                            args = "B";
                            break;
                        case "-5":
                            args = "C";
                            break;
                        case "-6":
                            args = "D";
                            break;
                        case "-7":
                            args = "E";
                            break;
                        case "-8":
                            args = "F";
                            break;
                        default:
                            break;
                    }
                }
                hexOp = "4" + convertReg(parse[1]) + args;
                break;
            case "SHIFT":
                data = Integer.parseInt(parse[2]);
                args = "";
                if (data >= 0) {
                    data = Integer.parseInt(parse[2]) - 1;
                    args = Integer.toString(data);
                } else {
                    switch (parse[2]) {
                        case "-1":
                            args = "8";
                            break;
                        case "-2":
                            args = "9";
                            break;
                        case "-3":
                            args = "A";
                            break;
                        case "-4":
                            args = "B";
                            break;
                        case "-5":
                            args = "C";
                            break;
                        case "-6":
                            args = "D";
                            break;
                        case "-7":
                            args = "E";
                            break;
                        case "-8":
                            args = "F";
                            break;
                        default:
                            break;
                    }
                }
                hexOp = "5" + convertReg(parse[1]) + args;
                break;
            case "ADD":
                hexOp = "6" + convertReg(parse[1]) + convertReg(parse[2]);
                break;
            case "SUB":
                hexOp = "7" + convertReg(parse[1]) + convertReg(parse[2]);
                break;
            case "AND":
                hexOp = "8" + convertReg(parse[1]) + convertReg(parse[2]);
                break;
            case "COPY":
                hexOp = "9" + convertReg(parse[1]) + convertReg(parse[2]);
                break;
            case "LOAD":
                hexOp = "A" + convertReg(parse[1]) + convertReg(parse[2]);
                break;
            case "STORE":
                hexOp = "B" + convertReg(parse[1]) + convertReg(parse[2]);
                break;
            case "READ":
                switch (parse[2]) {
                    case "DD":
                        args = "0";
                        break;
                    case "HD":
                        args = "1";
                        break;
                    case "AD":
                        args = "2";
                        break;
                    default:
                        break;
                }
                hexOp = "C" + convertReg(parse[1]) + args;
                break;
            case "WRITE":
                switch (parse[2]) {
                    case "DD":
                        args = "0";
                        break;
                    case "HD":
                        args = "1";
                        break;
                    case "AD":
                        args = "2";
                        break;
                    default:
                        break;
                }
                hexOp = "D" + convertReg(parse[1]) + args;
                break;
            case "JPIF":
                switch (parse[2]) {
                    case "LZ":
                        switch (parse[3]) {
                            case "J0":
                                args = "0";
                                break;
                            case "J1":
                                args = "1";
                                break;
                            case "J2":
                                args = "2";
                                break;
                            case "J3":
                                args = "3";
                                break;
                            default:
                                break;
                        }
                        break;
                    case "GZ":
                        switch (parse[3]) {
                            case "J0":
                                args = "4";
                                break;
                            case "J1":
                                args = "5";
                                break;
                            case "J2":
                                args = "6";
                                break;
                            case "J3":
                                args = "7";
                                break;
                            default:
                                break;
                        }
                        break;
                    case "EZ":
                        switch (parse[3]) {
                            case "J0":
                                args = "8";
                                break;
                            case "J1":
                                args = "9";
                                break;
                            case "J2":
                                args = "A";
                                break;
                            case "J3":
                                args = "B";
                                break;
                            default:
                                break;
                        }
                        break;
                    case "NZ":
                        switch (parse[3]) {
                            case "J0":
                                args = "C";
                                break;
                            case "J1":
                                args = "D";
                                break;
                            case "J2":
                                args = "E";
                                break;
                            case "J3":
                                args = "F";
                                break;
                            default:
                                break;
                        }
                    default:
                        break;
                }
                hexOp = "E" + convertReg(parse[1]) + args;
        }
        hexOpList.add(hexOp.toUpperCase());
        return hexOp;
    }

    public static void storeHex() {
        int i = 0;
        while (Stack.assemCodes.size() > 0) {
            ram[i] = Integer.parseInt(getHex(Stack.assemCodes.get(0)), 16);
            Stack.assemCodes.remove(0);
            i++;
        }
    }
    
    public static int nextLabel(String s){
        int returnNum = Stack.assemCodes.lastIndexOf(s);
        Stack.assemCodes.remove(returnNum);
        return returnNum+2;
    }

    public static void simHex() {
        Scanner scnr = new Scanner(System.in);
        Boolean b = true;
        while (b) {
            int reg1;
            int reg2;
            String s;
            String s1;
            String s2;
            String opCode = Integer.toHexString(ram[reg[15]]);
            reg[15]++;
            if (opCode.length() != 3) {
                opCode = "0" + opCode;
            }
            opCode = opCode.toUpperCase().trim();
            switch (opCode.charAt(0)) {
                case '0': //Halts the machine
                    b = false;
                    break;
                case '1':// Zeroes (or "clears") out register RRRR
                    s = opCode.charAt(1) + "";
                    reg1 = Integer.parseInt(s, 16);
                    reg[reg1] = 0;
                    break;
                case '2': //Sets the 4 lowest-order bits of register RRRR to BBBB
                    s = opCode.charAt(1) + "";
                    reg1 = Integer.parseInt(s, 16);
                    s1 = opCode.charAt(2) + "";
                    int r1 = reg[reg1];
                    r1 = r1 >> 4;
                    r1 = r1 << 4;
                    reg2 = Integer.parseInt(s1, 16);
                    reg[reg1] = reg2;
                    break;
                case '3': //Clears the data register DR, then sets its 8 lowest-order bits
                    reg[14] = 0;
                    s = opCode.charAt(1) + "";
                    reg1 = Integer.parseInt(s, 16);
                    s = Integer.toBinaryString(reg1);
                    while (s.length() != 4) {
                        s = "0" + s;
                    }
                    s1 = opCode.charAt(2) + "";
                    reg2 = Integer.parseInt(s1, 16);
                    s1 = Integer.toBinaryString(reg2);
                    while (s1.length() != 4) {
                        s1 = "0" + s1;
                    }
                    s2 = s + s1;
                    reg[14] = Integer.parseInt(s2, 2);
                    break;
                case '4': //Adds or subtracts from 1 to 8 from register RRRR
                    s = opCode.charAt(1) + "";
                    reg1 = Integer.parseInt(s, 16);
                    s1 = opCode.charAt(2) + "";
                    reg2 = Integer.parseInt(s1, 16);
                    if (reg2 <= 7) {
                        reg[reg1] += reg2 + 1;
                    } else {
                        reg[reg1] -= (reg2 - 7);
                    }
                    break;
                case '5': //Shifts register RRRR left (-) or right (+) by from 1 to 8 bits
                    s = opCode.charAt(1) + "";
                    reg1 = Integer.parseInt(s, 16);
                    s1 = opCode.charAt(2) + "";
                    reg2 = Integer.parseInt(s1, 16) + 1;
                    if (reg2 > 8) {
                        reg[reg1] = (reg[reg1] * (int) Math.pow(2, (reg2 - 8)));
                    } else if (reg2 <= 8) {
                        int shifter = reg[reg1];
                        s1 = Integer.toBinaryString(shifter);
                        while (reg2 > 0) {
                            s1 = s1.substring(0, s1.length() - 1);
                            reg2--;
                        }
                        shifter = Integer.parseInt(s1, 2);
                        reg[reg1] = shifter;
                    }
                    break;
                case '6': //Adds the contents of register RRR1 to RRR2 (result in RRR2)
                    s = opCode.charAt(1) + "";
                    reg1 = Integer.parseInt(s, 16);
                    s1 = opCode.charAt(2) + "";
                    reg2 = Integer.parseInt(s1, 16);
                    reg[reg2] = reg[reg1] + reg[reg2];
                    break;
                case '7': //Subtracts the contents of register RRR1 from RRR2 (result in RRR2)
                    s = opCode.charAt(1) + "";
                    reg1 = Integer.parseInt(s, 16);
                    s1 = opCode.charAt(2) + "";
                    reg2 = Integer.parseInt(s1, 16);
                    reg[reg2] = reg[reg2] - reg[reg1];
                    break;
                case '8': //Logically ANDs the contents of register RRR1 into RRR2 (result in RRR2)
                    s = opCode.charAt(1) + "";
                    reg1 = Integer.parseInt(s, 16);
                    s1 = opCode.charAt(2) + "";
                    reg2 = Integer.parseInt(s1, 16);
                    if (reg[reg1] == reg[reg2]) {
                        reg[reg2] = reg[reg1];
                    } else {
                        reg[reg2] = 0;
                    }
                    break;
                case '9': //Copies the contents of register RRR1 to RRR2
                    s = opCode.charAt(1) + "";
                    reg1 = Integer.parseInt(s, 16);
                    s1 = opCode.charAt(2) + "";
                    reg2 = Integer.parseInt(s1, 16);
                    reg[reg2] = reg[reg1];
                    break;
                case 'A': //Loads the contents of location addressed by RRR2 into register RRR1
                    s = opCode.charAt(1) + "";
                    reg1 = Integer.parseInt(s, 16);
                    s1 = opCode.charAt(2) + "";
                    reg2 = Integer.parseInt(s1, 16);
                    reg[reg1] = ram[reg[reg2]];
                    break;
                case 'B': //Stores the contents of register RRR1 into location addressed by RRR2
                    s = opCode.charAt(1) + "";
                    reg1 = Integer.parseInt(s, 16);
                    s1 = opCode.charAt(2) + "";
                    reg2 = Integer.parseInt(s1, 16);
                    ram[reg[reg2]] = reg[reg1];
                    break;
                case 'C': //Reads a value (up to 12 bits) into register RRRR from device DDDD
                    int decInput;
                    int hexInput;
                    String ascInput;
                    char charInput;
                    s = opCode.charAt(1) + "";
                    reg1 = Integer.parseInt(s, 16);
                    if (opCode.charAt(2) == '0') {
                        System.out.println("Enter decimal number: ");
                        decInput = scnr.nextInt();//num must between -2047 and 2047
                        reg[reg1] = decInput;
                    }
                    if (opCode.charAt(2) == '1') {
                        System.out.println("Enter hexadecimal number: ");
                        hexInput = scnr.nextInt();
                        hexInput = Integer.parseInt(Integer.toHexString(hexInput), 16);
                        reg[reg1] = hexInput;
                    }
                    if (opCode.charAt(2) == '2') {
                        System.out.println("Enter ASCII character: ");
                        ascInput = scnr.next();
                        reg[reg1] = (int) ascInput.charAt(0);
                    }
                    break;
                case 'D': //Writes a value (up to 12 bits) from register RRRR to device DDDD
                    s = opCode.charAt(1) + "";
                    reg1 = Integer.parseInt(s, 16);
                    if (opCode.charAt(2) == '0') {
                        System.out.println("Decimal output written: " + reg[reg1]);
                    }
                    if (opCode.charAt(2) == '1') {
                        String outStr = Integer.toHexString(reg[reg1]);
                        while (outStr.length() != 3) {
                            outStr = "0" + outStr;
                        }
                        System.out.println("Hexadecimal output written: " + outStr.toUpperCase());
                    }
                    if (opCode.charAt(2) == '2') {
                        System.out.println("ASCII output written: " + Character.toString((char) reg[reg1]));
                    }
                    break;
                case 'E': //If contents of RRRR meet condition CC, jump to address in JJ
                    s = opCode.charAt(1) + "";
                    reg1 = Integer.parseInt(s, 16);
                    s1 = opCode.charAt(2) + "";
                    reg2 = Integer.parseInt(s1, 16);
                    s1 = Integer.toBinaryString(reg2);
                    while (s1.length() != 4) {
                        s1 = "0" + s1;
                    }
                    s2 = s1.substring(s1.length() - 2, s1.length());
                    s = s1.substring(0, 2);
                    switch (s) {
                        case "00"://LZ
                            if (reg[reg1] < 0) {
                                switch (s2) {
                                    case "00":
                                        reg[15] = reg[10];
                                        break;
                                    case "01":
                                        reg[15] = reg[11];
                                        break;
                                    case "10":
                                        reg[15] = reg[12];
                                        break;
                                    case "11":
                                        reg[15] = reg[13];
                                        break;
                                    default:
                                        System.out.println("Invalid OpCode.");
                                        break;
                                }
                            }
                            break;
                        case "01"://GZ
                            if (reg[reg1] > 0) {
                                switch (s2) {
                                    case "00":
                                        reg[15] = reg[10];
                                        break;
                                    case "01":
                                        reg[15] = reg[11];
                                        break;
                                    case "10":
                                        reg[15] = reg[12];
                                        break;
                                    case "11":
                                        reg[15] = reg[13];
                                        break;
                                    default:
                                        System.out.println("Invalid OpCode.");
                                        break;
                                }
                            }
                            break;
                        case "10"://EZ
                            if (reg[reg1] == 0) {
                                switch (s2) {
                                    case "00":
                                        reg[15] = reg[10];
                                        break;
                                    case "01":
                                        reg[15] = reg[11];
                                        break;
                                    case "10":
                                        reg[15] = reg[12];
                                        break;
                                    case "11":
                                        reg[15] = reg[13];
                                        break;
                                    default:
                                        break;
                                }
                            }
                            break;
                        case "11"://NZ
                            if (reg[reg1] != 0) {
                                switch (s2) {
                                    case "00":
                                        reg[15] = reg[10];
                                        break;
                                    case "01":
                                        reg[15] = reg[11];
                                        break;
                                    case "10":
                                        reg[15] = reg[12];
                                        break;
                                    case "11":
                                        reg[15] = reg[13];
                                        break;
                                    default:
                                        System.out.println("Invalid OpCode.");
                                        break;
                                }
                            }
                            break;
                        default:
                            System.out.println("Invalid OpCode.");
                            break;
                    }
                    break;
                case 'F': //Jump directly to the location whose address is AAAA AAAA
                    s = opCode.substring(1, opCode.length());
                    int parser = Integer.parseInt(s, 16);
                    reg[15] = parser;
                    break;
                default:
                    System.out.println("Invalid Op-Code.");
                    break;
            }
        }
    }
}
