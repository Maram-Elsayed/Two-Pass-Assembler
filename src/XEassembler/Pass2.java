package XEassembler;



import static XEassembler.Pass1.isInteger;
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
public class Pass2 {
    ArrayList<AssemblyLine> lines;
    String objcode; 
    private int baseAddress;
    ArrayList<AssemblyLine> line;
     Optable optab;
             
     RegTab regtab;
    public void run(ArrayList<Hashtable<String, Integer> >symtab, ArrayList<AssemblyLine> lines ,
            Hashtable< String, Integer> CSECT,ArrayList<Hashtable<String,Integer>>extdef,ArrayList<ArrayList<String>> extref) 
    {
        int disp=0;
        optab = new Optable();
        regtab=new RegTab();
        String label;
        String inst;
        String operand;
        String operand1;
        String operand2;  
        String[] tokens=null;
        Integer pos=null,pc=null;
        line=new ArrayList<AssemblyLine>();
           
     int csect=0;
//   int cs=0;
//     while(CSECT.size()>cs)
//     {  int ss=0;
//         while(extref.size()>ss)
//         {  int cc=0;
//             while(extdef.size()>cc)
//             { if(extdef.get(cc).contains(extref.get(ss)))
//                 symtab.get(cs).putAll(extdef.get(cs));
//             cc++;}
//             ss++;
//         }  
//         cs++;
//     }
         
         
     
         for (int j = 0; j < lines.size(); j++) {
            AssemblyLine l=lines.get(j);            
            label = l.label;
            inst = l.inst;
            operand = l.operand;
            pc=l.pc;
            operand1="";
            operand2="";
            objcode = "";
             if (lines.get(j).inst.startsWith(".")) 
             {            line.add(new AssemblyLine(label,inst,operand,pc,false,objcode));  
                continue; }// comment so i will skip it
//             if(symtab.get(csect).get(operand)==null)
//             { if(extref.get(csect).contains(operand))       
//                          
//                 if(symtab.size()>csect)
//                 {int cc=0;
//                 while(extdef.size()>cc)
//                 {     if(extdef.get(cc).containsKey(operand))
//                     symtab.get(csect).put(operand,extdef.get(cc).get(operand));
//                 cc++;
//                 }}   
//                     }
             if(inst.startsWith("+"))
                inst= inst.substring(1);
             if(inst.compareToIgnoreCase("extdef")==0||inst.compareToIgnoreCase("extref")==0)
{            line.add(new AssemblyLine(label,inst,operand,pc,false,objcode));  }
             else
                 if(inst.compareToIgnoreCase("csect")==0)
            {
                csect=CSECT.get(label);
 line.add(new AssemblyLine(label,inst,operand,pc,false,objcode));
            }
                 else
                     


             if(inst.compareToIgnoreCase("start")==0)
                line.add(new AssemblyLine(label,inst,operand,pc,false,objcode));
             else if (inst.compareTo("EQU") == 0) {            
            line.add(l); continue;
        }  else if (inst.compareTo("ORG") == 0) {            
            line.add(l); continue;
        }  
             else if (optab.opTable.containsKey(inst)){
                 
                   switch (optab.opTable.get(inst.toUpperCase()).format) {
                case 1:
                    objcode = optab.opTable.get(inst.toUpperCase()).code;
                     line.add(new AssemblyLine(label,inst,operand,pc,false,objcode));
                    
                    break;
                case 2:
                    objcode = optab.opTable.get(inst.toUpperCase()).code;                     
                    operand = operand.trim();
                    tokens = operand.split("\t");
                    int index = 0;
                    pos = tokens[index].indexOf(',');
                if (pos >= 0) {
                    operand1 = tokens[index].substring(0, pos);
                    operand2= tokens[index].substring(pos + 1); 
                    if(isInteger(operand2)){
                    objcode += Integer.toHexString(regtab.regTable.get(operand1.toUpperCase()));
                    objcode += Integer.toHexString(Integer.parseInt(operand2));
                    line.add(new AssemblyLine(label,inst,operand,pc,false,objcode));}
                    else{
                    objcode += Integer.toHexString(regtab.regTable.get(operand1.toUpperCase()));
                    objcode += Integer.toHexString(regtab.regTable.get(operand2.toUpperCase()));
                    line.add(new AssemblyLine(label,inst,operand,pc,false,objcode));}}
                else  {objcode +=Integer.toHexString(regtab.regTable.get(l.operand.toUpperCase()))+"0";
                    line.add(new AssemblyLine(label,inst,operand,pc,false,objcode));}
                    break;
                case 3:
                    final int n = 1 << 5;
                    final int i = 1 << 4;
                    final int x = 1 << 3;
                    final int b = 1 << 2;
                    final int p = 1 << 1;
                    final int e = 1;
                    
                    int code = Integer.parseInt(optab.opTable.get(inst.toUpperCase()).code, 16) << 4;                    
                    
                  if(l.operand.compareToIgnoreCase("*")==0){
                              operand=Integer.toString(pc);}
                      else if (l.operand.compareTo("")==0) {
                       code =3+ Integer.parseInt(optab.opTable.get(inst.toUpperCase()).code, 16) << 4;                      
                        disp=0;
                         code = (code << 12) | (disp & 0xFFF);                         
                         objcode = String.format(l.inst.charAt(0) == '+' ? "%08X" : "%06X", code) ;
                         line.add(new AssemblyLine(label,inst,operand,pc,false,objcode));
                         continue;
                    }   else {
                        switch (operand.charAt(0)) {
                            case '#': // immediate addressing
                                code |= i;
                                
                                operand = operand.substring(1);
                                break;
                            case '@': // indirect addressing
                                code |= n;

                                operand = operand.substring(1);
                                break;
                            default: // simple/direct addressing
                                code |= n | i;
                                operand = operand.trim();
                    tokens = operand.split("\t");
                    index = 0;
                    pos = tokens[index].indexOf(',');
                if (pos >= 0) {
                    operand= tokens[index].substring(0, pos);
                    operand2= tokens[index].substring(pos + 1);}
                
                                if (operand2.compareToIgnoreCase("x")==0) {
                                    code |= x;
                                }
                        }  
                          int a=0,c=0;                   
                        int h=0; 
                        tokens = operand.split("\t");
                      if( tokens[h].indexOf('-')>0){
                 pos = tokens[h].indexOf('-');   
                   operand1 = tokens[h].substring(0, pos);
                   operand2= tokens[h].substring(pos + 1);                   
                   if (!isInteger(operand1) & !isInteger(operand2)){
                   a=symtab.get(csect).get(operand1);
                   c=symtab.get(csect).get(operand2);                              
                  disp=a-c;}
                   else if(!isInteger(operand1) & isInteger(operand2)){
              a=symtab.get(csect).get(operand1); 
                  disp=a-Integer.parseInt(operand2);            
              }
                   else {
                    line.add(new AssemblyLine(label,inst,operand,pc,false,""));
             line.add(new AssemblyLine("",".ERROR! wrong expression ","",pc,false,""));} 
              }
                else if(tokens[h].indexOf('+')>0 ){
                    pos=tokens[h].indexOf('+');
                    operand1 = tokens[h].substring(0, tokens[h].indexOf('+'));
                   operand2= tokens[h].substring(tokens[h].indexOf('+') + 1);
                    if(!isInteger(operand1) & !isInteger(operand2)){
                      line.add(new AssemblyLine(label,inst,operand,pc,false,""));
            line.add(new AssemblyLine("",".ERROR! wrong expression ","",pc,false,""));
              continue;  }
                else{
                if(!isInteger(operand1)){
                     a=symtab.get(csect).get(operand1); 
                  disp=a+Integer.parseInt(operand2);
                 }
                else if(!isInteger(operand2)) { c=symtab.get(csect).get(operand2);
                   disp=c+Integer.parseInt(operand1);
               }
                else
                disp=Integer.parseInt(operand2)+Integer.parseInt(operand1);
                 
                  
                }
                           
                }
                        
                        
                        if (symtab.get(csect).get(operand)!= null ) {                            
                            int targetAddress = symtab.get(csect).get(operand);                            
                            disp = targetAddress;
                            
                            if (l.inst.charAt(0) != '+') {
                                disp -= pc + 3;
                                
                                if (disp >= -2048 && disp <= 2047) {
                                    code |= p;
                                } else {
                                    code |= b;
                                    
                                    disp = targetAddress - baseAddress;
                                }
                            }
                        } 
                        if (isInteger(operand) & l.operand.startsWith("#"))
                                disp=Integer.parseInt(operand);
                        
                        if (l.inst.charAt(0) == '+') {
                            code |= e;    
                              if(symtab.get(csect).get(operand)==null)
                                 disp=0;
                              else
                            disp = symtab.get(csect).get(operand);                            
                            code = (code << 20) | (disp & 0xFFFFF);
                        } else {
                            code = (code << 12) | (disp & 0xFFF);
                        }
                    }
                    
                    objcode = String.format(l.inst.charAt(0) == '+' ? "%08X" : "%06X", code) ;
                    line.add(new AssemblyLine(label,l.inst,l.operand,pc,false,objcode));
                    break;
            }
            }      else if (inst.compareToIgnoreCase("RESB") == 0) {
                    line.add(new AssemblyLine(label,l.inst,l.operand,pc,false,objcode));
                    }
                else if (inst.compareTo("BYTE") == 0) {
            String s =operand;
            char type = s.charAt(0);
                 if(s!="0")
            s = s.substring(s.indexOf('\'') + 1, s.lastIndexOf('\''));
            
            switch (type) {
                case 'C':
                    for (char ch : s.toCharArray()) {
                        objcode += Integer.toHexString(ch).toUpperCase();
                        
                    }
                line.add(new AssemblyLine(label,inst,operand,pc,true,objcode));
                    break;
                case 'X':
                    objcode = s;
                    line.add(new AssemblyLine(label,inst,operand,pc,true,objcode));
                    break;
            }
        } else if (inst.compareTo("WORD") == 0) {
            int y=Integer.parseInt(operand);
            objcode = Integer.toHexString(y);
            line.add(new AssemblyLine(label,inst,operand,pc,true,objcode));
        } else if (inst.compareTo("BASE") == 0) {
            baseAddress = symtab.get(csect).get(operand);
            line.add(l);
        }  else if (inst.compareTo("NOBASE") == 0) {
            baseAddress = 0;
            line.add(l);
        }else if (inst.compareTo("END") == 0) {           
            line.add(l);
            break;
        }
             else if (inst.compareTo("RESW") == 0 || inst.compareTo("RESW") == 0 ) {           
            line.add(l);
        }
             
            else {
            line.add(new AssemblyLine(l.label,l.inst,l.operand,l.pc,false,""));
            line.add(new AssemblyLine("",".ERROR! unknown instruction "+"'"+inst+"'","",pc,false,""));
            
             }

    }
    }
    /**
     * Format zeros String function is try to add "0" to each string to format it as file format
     */
    String formatZeros(String string , int reqDigits)
    {
    return string;  
    }

}
