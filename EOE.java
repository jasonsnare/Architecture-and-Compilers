/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg353.lab.pkg8;

/**
 *
 * @author jasonsnare
 */
public class EOE extends Expression {

    public int op;
    Expression exp1;
    Expression exp2;
    public int i = 0;
    public static int ADD = 0, SUB = 1, MULT = 2, GT = 3, LT = 4, EQ = 5, AND = 6, OR = 7;

    public EOE(Expression exp1, int op, Expression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    public int eval() {
        int b = exp2.eval();
        int a = exp1.eval();
        switch (op) {
            case 0:
                i = a + b;
                if (Stack.regCheck == false) {
                    Stack.assemCodes.add("SET R" + HexGenerator.currReg + "," + a);
                    HexGenerator.currReg++;
                    Stack.assemCodes.add("SET R" + HexGenerator.currReg + "," + b);
                    HexGenerator.currReg++;
                    Stack.assemCodes.add("ADD R" + ((HexGenerator.currReg) - 2) + ",R" + ((HexGenerator.currReg) - 1));
                    Stack.assemCodes.add("COPY R" + ((HexGenerator.currReg) - 1) + ",R9");
                    Stack.regCheck = true;
                } else {
                    Stack.assemCodes.add("SET R"+(HexGenerator.currReg-1)+","+a);
                    Stack.assemCodes.add("ADD R" + ((HexGenerator.currReg) - 1) + ",R9");
                }
                break;
            case 1:
                i = a - b;
                if (Stack.regCheck == false) {
                    Stack.assemCodes.add("SET R"+HexGenerator.currReg+","+b);
                    HexGenerator.currReg++;
                    Stack.assemCodes.add("SET R"+HexGenerator.currReg+","+a);
                    HexGenerator.currReg++;
                    Stack.assemCodes.add("SUB R" + ((HexGenerator.currReg) - 2) + ",R" + ((HexGenerator.currReg) - 1));
                    Stack.assemCodes.add("COPY R" + ((HexGenerator.currReg) - 1) + ",R9");
                    Stack.regCheck = true;
                } else {
                    Stack.assemCodes.add("SET R"+(HexGenerator.currReg-1)+","+b);
                    Stack.assemCodes.add("SUB R" + ((HexGenerator.currReg) - 1) + ",R9");
                }
                break;
            case 2:
                i = a * b;
                Stack.assemCodes.add("SET R" + HexGenerator.currReg + "," + a);
                HexGenerator.currReg++;
                Stack.assemCodes.add("SET R" + HexGenerator.currReg + "," + b);
                HexGenerator.currReg++;
                Stack.assemCodes.add("DATA #mult");
                Stack.assemCodes.add("COPY DR,J0");
                Stack.assemCodes.add("COPY R" + (HexGenerator.currReg - 2) + ",R7");
                Stack.assemCodes.add("COPY R" + (HexGenerator.currReg - 1) + ",R8");
                Stack.assemCodes.add("ZERO R9");
                Stack.assemCodes.add("#mult");
                Stack.assemCodes.add("ADD R7,R9");
                Stack.assemCodes.add("INC R8, -1");
                Stack.assemCodes.add("JPIF R8,GZ,J0");
                break;
            case 3:
                if (a > b) {
                    i = 1;
                }
                break;
            case 4:
                if (a < b) {
                    i = 1;
                }
                break;
            case 5:
                if (a == b) {
                    i = 1;
                }
            case 6:
                if (a == b) {
                    i = 1;
                }
                break;
            case 7:
                if (!(a == 0 && b == 0)) {
                    i = 1;
                }
        }
        HexGenerator.currReg--;
        return i;
    }

    public String toString() {
        String sign;
        switch (op) {
            case 0:
                sign = "+";
                break;
            case 1:
                sign = "-";
                break;
            case 2:
                sign = "*";
                break;
            case 3:
                sign = ">";
                break;
            case 4:
                sign = "<";
                break;
            case 5:
                sign = "=";
                break;
            case 6:
                sign = "&";
                break;
            case 7:
                sign = "|";
                break;
            default:
                sign = "?";
                break;
        }
        return '(' + exp1.toString() + sign + exp2.toString() + ')';
    }
}
