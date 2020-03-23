package XEassembler;

import java.util.ArrayList;
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
public class Pass1 {

    String progName = "";
    ArrayList <Hashtable< String, Integer>> symtab;
    public ArrayList<AssemblyLine> lines;// mtfasas kol 7eta fl line  bta3 el instruction
    public ArrayList<Hashtable<String,Integer>> extdef;
     public ArrayList<Integer>pc;
       public ArrayList<Integer>  prevpc;
      public ArrayList<ArrayList<String>>extref;
    Optable optab;
        Hashtable< String, Integer> CSECT;
Hashtable< String, Integer> symbtab;
public ArrayList<Integer>begincount;
       public ArrayList<Integer>  endcount;
             public ArrayList<Integer>   lineCountstart;
public ArrayList<Integer>   lineCountend;
    public void run(ArrayList<String> codeLines) {

        //int pc = 0,prevpc=0
         lineCountstart=new ArrayList<Integer>();
        lineCountend=new ArrayList<Integer>();
          begincount=new ArrayList<Integer>();
            endcount=new ArrayList<Integer>();
        pc=new ArrayList<Integer>();
        prevpc=new ArrayList<Integer>();
        String label;
        String inst;
        String operand;
        progName = "";
        symbtab=new Hashtable< String, Integer>();
       extref=new ArrayList<ArrayList<String>>();
        extdef=new ArrayList<Hashtable<String,Integer>>();
        symtab = new ArrayList <Hashtable< String, Integer>>();
        lines = new ArrayList<AssemblyLine>();
        optab = new Optable();
       CSECT = new Hashtable< String,Integer>();
     int csect=0;
     CSECT.put("" ,csect);
      pc.add(csect, 0);
       prevpc.add(csect, 0);
symtab.add(symbtab);
int lc=0;


        for (int i = 0; i < codeLines.size(); i++) {


            label = "";
            inst = "";
            operand = "";

            int linelen = codeLines.get(i).length();
            if (codeLines.get(i).charAt(0) == '.') {
                continue; // comment so i will skip it
            }

            // extract label
            if (linelen > 8) {
                label = codeLines.get(i).substring(0, 8);
            } else {// error 3shan mynf3sh ykoon fe label lw7do 
                label = codeLines.get(i).substring(0, linelen);
            }

            // extract instruction
            if (linelen > 8) {
                if (linelen > 15) {
                    inst = codeLines.get(i).substring(9, 15);
                } else { 
                    inst = codeLines.get(i).substring(9, linelen);
                }
            }
           
            
             // extract operand
            if (linelen > 15) {
                if (linelen > 35) {
                    operand = codeLines.get(i).substring(15, 35);
                } else {
                    operand = codeLines.get(i).substring(15, linelen);
                }
            }
            //removing spaces
             label = label.trim();
            inst = inst.trim();
             operand= operand.trim();
             
             
            
             
            if(inst.compareToIgnoreCase("csect")==0&&!CSECT.containsKey(label))
            {   
            lineCountend.add(csect, lc-1);
        
                System.out.println("pc   "+pc.get(csect)+" csect  "+csect); 
                endcount.add(pc.get(csect));
                symtab.add(new Hashtable< String, Integer>());
                csect=csect+1;
                 lineCountstart.add(csect,lc);
               CSECT.put(label,csect);
                pc.add(csect, 0);
                lines.add(new AssemblyLine(label,inst,operand,pc.get(csect),false,""));
                begincount.add( pc.get(csect));
                
                                 System.out.println("pc   "+pc.get(csect)+" csect  "+csect);
            }else
                 if(inst.compareToIgnoreCase("csect")==0&&CSECT.containsKey(label))
            {
                csect=CSECT.get(label);
                lines.add(new AssemblyLine(label,inst,operand,pc.get(csect),false,""));
            }else
                
if(inst.compareToIgnoreCase("extdef")==0)
{   int q = 0;
String[]tokens=null;
Hashtable< String, Integer> extDEF=new Hashtable<>();
 operand = operand.trim();
 tokens = operand.split(",");
while(tokens.length>q){
  extDEF.put(tokens[q], -1);
 q++;
          } 
lines.add(new AssemblyLine(label,inst,operand,pc.get(csect),false,""));

extdef.add(extDEF);
}else
    if(inst.compareToIgnoreCase("extref")==0)
{   int q = 0;
String[]tokens=null;
operand = operand.trim();
 tokens = operand.split(",");
ArrayList extREF=new ArrayList<String>();
 
while(tokens.length>q){
  extREF.add( tokens[q]);
 q++;
          } 
                lines.add(new AssemblyLine(label,inst,operand,pc.get(csect),false,""));

             extref.add(extREF);
}else
    
            if (inst.compareToIgnoreCase("start") == 0) {
                lineCountstart.add(csect,lc);
            pc.set(csect,  Integer.parseInt(operand,16));// b2olo 7awlha le hexa
                    System.out.println("pc   "+pc.get(csect)+" csect  "+csect);
            begincount.add(0, pc.get(csect));
            progName=label;
            AssemblyLine l=new AssemblyLine(label,inst,operand,pc.get(csect),false,"");            
            if(progName.isEmpty())
            {
             l.seterrormsg(".ERROR! please specify prog name") ;  
            }
            lines.add(l);
            } 
          else if (inst.compareToIgnoreCase("EQU") == 0) {
                int y=0,index = 0,pos=0,a = 0,b = 0;
                String[] tokens = operand.split("\t");
                String operand1 ,operand2 ; operand = operand.trim();
                if (operand.compareToIgnoreCase("*")==0){               
                   operand=Integer.toHexString(prevpc.get(csect));
                y=prevpc.get(csect);
                lines.add(new AssemblyLine(label,inst,"*",y,false,""));
                symtab.get(csect).put(label,y);
               System.out.println(label+" "+Integer.toHexString(y)+" A");
              continue;}
                else if(isInteger(operand)){
                   y=Integer.parseInt(operand);               
               lines.add(new AssemblyLine(label,inst,operand,y,false,""));
                symtab.get(csect).put(label,y);
               System.out.println(label+" "+Integer.toHexString(y)+" A");
              continue;}
                else if( tokens[index].indexOf('-')>0){
                 pos = tokens[index].indexOf('-');   
                   operand1 = tokens[index].substring(0, pos);
                   operand2= tokens[index].substring(pos + 1);                   
                   if (!isInteger(operand1) & !isInteger(operand2)){                   
                   if(symtab.get(csect).get(operand1)== null | symtab.get(csect).get(operand2)==null){
                   lines.add(new AssemblyLine(label,inst,operand,pc.get(csect),false,""));
             lines.add(new AssemblyLine("",".ERROR! wrong expression, unknown label ","",pc.get(csect),false,""));}
                   else{ a=symtab.get(csect).get(operand1);
                   b=symtab.get(csect).get(operand2); y=a-b;  
                       lines.add(new AssemblyLine(label,inst,operand,y,false,""));
                  symtab.get(csect).put(label,y);
               System.out.println(label+" "+Integer.toHexString(y)+" A");}
              continue; } else if(!isInteger(operand1) & isInteger(operand2)){
               b=symtab.get(csect).get(operand1);             
               y=b-Integer.parseInt(operand2);
                lines.add(new AssemblyLine(label,inst,operand,y,false,""));
                  symtab.get(csect).put(label,y);
               System.out.println(label+" "+Integer.toHexString(y)+" A");
                 continue;
              }
                   else {
                    lines.add(new AssemblyLine(label,inst,operand,pc.get(csect),false,""));
             lines.add(new AssemblyLine("",".ERROR! wrong expression ","",pc.get(csect),false,""));} 
              continue; }
                else if(!operand.startsWith("*") & tokens[index].indexOf('*')>0 ){
                  lines.add(new AssemblyLine(label,inst,operand,pc.get(csect),false,""));
            lines.add(new AssemblyLine("",".ERROR! wrong expression ","",pc.get(csect),false,""));
              continue;  }
                else if(tokens[index].indexOf('+')>0 & !isInteger(tokens[index].substring(tokens[index].indexOf('+') + 1)) & !isInteger(tokens[index].substring(0,tokens[index].indexOf('+')))){
                      lines.add(new AssemblyLine(label,inst,operand,pc.get(csect),false,""));
            lines.add(new AssemblyLine("",".ERROR! wrong expression ","",pc.get(csect),false,""));
              continue;  }                
                else{operand1 = tokens[index].substring(0, tokens[index].indexOf('+'));
                   operand2= tokens[index].substring(tokens[index].indexOf('+') + 1);
                if(!isInteger(operand1)){
                     a=symtab.get(csect).get(operand1); 
                   y=a+Integer.parseInt(operand2);
                 lines.add(new AssemblyLine(label,inst,operand,y,false,""));
                  symtab.get(csect).put(label,y);
               System.out.println(label+" "+Integer.toHexString(y)+" A");
                 continue;  }  
                else if(!isInteger(operand2)) { b=symtab.get(csect).get(operand2);
                   y=b+Integer.parseInt(operand1);
                 lines.add(new AssemblyLine(label,inst,operand,y,false,""));
                  symtab.get(csect).put(label,y);
               System.out.println(label+" "+Integer.toHexString(y)+" A");
                   continue;     }
                else{
                y=Integer.parseInt(operand2)+Integer.parseInt(operand1);
                 lines.add(new AssemblyLine(label,inst,operand,y,false,""));
                  symtab.get(csect).put(label,y);
               System.out.println(label+" "+Integer.toHexString(y)+" A");
                 continue;  }
                } 
            } else if (inst.compareToIgnoreCase("ORG") == 0) {
                if(isInteger(operand)){
                pc.set(csect, Integer.valueOf(operand, 16));
                lines.add(new AssemblyLine(label,inst,operand,pc.get(csect),false,""));
                } else if(symtab.get(csect).get(operand)!= null){
                pc.set(csect, symtab.get(csect).get(operand));
                lines.add(new AssemblyLine(label,inst,operand,pc.get(csect),false,""));
                }prevpc=pc;
                continue;
               
            }
            else if(symtab.get(csect).containsKey(label)){
            lines.add(new AssemblyLine(label,inst,operand,pc.get(csect),false,""));
            lines.add(new AssemblyLine("",".ERROR! symbol "+"'"+label+"' already defined","",pc.get(csect),false,""));
           
            }
            else if (inst.compareToIgnoreCase("END") == 0) {
                lines.add(new AssemblyLine(label,inst,operand,pc.get(csect),false,""));
                 prevpc.set(csect, pc.get(csect)); 
                 System.out.println("pc   "+pc.get(csect)+" csect  "+csect+" R");
                 endcount.add(pc.get(csect));
                 lineCountend.add(csect,lc);
            } else if (inst.compareToIgnoreCase("BASE") == 0) {
               lines.add(new AssemblyLine(label,inst,operand,pc.get(csect),false,"")); 
               prevpc.set(csect, pc.get(csect));              
            } else if (inst.compareToIgnoreCase("RESW") == 0) {
                lines.add(new AssemblyLine(label,inst,operand,pc.get(csect),false,""));
                symtab.get(csect).put(label,pc.get(csect));
                System.out.println(label+" "+Integer.toHexString(pc.get(csect))+" R");
               prevpc.set(csect, pc.get(csect)); 
                pc.set(csect, pc.get(csect)+3*Integer.parseInt(operand));
            } else if (inst.compareToIgnoreCase("RESB") == 0) {
               lines.add(new AssemblyLine(label,inst,operand,pc.get(csect),false,""));
                symtab.get(csect).put(label,pc.get(csect));
                System.out.println(label+" "+Integer.toHexString(pc.get(csect))+" R");
                prevpc.set(csect, pc.get(csect)); 
                pc.set(csect, pc.get(csect)+Integer.parseInt(operand));
            } else if (inst.compareToIgnoreCase("WORD") == 0) {
                lines.add(new AssemblyLine(label,inst,operand,pc.get(csect),true,""));
                symtab.get(csect).put(label,pc.get(csect));
                System.out.println(label+" "+Integer.toHexString(pc.get(csect))+" R");
               prevpc.set(csect, pc.get(csect)); 
               pc.set(csect, pc.get(csect)+3);
            } else if (inst.compareToIgnoreCase("BYTE") == 0) {
                 lines.add(new AssemblyLine(label,inst,operand,pc.get(csect),true,""));
                symtab.get(csect).put(label,pc.get(csect));
                System.out.println(label+" "+Integer.toHexString(pc.get(csect))+" R");
               if(operand.contains("C")||operand.contains("c")){
                   pc.set(csect, pc.get(csect)+operand.length()-3);
               } else {
                   pc.set(csect, pc.get(csect)+(operand.length()-3)/2);
               } prevpc.set(csect, pc.get(csect)); 
            }
            else {
if(!label.equalsIgnoreCase("") && inst.compareToIgnoreCase("start") != 0)
{
    symtab.get(csect).put(label,pc.get(csect));
    System.out.println(label+" "+Integer.toHexString(pc.get(csect))+" R");
}
lines.add(new AssemblyLine(label,inst,operand,pc.get(csect),true,""));
 prevpc.add(csect, pc.get(csect)); 
if(inst.startsWith("+")){
    pc.set(csect,pc.get(csect)+4);
}else if (optab.opTable.containsKey(inst.toUpperCase())){
    
    pc.set(csect,pc.get(csect)+optab.opTable.get(inst.toUpperCase()).format);
}
            }
 if(extdef.size()>csect)         
     if(extdef.get(csect).containsKey(label)) 
            {
                extdef.get(csect).replace(label, pc.get(csect));
            }
 lc++;
        }
//         if(extdef.get(csect).containsValue(-1))
//            {      
//                extdef.get(csect).
//                System.out.println("ERROR! Extdef not defned:  "+extdef.get(csect).get(-1)); 
//            }

    }
    public static boolean isInteger(String s) {
    try { 
        Integer.parseInt(s); 
    } catch(NumberFormatException e) { 
        return false; 
    } catch(NullPointerException e) {
        return false;
    }
    // only got here if we didn't return false
    return true;
}

}
