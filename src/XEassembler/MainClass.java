package XEassembler;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Aya
 */
public class MainClass {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
    
        FileHandler x= new FileHandler(); 
        FileHandler y= new FileHandler(); 
        FileHandler z= new FileHandler(); 
        ArrayList<String> a = x.readFile("sourcefile.txt");// el file el h2rah... el a hya hya el code el fl filehandlerzay         
        Pass1 pass1 = new Pass1();
        pass1.run(a);
        x.ListFile("pass1file.txt", pass1.lines);
        Pass2 pass2 = new Pass2();
        pass2.run(pass1.symtab,pass1.lines,pass1.CSECT,pass1.extdef,pass1.extref);
        ObjectFileGenerator generate = new ObjectFileGenerator();
        
        y.ListFile("pass2file.txt", pass2.line);
        
        generate.run(pass1.progName , pass2.line,pass1.symtab,pass1.CSECT,pass1.extdef,pass1.begincount,pass1.endcount,pass1.lineCountstart,pass1.lineCountend,pass1.extref);
        
}
                
    }
    