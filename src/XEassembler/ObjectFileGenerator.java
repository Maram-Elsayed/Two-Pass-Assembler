package XEassembler;




import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.io.BufferedWriter;
import java.util.Map;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Aya
 */
public class ObjectFileGenerator {
    
    
    public void run(String progName , ArrayList<AssemblyLine> lines,ArrayList<Hashtable<String, Integer> >symtab,Hashtable< String, Integer> CSECT,
                   ArrayList<Hashtable<String,Integer>> extdef,ArrayList<Integer> begin,ArrayList<Integer> end,
                   ArrayList<Integer> linecountstart,ArrayList<Integer> linecountend,ArrayList<ArrayList<String>> extref) throws IOException
    {  
       
        Integer x=0;
         int r=0;
                     while(!CSECT.isEmpty()){

                     
          File file = new File ("HTErecord.txt");
          BufferedWriter out = new BufferedWriter(new FileWriter(file));
         
         String key= null;
       
        for(Map.Entry <String,Integer>entry: CSECT.entrySet()){
            if(x.equals(entry.getValue())){
                key =  entry.getKey();
                break; //breaking because its one to one map
            }
        }
        if(x==0)
         HeaderRecord(progName, begin.get(x), end.get(x) -  lines.get(0).pc,out);
       else HeaderRecord(key, begin.get(x), end.get(x)-  begin.get(x),out);
        out.write(System.lineSeparator());
       
        if(x<extdef.size())
        DefineRecord(extdef.get(x),out);
        out.write(System.lineSeparator());
        
         RefRecord(extref.get(x),out);        
        out.write(System.lineSeparator());


       TextRecord(begin.get(x),end.get(x), end.get(x) -  begin.get(x), lines,out,linecountstart.get(x),linecountend.get(x));
        out.write(System.lineSeparator());
            
        ModificationRecord(extref.get(x),lines,symtab.get(x),out);
            out.write(System.lineSeparator());
       
        EndRecord(lines.get(0).pc,out,x);
    
         out.close();
        
      CSECT.values().remove(x);
        x++;
        }
         
    }
    
    public void HeaderRecord (String progName, Integer startAddress , Integer progLength,BufferedWriter out) throws IOException
    {       System.out.println("H^"+ progName+"^"+formatZeros(Integer.toHexString(startAddress),6)+"^"+ formatZeros(Integer.toHexString(progLength),6));
        out.write("H^"+ progName+"^"+formatZeros(Integer.toHexString(startAddress),6)+"^"+ formatZeros(Integer.toHexString(progLength),6));
    }

public void DefineRecord (Hashtable<String,Integer> extDEF,BufferedWriter out) throws IOException  
{String buf="D^";
    
      for(Map.Entry<String,Integer> entry : extDEF.entrySet()) {
    String key = entry.getKey();
    Integer value = entry.getValue();
    buf+=key;
    buf+="^";
    buf+=formatZeros(Integer.toHexString(value-1),6);
//    System.out.println("D^"+key+"^"+ formatZeros(Integer.toHexString(value-1),6));
//       out.write("D^"+key+"^"+ formatZeros(Integer.toHexString(value-1),6));
//                   out.write(System.lineSeparator());
                  
           
      }    
      System.out.println(buf); out.write(buf);
    
}
public void RefRecord (ArrayList<String> extref,BufferedWriter out) throws IOException  
{
    int lineCount = 0; 
    String buf="";
    List<String> ext = new ArrayList<>(); 
    while(lineCount < extref.size()) {
    ext.add(extref.get(lineCount));
    lineCount++;}
       buf="R";    
        for (String s : ext) {
            buf+="^";
            buf += s;}    
      System.out.println(buf); out.write(buf);

    
}
   public void TextRecord (Integer startAdress,Integer end, Integer progLength,ArrayList<AssemblyLine> lines,
           BufferedWriter out,int linecountstart,int linecountend) throws IOException {
          List<String> obj = new ArrayList<>(); 
          String buf="";
        int startT = startAdress;
        int lineCount = linecountstart;
        String code; 
        int len=end-startAdress;
        int count = 0;
        //startT=lines.get(0).pc;
        code="";
        //System.out.println("counter  "+linecountstart+" end " +linecountend);
        while (lines.size()>lineCount&&lines.get(lineCount).pc < end && linecountend>lineCount) {
            
              code=lines.get(lineCount).objCode;       
              
              if(count+code.length()/2>30){                
               buf = String.format("T^%06X^%02X", startT, count);          
               for (String s : obj) {
                     buf+="^";
                     buf += s;}               
                System.out.println(buf);
              count=0;startT=lines.get(lineCount++).pc;
              obj.clear(); out.write(buf);out.newLine();
              count+=code.length()/2;obj.add(code);continue;
              } 
              if(code.compareTo("")!=0){
                  count+=code.length()/2;
              obj.add(code);
              }
              lineCount++;              
        }         
           buf = String.format("T^%06X^%02X", startT, count);          
        for (String s : obj) {
            buf+="^";
            buf += s;}
        
        System.out.println(buf); out.write(buf);
        

      
}
     public void ModificationRecord (ArrayList<String> extref,ArrayList<AssemblyLine> lines,Hashtable<String, Integer> symtab,BufferedWriter out) throws IOException
    { int startT =0,lineCount=0;
    String operand="";
      while(lineCount < lines.size()){
          operand=lines.get(lineCount).operand;
          if(lines.get(lineCount).inst.charAt(0) == '+' ){     
              if(lines.get(lineCount).operand.charAt(0) == '#' || lines.get(lineCount).operand.charAt(0) == '#')
                   operand = operand.substring(1);
              else
                  operand=lines.get(lineCount).operand;
              if(symtab.containsKey(operand)){
              startT=lines.get(lineCount).pc+1;
            System.out.println("M^"+formatZeros(Integer.toHexString(startT),6)+"^"+"05");
              out.write("M^"+formatZeros(Integer.toHexString(startT),6)+"^"+"05");
            out.write(System.lineSeparator());}
              else if(extref.contains(operand)){
              startT=lines.get(lineCount).pc+1;
            System.out.println("M^"+formatZeros(Integer.toHexString(startT),6)+"^"+"05"+ "^"+operand);
              out.write("M^"+formatZeros(Integer.toHexString(startT),6)+"^"+"05"+"^"+operand);
            out.write(System.lineSeparator());}
           
          }lineCount++;
      }
    
    }
    
    public void EndRecord (Integer startAddress,BufferedWriter out,int x) throws IOException
    {    if(x==0){
         System.out.println(String.format("E^%1$06X", startAddress)); 
        out.write(String.format("E^%1$06X", startAddress));}
    else{
         System.out.println("E^"); 
        out.write("E^");}
    }
    
    /**
     * Format zeros String function is  to add "0" to each string to format it as file format
     */
    String formatZeros(String string , int reqDigits)
    {
        while (string.length() < reqDigits)
        {
            string = "0" + string;
        }
        return string.toUpperCase();
    }
    
    String formatSpaces(String string , int reqDigits)
    {
        while (string.length() < reqDigits)
        {
            string += " ";
        }
        return string;
    }
    
               
        
}
