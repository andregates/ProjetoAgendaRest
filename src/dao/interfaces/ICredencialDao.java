package dao.interfaces;

import models.Credencial;

public interface ICredencialDao extends IGenericDao<Credencial, Integer>{
	
	public Credencial validarUsuario(String userName, String pass);
	
	public Credencial buscarPorToken(String token);
	
}