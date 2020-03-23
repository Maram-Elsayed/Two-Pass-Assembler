package XEassembler;


import java.util.Hashtable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Aya
 */
public class RegTab {
    
    
    Hashtable<String,Integer> regTable;

  

    public RegTab()
    {
        regTable = new Hashtable<String,Integer>();
        
        regTable.put("A" , 0);
        regTable.put("X" , 1);
        regTable.put("L" , 2);
        regTable.put("B" , 3);
        regTable.put("S" , 4);
        regTable.put("T" , 5);
        regTable.put("F" , 6);
    }
    
  
    
    
    
}
