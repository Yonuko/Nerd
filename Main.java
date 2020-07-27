import java_cup.runtime.*;
import java.io.FileReader;

class Main {
    public static void main(String[] argv) throws Exception{
        if(argv.length == 0){
            System.out.println("Utilisation : java Main <inputfile>");
            return;
        }
        System.out.println("Parser Result:");
        Parser p = new Parser(new Lexer(new FileReader(argv[0]), new ComplexSymbolFactory()));
        try{
            p.parse();
        }catch(Exception e){
            System.out.println("Error: la fonction p.parse() à envoyer une Exception\n");
            e.printStackTrace();
        }
    }
}