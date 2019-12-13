package pkg353.lab.pkg8;


public class Compiler {

    public static void main(String[] args) {
        System.out.println("*********INITIAL EXPRESSION**********");
        String s = "x=READ+5";
        Expression exp = Stack.tokenizer(s);
        System.out.println(exp);
        exp.eval();
        Stack.assemCodes.add("WRITE R9,DD");
        System.out.println("*******ASSEMBLY CODE GENERATED*******");
        Stack.assemCodes.add("HALT");
        System.out.println(Stack.assemCodes);
        HexGenerator.storeHex();
        System.out.println("");
        System.out.println("*********ASSEMBLER RESULTS***********");
        System.out.println(HexGenerator.hexOpList);
        System.out.println("");
        System.out.println("*********SIMULATOR RESULTS***********");
        HexGenerator.simHex();
    }
    
}
