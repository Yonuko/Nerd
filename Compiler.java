import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Compiler{

	private String outputFileName = "output.asm";
	private FileWriter outputFile = null;
	private BufferedWriter exprOut;
	private int ifCount = 0, whileCount = 0, stringCount = 0;
	
	// Variables and register table
	private HashMap<String, String> variableTypeTable = new HashMap<String, String>();
	private ArrayList<String> usedRegister = new ArrayList<String>();
	
	// Contient les instructions MIPS des zones .data et .text
	private String dataAreaData = ".data\n";
	private String textAreaData = ".text\n";
	
	public static void main(String[] args) {
		Compiler comp = new Compiler();
		try {
			comp.compile("parsedTree.json");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public Compiler(){

    }
    
    public void compile(String file) throws Exception{
    	BufferedReader br;
    	String fileAsString = null;
		try {
			br = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) { 
				sb.append(line).append("\n");
				line = br.readLine();
			}
			fileAsString = sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ArrayList<HashMap<String, String>> test = parseArray(fileAsString);
    	//ArrayList<HashMap<String, String>> test = parseArray("[{\"instruction\" : \"Decl\", \"type\" : \"int\", \"name\" : \"test\"}, {\"instruction\" : \"affect\", \"name\" : \"test\", \"op\" : \"=\", \"value\" : {\"instruction\" : \"MINUS\", \"left\" : {\"instruction\" : \"INT\", \"value\" : 20}, \"right\" : {\"instruction\" : \"MULT\", \"left\" : {\"instruction\" : \"INT\", \"value\" : 2}, \"right\" : {\"instruction\" : \"INT\", \"value\" : 4}}}}, {\"instruction\" : \"if\", \"statement\" : [{\"instruction\" : \"statement\", \"left\" : [{\"instruction\" : \"statement\", \"left\" : [{\"instruction\" : \"statement\", \"left\" : {\"instruction\" : \"VAR\", \"name\" : \"test\"}, \"op\" : \"==\", \"right\" : {\"instruction\" : \"INT\", \"value\" : 9}}], \"op\" : \"&&\", \"right\" : [{\"instruction\" : \"statement\", \"left\" : {\"instruction\" : \"VAR\", \"name\" : \"test\"}, \"op\" : \"<\", \"right\" : {\"instruction\" : \"INT\", \"value\" : 10}}]}], \"op\" : \"||\", \"right\" : [{\"instruction\" : \"statement\", \"left\" : {\"instruction\" : \"VAR\", \"name\" : \"test\"}, \"op\" : \"!=\", \"right\" : {\"instruction\" : \"INT\", \"value\" : 12}}]}], \"expression\" : [{\"instruction\" : \"print\", \"value\" : {\"instruction\" : \"Concat\", \"left\" : {\"instruction\" : \"STRING\", \"value\" : \"Valeur de test : \"}, \"right\" : {\"instruction\" : \"VAR\", \"name\" : \"test\"}}}, {\"instruction\" : \"print\", \"value\" : {\"instruction\" : \"STRING\", \"value\" : \"Petit test\"}}], \"else\" : {\"instruction\" : \"else\", \"expression\" : [{\"instruction\" : \"print\", \"value\" : {\"instruction\" : \"STRING\", \"value\" : \"On est dans le else\"}}]}}, {\"instruction\" : \"Decl\", \"type\" : \"int\", \"name\" : \"i\"}, {\"instruction\" : \"affect\", \"name\" : \"i\", \"op\" : \"=\", \"value\" : {\"instruction\" : \"read\", \"value\" : {\"instruction\" : \"STRING\", \"value\" : \"Entrer votre âge : \"}}}, {\"instruction\" : \"Decl\", \"type\" : \"int\", \"name\" : \"j\"}, {\"instruction\" : \"affect\", \"name\" : \"j\", \"op\" : \"=\", \"value\" : {\"instruction\" : \"INT\", \"value\" : 0}}, {\"instruction\" : \"while\", \"statement\" : [{\"instruction\" : \"statement\", \"left\" : {\"instruction\" : \"VAR\", \"name\" : \"j\"}, \"op\" : \"<\", \"right\" : {\"instruction\" : \"INT\", \"value\" : 10}}], \"expression\" : [{\"instruction\" : \"affect\", \"name\" : \"j\", \"op\" : \"++\"}, {\"instruction\" : \"affect\", \"name\" : \"i\", \"op\" : \"++\"}]}, {\"instruction\" : \"print\", \"value\" : {\"instruction\" : \"Concat\", \"left\" : {\"instruction\" : \"Concat\", \"left\" : {\"instruction\" : \"STRING\", \"value\" : \"Dans 10 ans, tu auras \"}, \"right\" : {\"instruction\" : \"VAR\", \"name\" : \"i\"}}, \"right\" : {\"instruction\" : \"STRING\", \"value\" : \" ans\"}}}]");
    	for(HashMap<String, String> data : test) {
			unparseAll(data);
		}
    	System.out.println(dataAreaData);
    	System.out.println(textAreaData);
    	Write(dataAreaData);
    	Write(textAreaData);
    }
    
    private void unparseAll(HashMap<String, String> jsonObject) throws Exception {
		for(String key : jsonObject.keySet()) {
			if(jsonObject.get(key).charAt(0) == '['){
				ArrayList<HashMap<String, String>> jsonArray = parseArray(jsonObject.get(key));
				for(HashMap<String, String> newObject : jsonArray) {					
					unparseAll(newObject);
				}
			}else if(jsonObject.get(key).charAt(0) == '{') {
				HashMap<String, String> newObject = parse(jsonObject.get(key));				
				unparseAll(newObject);
			}
		}
		compileNode(jsonObject);
    }
    
    private void compileNode(HashMap<String, String> jsonObject) throws Exception {
    	String name = null;
    	String left = null;
    	String right = null;
    	textAreaData += "\n";
		switch(jsonObject.get("instruction")) {
			// Declaration
			case "Decl":
				name = (jsonObject.get("name") == null) ? 
						parse(jsonObject.get("value")).get("name") : 
						jsonObject.get("name");
				if(variableTypeTable.containsKey(name)) {
					throw new Exception("Already defined variable \"" + name + "\"");
				}
				dataAreaData += name + ": ";
				switch(jsonObject.get("type")) {
					case "int":
						dataAreaData += ".word ";
						dataAreaData += "0\n";
						break;
					case "string":
						dataAreaData += ".asciiz ";
						dataAreaData += "\"\"\n";
						break;
					case "bool":
						dataAreaData += ".byte ";
						if(jsonObject.get("value") != null) {
							if(jsonObject.get("value").equals("true")) {
								dataAreaData += "1\n";		
							}else{
								dataAreaData += "0\n";
							}
						}else {
							dataAreaData += "0\n";
						}
						break;
					case "float":
						dataAreaData += ".double ";
						dataAreaData += "0.0\n";
						break;
					default:
						throw new Exception("Undefined type");
				}
				variableTypeTable.put(name, jsonObject.get("type"));
				break;
			// Affectation
			case "affect":
				name = jsonObject.get("name");
				if(!variableTypeTable.containsKey(name)) {
					throw new Exception("Undefined variable \"" + name + "\"");
				}
				if(jsonObject.get("op").equals("=")) {
					switch(variableTypeTable.get(name)) {
					case "int":
						textAreaData += "sw $" + usedRegister.get(usedRegister.size() - 1) + ", " + name + "\n";
						usedRegister.remove(usedRegister.size() - 1);
						break;
					case "string":
						textAreaData += "sw $" + usedRegister.get(usedRegister.size() - 1) + ", " + name + "\n";
						usedRegister.remove(usedRegister.size() - 1);
						break;
					case "bool":
						dataAreaData += "sb $" + usedRegister.get(usedRegister.size() - 1) + ", " + name + "\n";
						usedRegister.remove(usedRegister.size() - 1);
						break;
					case "float":
						textAreaData += "sw $" + usedRegister.get(usedRegister.size() - 1) + ", " + name + "\n";
						usedRegister.remove(usedRegister.size() - 1);
						break;
					default:
						throw new Exception("Undefined type");
					}
				}else {
					if(!variableTypeTable.get(name).equals("int")) {
						throw new Exception("Can't use the operand \"" + jsonObject.get("op") + "\" on the non int type variable \"" + name + "\"");
					}
					int registerCount = usedRegister.size();
					switch(jsonObject.get("op")) {
						case "++":
							textAreaData += "lw $t" + registerCount + ", " + name + "\n";
							usedRegister.add("t" + registerCount);
							textAreaData += "addi $t" + registerCount + ", $t" + registerCount + ", 1\n";
							textAreaData += "sw $t" + registerCount + ", " + name + "\n";
							usedRegister.remove("t" + registerCount);
							usedRegister.remove(registerCount - 1);
							break;
						case "--":
							textAreaData += "lw $t" + registerCount + ", " + name + "\n";
							usedRegister.add("t" + registerCount);
							textAreaData += "subi $t" + registerCount + ", $t" + registerCount + ", 1\n";
							textAreaData += "sw $t" + registerCount + ", " + name + "\n";
							usedRegister.remove("t" + registerCount);
							usedRegister.remove(registerCount - 1);
							break;
						case "+=":
							textAreaData += "lw $t" + registerCount + ", " + name + "\n";
							usedRegister.add("t" + registerCount);
							textAreaData += "add $t" + registerCount + ", $t" + registerCount + ", $" + usedRegister.get(registerCount - 1) + "\n";
							textAreaData += "sw $t" + registerCount + ", " + name + "\n";
							usedRegister.remove("t" + registerCount);
							usedRegister.remove(registerCount - 1);
							break;
						case "-=":
							usedRegister.add("t" + registerCount);
							textAreaData += "sub $t" + registerCount + ", $t" + registerCount + ", $" + usedRegister.get(registerCount - 1) + "\n";
							textAreaData += "sw $t" + registerCount + ", " + name + "\n";
							usedRegister.remove("t" + registerCount);
							usedRegister.remove(registerCount - 1);
							break;
						case "/=":
							textAreaData += "lw $t" + registerCount + ", " + name + "\n";
							usedRegister.add("t" + registerCount);
							textAreaData += "div $t" + registerCount + ", $t" + registerCount + ", $" + usedRegister.get(registerCount - 1) + "\n";
							textAreaData += "sw $t" + registerCount + ", " + name + "\n";
							usedRegister.remove("t" + registerCount);
							usedRegister.remove(registerCount - 1);
							break;
						case "*=":
							textAreaData += "lw $t" + registerCount + ", " + name + "\n";
							usedRegister.add("t" + registerCount);
							textAreaData += "mul $t" + registerCount + ", $t" + registerCount + ", $" + usedRegister.get(registerCount - 1) + "\n";
							textAreaData += "sw $t" + registerCount + ", " + name + "\n";
							usedRegister.remove("t" + registerCount);
							usedRegister.remove(registerCount - 1);
							break;
					}
				}
			// Variable
			case "VAR":
				name = jsonObject.get("name");
				if(!variableTypeTable.containsKey(name)) {
					throw new Exception("Undefined variable \"" + name + "\"");
				}
				switch(variableTypeTable.get(name)) {
					case "int":
						textAreaData += "lw $t" + usedRegister.size() + ", " + name + "\n";
						break;
					case "float":
						textAreaData += "la $t" + usedRegister.size() + ", " + name + "\n";
						break;
					case "string":
						textAreaData += "la $t" + usedRegister.size() + ", " + name + "\n";
						break;
				}
				usedRegister.add("t" + usedRegister.size());
				break;
			// Statement
			case "statement":
				switch(jsonObject.get("op")) {
					case "==":
						left = usedRegister.get(usedRegister.size() - 2);
						right = usedRegister.get(usedRegister.size() - 1);
						textAreaData += "beq $" + left + ", $" + right + ", if_" + ifCount + "\n";
						usedRegister.remove(left);
						usedRegister.remove(right);
						break;
				}
				break;
			// While
			case "while":
				break;
			// if
			case "if":
				ifCount++;
				break;
			// else
			case "else":
				break;
			// else if
			case "else if":
				break;
			// print
			case "print":
				textAreaData += "li $v0, 4\nla $a0, string_" + (stringCount - 1) + "\nsyscall\n";
				break;
			// read
			case "read":
				
				break;
			// Concat
			case "Concat":
				break;
			// ADD
			case "ADD":
				if(usedRegister.size() < 2) {
					throw new Exception("Sementic error, there isn't 2 number to add");
				}
				left = "$" + usedRegister.get(usedRegister.size() - 2);
				right = "$" + usedRegister.get(usedRegister.size() - 1);
				textAreaData += "add $t" + (usedRegister.size() - 2) + ", " + left + ", " + right + "\n";
				usedRegister.remove(usedRegister.size() - 2);
				usedRegister.remove(usedRegister.size() - 1);
				usedRegister.add("t" + usedRegister.size());
				break;
			// Minus
			case "MINUS":
				if(usedRegister.size() < 2) {
					throw new Exception("Sementic error, there isn't 2 number to subtract");
				}
				left = "$" + usedRegister.get(usedRegister.size() - 2);
				right = "$" + usedRegister.get(usedRegister.size() - 1);
				textAreaData += "sub $t" + (usedRegister.size() - 2) + ", " + left + ", " + right + "\n";
				usedRegister.remove(usedRegister.size() - 2);
				usedRegister.remove(usedRegister.size() - 1);
				usedRegister.add("t" + usedRegister.size());
				break;
			// Mult
			case "MULT":
				if(usedRegister.size() < 2) {
					throw new Exception("Sementic error, there isn't 2 number to mult");
				}
				left = "$" + usedRegister.get(usedRegister.size() - 2);
				right = "$" + usedRegister.get(usedRegister.size() - 1);
				textAreaData += "mul $t" + (usedRegister.size() - 2) + ", " + left + ", " + right + "\n";
				usedRegister.remove(usedRegister.size() - 2);
				usedRegister.remove(usedRegister.size() - 1);
				usedRegister.add("t" + usedRegister.size());
				break;
			// Div
			case "DIV":
				if(usedRegister.size() < 2) {
					throw new Exception("Sementic error, there isn't 2 number to divide");
				}
				left = "$" + usedRegister.get(usedRegister.size() - 2);
				right = "$" + usedRegister.get(usedRegister.size() - 1);
				textAreaData += "div $t" + (usedRegister.size() - 2) + ", " + left + ", " + right + "\n";
				usedRegister.remove(usedRegister.size() - 2);
				usedRegister.remove(usedRegister.size() - 1);
				usedRegister.add("t" + usedRegister.size());
				break;
			// Neg
			case "NEG":
				textAreaData += "li $t" + usedRegister.size() + ", -" + jsonObject.get("value");
				break;
			// Int
			case "INT":
				textAreaData += "li $t" + usedRegister.size() + ", " + jsonObject.get("value") + "\n";
				usedRegister.add("t" + usedRegister.size());
				break;
			// Float
			case "FLOAT":
				break;
			// String
			case "STRING":
				dataAreaData += "string_" + stringCount + ": .asciiz \"" + jsonObject.get("value").replace("\\", "\\\\") + "\"\n";
				stringCount++;
				break;
			// Bool
			case "BOOL":
				dataAreaData += "li $t0, ";
				if(jsonObject.get("value").equals("true")) {
					dataAreaData += "1\n";		
				}else{
					dataAreaData += "0\n";
				}
				break;
		}
    }

	/**
	 * Cette méthode permet de parser un objet JSON
	 * @param Objet JSON sous la forme d'un String
	 * @return chaque propriétés de l'objet JSON et ses valeurs dans une HashMap
	 * */
	public HashMap<String, String> parse(String responseBody) {
		HashMap<String, String> datas = new HashMap<String, String>();
		JSONObject album = new JSONObject(new JSONTokener(responseBody));
		Iterator<String> keys = album.keys();
		while(keys.hasNext()) {
			String currentKey = keys.next();
			datas.put(currentKey, String.valueOf(album.get(currentKey)));
		}
		return datas;
	}
	
	/**
	 * Cette méthode permet de parser un tableau d'objet JSON.
	 * @param Tableau JSON sous la forme d'un String
	 * @return Une ArrayList des données de chaque objet JSON contenu dans une HashMap
	 * */
	public ArrayList<HashMap<String, String>> parseArray(String responseBody) {
		ArrayList<HashMap<String, String>> datas = new ArrayList<HashMap<String,String>>();
		JSONArray albums = new JSONArray(responseBody);
		for(int i = 0; i < albums.length(); i++) {
			JSONObject album = albums.getJSONObject(i);
			datas.add(new HashMap<String, String>());
			Iterator<String> keys = album.keys();
			while(keys.hasNext()) {
				String currentKey = keys.next();
				datas.get(i).put(currentKey, String.valueOf(album.get(currentKey)));
			}
		}
		return datas;
	}
    
    private void Write(String parsedText){
        if(outputFile == null || exprOut == null){
            try {
            	outputFile = new FileWriter(outputFileName, true);
                exprOut = new BufferedWriter(outputFile);
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        try{
            exprOut.write(parsedText);
            exprOut.close();
            exprOut = null;
        }catch (IOException e){
            System.out.println("An error as occured");
            e.printStackTrace();
        }
    }
}