package dao.implementations;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import dao.interfaces.IContatoDao;
import models.Contato;
import util.HibernateUtil;


public class ContatoDaoImpl extends
        GenericDaoImpl<Contato, Integer> implements IContatoDao {

    public ContatoDaoImpl() {
        super(Contato.class);
    }
    
    @SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	@Override
	public List<Contato> findAllByContatosPorUsuario(Integer idUser, String orderBy) {
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	try {	  
    		  
	    	  
    		  Query query = session.getSession().createQuery(
  					"SELECT u.id, u.nome, c.id, c.nome, c.telefone, c.email FROM Contato c "
  					+ " INNER JOIN c.usuario as u "
  					+ " WHERE u.id = " + idUser + " ORDER BY " + "c." + orderBy);
  			
    		  		List<Contato> contacts = query.list();
    		  
    		  		return contacts;
	    
	} catch (RuntimeException erro) {
		throw erro;
	} finally {
		 session.close();
	}
    	
    }
    
}

