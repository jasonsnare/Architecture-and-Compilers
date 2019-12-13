/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg353.lab.pkg8;
import java.util.*;
/**
 *
 * @author jasonsnare
 */
public class Read extends Expression {
   
    Scanner scnr = new Scanner(System.in);
    
    public int eval(){
         int i = scnr.nextInt();
         Stack.assemCodes.add("READ R"+HexGenerator.currReg+",DD");
         HexGenerator.currReg++;
         return i;
     }
    
    public String toString(){
        return "(READ)";
    }
}
