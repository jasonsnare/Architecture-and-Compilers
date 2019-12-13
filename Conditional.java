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
public class Conditional extends Expression{
    Expression exp1;
    Expression exp2;
    Expression exp3;
    
    
    public Conditional(Expression exp1, Expression exp2, Expression exp3){
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }
    
    public int eval(){
        if (exp1.eval() != 0){
            return exp2.eval();          
        }
        else {
            return exp3.eval();
        }
    }
    
    public String toString(){
        return '('+exp1.toString() + '?' + exp2.toString() + ':' + exp3.toString() + ')';
    }
}
