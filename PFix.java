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
public class PFix extends Expression{
    public int op;
    Variable var;
    public static final int PRE_INC = 0, POST_INC = 1, POST_DEC = 2, PRE_DEC = 3;
    
    public PFix(int op, Variable var){
        this.op = op;
        this.var = var;
    }
    
    public int eval(){
        int k = 0;
        switch(op){
            case PRE_INC:
                k = map.get(var.name);
                k++;
                map.put(var.name, k);
                break;
            case POST_INC:
                k++;
                k += map.get(var.name);
                map.put(var.name, k);
                break;
            case PRE_DEC:
                k = map.get(var.name);
                k--;
                map.put(var.name, k);
                break;
            case POST_DEC:
                k--;
                k += map.get(var.name);
                map.put(var.name, k);
                break;
            default:
                break;
        }
        return k;
    }
    
    public String toString(){
        switch(op){
            case PRE_INC:
                return "++" + var.name;
            case POST_INC:
                return var.name + "++";
            case PRE_DEC:
                return "--" + var.name;
            case POST_DEC:
                return var.name + "--";
            default:
                return "X";
        }
    }
}
