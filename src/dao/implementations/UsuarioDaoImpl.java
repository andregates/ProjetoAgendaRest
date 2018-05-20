/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.implementations;

import dao.interfaces.IUsuarioDao;
import models.Usuario;


public class UsuarioDaoImpl extends
        GenericDaoImpl<Usuario, Integer> implements IUsuarioDao {

    public UsuarioDaoImpl() {
        super(Usuario.class);
    }
}
