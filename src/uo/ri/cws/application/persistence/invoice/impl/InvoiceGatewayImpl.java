package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class InvoiceGatewayImpl implements InvoiceGateway{

    @Override
    public void add(InvoiceRecord t) throws PersistenceException {
	try {
    	Connection connection = Jdbc.getCurrentConnection();
    	String sql = Queries.getSQLSentence("TINVOICES_INSERT");
    
            try (PreparedStatement pst = connection.prepareStatement(sql)) {
                pst.setString(1, t.id);
                pst.setLong(2, t.number);
                pst.setDate(3, java.sql.Date.valueOf(t.date));
                pst.setDouble(4, t.vat);
                pst.setDouble(5, t.amount);
                pst.setString(6, "NOT_YET_PAID");
                pst.setLong(7, 1L);
                pst.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
		pst.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
		pst.setString(10, "ENABLED");
                pst.executeUpdate();
            }
        }catch (SQLException e) {
	    throw new PersistenceException(
		"Error inserting invoice", e);
        }
    }
    
    @Override
    public Optional<Long> findLastInvoiceNumber() throws PersistenceException {
	try {
            Connection c = Jdbc.getCurrentConnection();
            try (PreparedStatement pst = c.prepareStatement(
                    Queries.getSQLSentence("TINVOICES_SELECT_LAST_NUMBER"))) {
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        return Optional.ofNullable(rs.getLong(1));
                    }
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error retrieving last invoice number", e);
        }
    }


    @Override
    public void remove(String id) throws PersistenceException {
	
    }

    @Override
    public void update(InvoiceRecord t) throws PersistenceException {
	
    }

    @Override
    public Optional<InvoiceRecord> findById(String id)
	throws PersistenceException {
	return Optional.empty();
    }

    @Override
    public List<InvoiceRecord> findAll() throws PersistenceException {
	return null;
    }

}
