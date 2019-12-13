package pkg353.lab.pkg8;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Stack extends Expression {

    public static ArrayList<Expression> tree = new ArrayList<Expression>();
    public static ArrayList<String> op = new ArrayList<String>();
    public static ArrayList<String> assemCodes = new ArrayList<String>();
    public static int ramCnt = 255;
    public static boolean regCheck = false;
    
    
    
    public static Pattern varPat = Pattern.compile("^[a-z]+");
    public static Pattern litPat = Pattern.compile("^[0-9]+");
    public static Pattern readPat = Pattern.compile("^READ");
    public static Pattern writePat = Pattern.compile("^WRITE\\(.+?\\)");
    public static Pattern pfixPat = Pattern.compile("^((([a-z]+)(\\+\\+|--))|((\\+\\+|--)([a-z]+)))");
    public static Pattern opPat = Pattern.compile("^\\s*((\\+)|(\\-)|(\\*)|(\\>)|(\\<)|(\\=\\=)|(\\&)|(\\|))\\s*");
    public static Pattern assignPat = Pattern.compile("^[a-z]+\\=");
    public static Pattern ternPat1 = Pattern.compile("^\\?");
    public static Pattern ternPat2 = Pattern.compile("^\\:");
    

    public static Expression treePop() {
        return tree.remove(tree.size() - 1);
    }

    public static void treePush(Expression e) {
        tree.add(e);
    }

    public static String opPop() {
        return op.remove(op.size() - 1);
    }

    public static void opPush(String c) {
        op.add(c);
    }

    public static boolean hasPrec(String a, String b) {
        if (a.equals(b)) {
            return false;
        } else if (a.equals("(")) {
            return true;
        } else if (a.equals("*") && !(b.equals("("))) {
            return true;
        } else if ((a.equals("+") || a.equals("-")) && (!(b.equals("(") || b.equals("*")))) {
            return true;
        } else if ((a.equals(">") || a.equals("<") || a.equals("==") || a.equals("&") || a.equals("|"))
                && (!(b.equals("+") || b.equals("-") || b.equals("*") || b.equals("(")))) {
            return true;
        }
        return false;
    }

    public static int opConverter(String c) { //ADD = 0, SUB = 1, MULT = 2, GT = 3, LT = 4, EQ = 5, AND = 6, OR = 7;
        switch (c) {
            case "*":
                return 2;
            case "+":
                return 0;
            case "-":
                return 1;
            case ">":
                return 3;
            case "<":
                return 4;
            case "==":
                return 5;
            case "&":
                return 6;
            case "|":
                return 7;
            default:
                return -99;
        }
    }

    public static Expression tokenizer(String s) {
        Matcher varMat = varPat.matcher(s);
        Matcher litMat = litPat.matcher(s);
        Matcher readMat = readPat.matcher(s);
        Matcher writeMat = writePat.matcher(s);
        Matcher pfixMat = pfixPat.matcher(s);
        Matcher opMat = opPat.matcher(s);
        Matcher assignMat = assignPat.matcher(s);
        Matcher ternMat1 = ternPat1.matcher(s);
        Matcher ternMat2 = ternPat2.matcher(s);       
        if (s.equals("")) {
            return parse();
        } else if (varMat.find()) {
            String var = varMat.group();
            Expression var1 = new Variable(var);
            treePush(var1);
            s = varMat.replaceFirst("");
            s = s.substring(1,s.length());
            Expression var2 = getNextToken(s);
            map.put(var, var2.eval());
            s = s.replace(var2.toString(), "");
            s = s.replace("READ", "");
        } else if (litMat.find()) {            
            treePush(new Literal(Integer.parseInt(litMat.group())));
            s = litMat.replaceFirst("");
        } else if (readMat.find()) {
            treePush(new Read());
            s = readMat.replaceFirst("");
        } else if (writeMat.find()) {
            Pattern writePat1 = Pattern.compile("\\(.+?\\)");
            Matcher writeMat1 = writePat1.matcher(writeMat.group());
            String s1 = writeMat1.group();
            s1 = s1.substring(1, s1.length() - 1);
            treePush(new Write(tokenizer(s1)));
            s = writeMat.replaceFirst("");
        } else if (pfixMat.find()) {
            String match = pfixMat.group();
            if (match.substring(0, 2).equals("++")) {
                treePush(new PFix(PFix.PRE_INC, new Variable(pfixMat.group())));
            } else if (match.substring(0, 2).equals("--")) {
                treePush(new PFix(PFix.PRE_DEC, new Variable(pfixMat.group())));
            } else if (match.substring(match.length() - 2, match.length()).equals("++")) {
                treePush(new PFix(PFix.POST_INC, new Variable(pfixMat.group())));
            } else if (match.substring(match.length() - 2, match.length()).equals("--")) {
                treePush(new PFix(PFix.POST_DEC, new Variable(pfixMat.group())));
            } else {
                System.out.println("PFIX error");
            }
            s = s.replaceFirst(match, "");
        } else if (opMat.find()) {
            if (op.size() != 0 && tree.size() > 1) {
                String op1 = opPop();
                if (hasPrec(op1, opMat.group())) {
                    Expression exp1 = treePop();
                    Expression exp2 = treePop();
                    opPush(opMat.group());
                    treePush(new EOE(exp2, opConverter(op1), exp1));
                } else {
                    opPush(op1);
                    opPush(opMat.group());
                }
            } else {
                opPush(opMat.group());
            }
            s = opMat.replaceFirst("");
        } else if (assignMat.find()) {
            opPush(assignMat.group().substring(1, assignMat.group().length()));
            s = assignMat.replaceFirst("");

        } else if (ternMat1.find()) {
            opPush(ternMat1.group().substring(1, ternMat1.group().length()));
            s = ternMat1.replaceFirst("");
        } else if (ternMat2.find()) {
            opPush(ternMat2.group().substring(1, ternMat2.group().length()));
            s = ternMat2.replaceFirst("");
        } else if (s.charAt(0) == '(') {
            opPush(s.charAt(0) + "");
            s = s.replace('(' + "", "");
        } else if (s.charAt(0) == ')') {
            String peak = opPop();
            while (!(peak.equals("("))){
                Expression parenExp = treePop();
                Expression parenExp1 = treePop();
                treePush(new EOE(parenExp1,opConverter(peak),parenExp));
                peak = opPop();
            }
            opPush(peak);
            s = s.replace(')' + "", "");
        }
        else {        
            System.out.println("No token found");
            return parse();
        }
        return tokenizer(s);
    }

    public static Expression parse() {        
        while (tree.size() > 1) {
            Expression exp1 = treePop();
            Expression exp2 = treePop();
            Expression exp3 = new EOE(exp2, opConverter(opPop()), exp1);
            treePush(exp3);
        }
        return treePop();
    }
     
    public static Expression getNextToken(String s){
        Matcher varMat = varPat.matcher(s);
        Matcher litMat = litPat.matcher(s);
        Matcher readMat = readPat.matcher(s);
        Matcher writeMat = writePat.matcher(s);
        Matcher pfixMat = pfixPat.matcher(s);
        Matcher opMat = opPat.matcher(s);
        Matcher assignMat = assignPat.matcher(s);
        Matcher ternMat1 = ternPat1.matcher(s);
        Matcher ternMat2 = ternPat2.matcher(s);
        if (varMat.find()) {
            return new Variable(varMat.group());
        } else if (litMat.find()) {
            return new Literal(Integer.parseInt(litMat.group()));
        } else if (readMat.find()) {
            return new Read();
        }
        return new Literal(-99);
    }
    
    public String toString(){
        return tree.toString();
    }
    
    public int eval(){
        return tree.get(0).eval();
    }
}
