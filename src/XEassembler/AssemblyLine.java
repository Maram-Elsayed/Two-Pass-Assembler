package XEassembler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 
 */
public class AssemblyLine {

    protected String operand;
    protected String inst;
    protected int pc;
    protected String objCode;
    protected String label;
    protected boolean TranslateFlag ; //start
    protected String errormsg;
    
    public AssemblyLine() {
    }

    public AssemblyLine(String inst, String operand, int pc) {
        this.pc = pc;
        this.operand = operand;
        this.inst = inst;
    }

    public AssemblyLine(String label, String inst, String operand, int pc, boolean TrnsltedFlg,String objcode) {
        this.operand = operand;
        this.inst = inst;
        this.pc = pc;
        this.label = label;
        this.TranslateFlag = TrnsltedFlg;
        this.objCode = objcode;
        
    }    
  public String geterrormsg(String errormsg) {
        return inst;
    }

    public void seterrormsg(String errormsg) {
        this.inst = inst;
    }
   
    public String getInst() {
        return inst;
    }

    public void setInst(String inst) {
        this.inst = inst;
    }
    

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getObjCode() {
        return objCode;
    }

    public void setObjCode(String objCode) {
        this.objCode = objCode;
    }

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

    public int getPc() {
        return pc;
    }

    public void setTranslateFlag(boolean TranslateFlag) {
        this.TranslateFlag = TranslateFlag;
    }

    public boolean getTrnsltedFlg()
    {
        return TranslateFlag;
    }
    
}
