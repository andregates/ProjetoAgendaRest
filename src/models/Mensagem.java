/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author André Gomes
 */
@XmlRootElement(name = "mensagem")
public class Mensagem {
	
    private long id;
    private String conteudo;

    public Mensagem(long id, String conteudo) {
        this.id = id;
        this.conteudo = conteudo;
    }
    
    public Mensagem(){}
    
    @XmlElement
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    @XmlElement
    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
    
    
}
