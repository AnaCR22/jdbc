package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecordAssembler;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

    @Override
    public void add(WorkOrderRecord t) throws PersistenceException {

    }

    @Override
    public void remove(String id) throws PersistenceException {

    }

    @Override
    public void update(WorkOrderRecord t) throws PersistenceException {

    }

    @Override
    public List<WorkOrderRecord> findAll() throws PersistenceException {
	return null;
    }

    @Override
    public Optional<WorkOrderRecord> findById(String id)
	throws PersistenceException {
	return Optional.empty();
    }

    @Override
    public boolean existAll(List<String> ids) throws PersistenceException {
	String sql = Queries.getSQLSentence("TWORKORDERS_FIND_ID_BY_ID");

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    for (String id : ids) {
		try (PreparedStatement pst = c.prepareStatement(sql)) {
		    pst.setString(1, id);
		    try (ResultSet rs = pst.executeQuery()) {
			if (!rs.next())
			    return false;
		    }
		}
	    }
	    return true;

	} catch (SQLException e) {
	    throw new PersistenceException("Error checking workorder existence",
		e);
	}
    }

    @Override
    public boolean areAllFinished(List<String> ids)
	throws PersistenceException {
	String sql = Queries.getSQLSentence("TWORKORDERS_FIND_STATUS_BY_ID");

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    for (String id : ids) {
		try (PreparedStatement pst = c.prepareStatement(sql)) {
		    pst.setString(1, id);
		    try (ResultSet rs = pst.executeQuery()) {
			if (rs.next()) {
			    String state = rs.getString("state");
			    if (!"FINISHED".equalsIgnoreCase(state))
				return false;
			}
		    }
		}
	    }
	    return true;

	} catch (SQLException e) {
	    throw new PersistenceException("Error checking workorder states",
		e);
	}
    }

    @Override
    public double findAmountById(String id) throws PersistenceException {
	String sql = Queries.getSQLSentence("TWORKORDERS_FIND_AMOUNT_BY_ID");

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next())
			return rs.getDouble("amount");
		    return 0.0;
		}
	    }

	} catch (SQLException e) {
	    throw new PersistenceException(
		"Error finding amount by workorder id", e);
	}
    }

    @Override
    public void linkToInvoice(String invoiceId, String workOrderId)
	throws PersistenceException {
	String sql = Queries.getSQLSentence("TWORKORDERS_UPDATE_INVOICEID");

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, invoiceId);
		pst.setString(2, workOrderId);
		pst.executeUpdate();
	    }

	} catch (SQLException e) {
	    throw new PersistenceException("Error linking workorder to invoice",
		e);
	}
    }

    @Override
    public void markAsInvoiced(String workOrderId) throws PersistenceException {
	String sql = Queries.getSQLSentence("TWORKORDERS_UPDATE_STATE");

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, workOrderId);
		pst.executeUpdate();
	    }

	} catch (SQLException e) {
	    throw new PersistenceException(
		"Error marking workorder as invoiced", e);
	}
    }

    @Override
    public void updateVersionAndTime(String workOrderId)
	throws PersistenceException {
	String sql = Queries.getSQLSentence("TWORKORDERS_UPDATE_TIMESTAMP");
	String sql2 = Queries.getSQLSentence("TWORKORDERS_UPDATE_VERSION");

	try {
	    Connection c = Jdbc.getCurrentConnection();

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
		pst.setString(2, workOrderId);
		pst.executeUpdate();
	    }

	    try (PreparedStatement pst = c.prepareStatement(sql2)) {
		pst.setString(1, workOrderId);
		pst.executeUpdate();
	    }

	} catch (SQLException e) {
	    throw new PersistenceException("Error updating version/time", e);
	}
    }

    @Override
    public List<WorkOrderRecord> findNotInvoicedByClientNif(String nif) {
	List<WorkOrderRecord> result = new ArrayList<>();

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence(
		"TWORKORDERS_FIND_NOT_INVOICED");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, nif);
		try (ResultSet rs = pst.executeQuery();) {
		    while (rs.next()) {
			result.add(WorkOrderRecordAssembler.fromResultSet(rs));
		    }
		}
	    }

	    return result;
	} catch (SQLException e) {
	    throw new PersistenceException(
		"Error retrieving not invoiced work orders", e);
	}
    }

    @Override
    public List<WorkOrderRecord> findByMechanicId(String idMechanic) {
	List<WorkOrderRecord> result = new ArrayList<>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TWORKORDERS_FINDBYMECHANICID");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, idMechanic);

		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			result.add(WorkOrderRecordAssembler.fromResultSet(rs));
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(
		"Error finding workorders by mechanic ID", e);
	}

	return result;
    }

}
