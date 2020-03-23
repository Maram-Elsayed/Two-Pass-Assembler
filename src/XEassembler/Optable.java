package XEassembler;



import java.util.Hashtable;

public class Optable {
	
	 Hashtable< String , InstValue > opTable ;
	
	public Optable(){
		opTable = new Hashtable< String , InstValue>();
		
		opTable.put( "ADD" ,  new InstValue("18" , 3) );
		opTable.put( "ADDF" ,  new InstValue("58" , 3));
                opTable.put( "ADDR" ,  new InstValue("90" , 2));
		opTable.put( "AND" , new InstValue("40" , 3));
                opTable.put( "CLEAR" ,  new InstValue("B4" , 2));
		opTable.put( "COMP" , new InstValue("28" , 3));
                opTable.put( "COMPR" , new InstValue("A0" , 2));
		opTable.put( "DIV" , new InstValue("24" , 3));
                opTable.put( "DIVR" , new InstValue("9C" , 2));
                opTable.put( "FIX" , new InstValue("C4" ,1));
                opTable.put( "FLOAT" , new InstValue("C0" ,1));
                opTable.put( "HIO" , new InstValue("F4" ,1));
		opTable.put(  "J" , new InstValue("3C" , 3));
		opTable.put( "JEQ" ,new InstValue ("30" , 3));
		opTable.put( "JGT" , new InstValue("34" , 3));
		opTable.put( "JLT" , new InstValue("38" , 3));
		opTable.put( "JSUB" ,new InstValue ("48" , 3));
		opTable.put( "LDA" ,new InstValue ("00" , 3));
		opTable.put( "LDB" , new InstValue("68" , 3));
		opTable.put( "LDCH" , new InstValue("50" , 3));
		opTable.put( "LDF" ,new InstValue("70" , 3));
		opTable.put( "LDL" , new InstValue("08" , 3));
		opTable.put( "LDS" , new InstValue("6C" , 3));
		opTable.put( "LDT" , new InstValue("74" , 3));
		opTable.put( "LDX" ,new InstValue ("04" , 3));
		opTable.put( "LPS" , new InstValue("D0" , 3));
		opTable.put( "MUL" , new InstValue("20" , 3));
                opTable.put( "MULR" , new InstValue("98" , 2));
                opTable.put( "NORM" , new InstValue("C8" ,1));
		opTable.put( "MULF" , new InstValue("60" , 3));
		opTable.put( "OR" , new InstValue("44" , 3));
		opTable.put( "RD" , new InstValue("D8" , 3));
                opTable.put( "RMO" , new InstValue("AC" , 2));
		opTable.put( "RSUB" , new InstValue("4C" , 3));
                opTable.put( "SHIFTL" , new InstValue("A4" , 2));
                opTable.put( "SHIFTR" , new InstValue("A8" , 2));
                opTable.put( "SIO" , new InstValue("F0" , 1));
		opTable.put( "SSK" , new InstValue("EC" , 3));
		opTable.put( "STA" , new InstValue("0C" , 3));
		opTable.put( "STB" , new InstValue("78" , 3));
		opTable.put( "STCH" , new InstValue("54" ,3));
		opTable.put( "STF" , new InstValue("80" , 3));
		opTable.put( "STI" , new InstValue("D4" , 3));
		opTable.put( "STL" , new InstValue("14" , 3));
		opTable.put( "STS" , new InstValue("7C" , 3));
		opTable.put( "STSW" , new InstValue("E8" , 3));
		opTable.put( "STT" , new InstValue("84" , 3));
		opTable.put( "STX" ,new InstValue ("10" , 3));
		opTable.put( "SUB" , new InstValue("1C" , 3));
		opTable.put( "SUBF" , new InstValue("5C" , 3));
                opTable.put( "SUBR" , new InstValue("94" , 2));
                opTable.put( "SVC" , new InstValue("B0" , 2));
		opTable.put( "TD" , new InstValue("E0" , 3));
                opTable.put( "TIO" , new InstValue("F8" , 1));
		opTable.put( "TIX" , new InstValue("2C" , 3));
                opTable.put( "TIXR" , new InstValue("B8" , 2));
		opTable.put( "WD" , new InstValue("DC" , 3));
               
	}
	
}
