package dao.implementations;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtil;

import dao.interfaces.ICredencialDao;
import models.Credencial;


public class CredencialDaoImpl extends
        GenericDaoImpl<Credencial, Integer> implements ICredencialDao {
	
	private Session session;
	
    public CredencialDaoImpl() {
        super(Credencial.class);
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public Credencial validarUsuario(String username, String pwd) {
	
    	
		try {	   
		    	  Transaction transaction = session.beginTransaction();
		    	  Criteria crit = session.createCriteria(Credencial.class);
		    	  crit.add(Restrictions.eq("username",username));
		    	  crit.add(Restrictions.eq("pwd",pwd));
		    	  transaction.commit();
		    	  return (Credencial) crit.uniqueResult();
		    
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			session.close();
		}
	
	}

	@Override
	public Credencial buscarPorToken(String token) {
		// TODO Auto-generated method stub
		
		try {	   
	    	  Transaction transaction = session.beginTransaction();
	    	  Criteria crit = session.createCriteria(Credencial.class);
	    	  crit.add(Restrictions.eq("token",token));
	    	  transaction.commit();
	    	  return (Credencial) crit.uniqueResult();
	    
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			session.close();
		}
	}

}