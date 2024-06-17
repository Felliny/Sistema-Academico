package edu.fatec.Avaliacao2_LBD.persistence;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

/*
Interface:
Create,
Update,
Delete,
Find,
and List
 */
@Repository
public interface ICRUD<T>
{
    public String insert (T t)	throws SQLException, ClassNotFoundException;
    public String update (T t)	throws SQLException, ClassNotFoundException;
    public String delete (T t)    throws SQLException, ClassNotFoundException;
    public T find (T t)         throws SQLException, ClassNotFoundException;
    public List<T> list ()    	    throws SQLException, ClassNotFoundException;
}
