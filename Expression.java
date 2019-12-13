/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg353.lab.pkg8;
import java.util.HashMap;

/**
 *
 * @author jasonsnare
 */
public abstract class Expression {
    public static HashMap<String, Integer> map = new HashMap<>();
    public abstract int eval();
    public abstract String toString();
}
