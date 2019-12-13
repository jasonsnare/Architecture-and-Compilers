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
public class VE extends Expression {
    Variable var;
    Expression exp;
    
    public VE(Variable var, Expression exp){
        this.exp = exp;
        this.var = var;
    }
    
    public int eval(){
        map.put(var.name, exp.eval());
        return exp.eval();
    }
    
    public String toString(){
        return '('+ var.toString() + "=" +exp.toString() + ')';
    }
}
