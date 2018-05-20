package dao.interfaces;

import java.util.List;

import models.Contato;

public interface IContatoDao extends IGenericDao<Contato, Integer>{
	public List<Contato> findAllByContatosPorUsuario(Integer idUser, String orderBy);
}
