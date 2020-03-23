package XEassembler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Aya
 */
public class InstValue {
    
    String code ;
    Integer format;

    public InstValue() {
    }

    public InstValue(String code, Integer format) {
        this.code = code;
        this.format = format;
    }

    public String getCode() {
        return code;
    }

    public Integer getFormat() {
        return format;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setFormat(Integer format) {
        this.format = format;
    }
    
    
    
}
