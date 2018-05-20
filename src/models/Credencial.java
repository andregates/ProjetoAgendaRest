package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Parameter;

@SuppressWarnings("serial")
@Entity
@Table(name = "credenciais")
public class Credencial implements Serializable {

	private static final long serialVersionUID = 2358133578510385668L;
	
	@Id
	@GeneratedValue(generator = "fk_credencial_usuario_id") 
	@org.hibernate.annotations.GenericGenerator(name = "fk_credencial_usuario_id", 
	strategy = "foreign", parameters = @Parameter(name = "property", value = "usuario"))
	private int id;
	
	@OneToOne
	private Usuario usuario;
	
	@Column
	private String username;
	
	@Column
	private String pwd;
	
	@Column
	private String token;
	
	public Credencial(){}
	
	
	public Credencial(Usuario usuario, String username, String pwd, String token) {
		super();
		this.username = username;
		this.pwd = pwd;
		this.token = token;
		this.usuario=usuario;
		
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credencial other = (Credencial) obj;
		if (id != other.id)
			return false;
		return true;
	}
		
}
