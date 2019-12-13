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
public class Write extends Expression {
    
    Expression exp;
    
    public Write(Expression exp) {
        this.exp = exp;
    }
    
    public int eval(){
        System.out.println(exp);
        return exp.eval();
    }
    
    public String toString(){
        return "WRITE("+exp.toString()+")";
    }
}
