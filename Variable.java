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
public class Variable extends Expression {
    public String name;
    public int value;
    
    public Variable(String name){
        this.name = name;
    }
    public int eval(){
        Stack.assemCodes.add("SET R"+HexGenerator.currReg+","+Integer.toHexString(value));
       // HexGenerator.currReg++;
        return map.get(name);
    }
    public String toString(){
        return name;
    }
}
