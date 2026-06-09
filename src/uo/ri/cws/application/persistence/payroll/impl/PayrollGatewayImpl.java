package uo.ri.cws.application.persistence.payroll.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollRecordAssembler;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class PayrollGatewayImpl implements PayrollGateway {

    @Override
    public void add(PayrollRecord t) throws PersistenceException {

    }

    @Override
    public void remove(String id) throws PersistenceException {

    }

    @Override
    public void update(PayrollRecord t) throws PersistenceException {

    }

    @Override
    public Optional<PayrollRecord> findById(String id)
	throws PersistenceException {
	return Optional.empty();
    }

    @Override
    public List<PayrollRecord> findAll() throws PersistenceException {
	return null;
    }


    @Override
    public int countByContractId(String id) {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TPAYROLLS_COUNTBYCONTRACT");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, id);

		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			return rs.getInt(1);
		    }
		    return 0;
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(
		"Error finding payrolls by mechanic and period", e);
	}
    }

    @Override
    public List<PayrollRecord> findLast12ByContractId(String contractId) {
        List<PayrollRecord> result = new ArrayList<>();
        try {
            Connection c = Jdbc.getCurrentConnection();
            String sql = Queries.getSQLSentence("TPAYROLLS_FINDBYCONTRACTID_LAST12");

            try (PreparedStatement pst = c.prepareStatement(sql)) {
                pst.setString(1, contractId);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        result.add(PayrollRecordAssembler.fromResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error finding last 12 payrolls by contractId", e);
        }
        return result;
    }


}
