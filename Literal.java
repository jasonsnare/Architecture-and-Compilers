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
public class Literal extends Expression {
    
    public int val;
    
    
    public Literal(int val){
        this.val = val;
    }
    
    public int eval(){
        //Stack.assemCodes.add("SET R"+HexGenerator.currReg+","+val);
        //HexGenerator.currReg++;
        return val;
    }
    public String toString(){
        return val+"";
    }
}
