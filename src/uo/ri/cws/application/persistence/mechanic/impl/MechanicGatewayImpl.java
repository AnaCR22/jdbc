package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicRecordAssembler;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class MechanicGatewayImpl implements MechanicGateway {

    @Override
    public void add(MechanicRecord t) throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TMECHANICS_ADD");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, t.id);
		pst.setString(2, t.nif);
		pst.setString(3, t.name);
		pst.setString(4, t.surname);
		pst.setLong(5, t.version);
		pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
		pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
		pst.setString(8, "ENABLED");

		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(
		"Error inserting mechanic into TMechanics", e);
	}
    }

    @Override
    public void remove(String id) throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TMECHANICS_DELETE");

            try (PreparedStatement pst = c.prepareStatement(sql)) {
                pst.setString(1, id);
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error deleting mechanic with id: " + id, e);
        }
    }

    @Override
    public void update(MechanicRecord t) throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TMECHANICS_UPDATE");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, t.name);
		pst.setString(2, t.surname);
		pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
		pst.setString(4, t.id);

		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException("Error updating mechanic " + t.id,
		e);
	}
    }

    @Override
    public List<MechanicRecord> findAll() throws PersistenceException {
        List<MechanicRecord> mechanics = new ArrayList<>();

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TMECHANICS_FINDALL");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		try (ResultSet rs = pst.executeQuery();) {
		    while (rs.next()) {
			mechanics.add(MechanicRecordAssembler.fromResultSet(rs));
		    }
		}
	    }
	    return mechanics;
	} catch (SQLException e) {
	    throw new PersistenceException("Error loading all mechanics", e);
	}
    }

    @Override
    public Optional<MechanicRecord> findById(String id)
	throws PersistenceException {

	try {
	    // get always the current connection and don't close the connection
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TMECHANICS_FINDBYID");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			return Optional.of(
			    MechanicRecordAssembler.fromResultSet(rs));
		    }
		    return Optional.empty();
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(
		"Error finding mechanic by ID: " + id, e);

	}
    }

    @Override
    public Optional<MechanicRecord> findByNif(String nif)
	throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TMECHANICS_FINDBYNIF");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, nif);
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			return Optional.of(
			    MechanicRecordAssembler.fromResultSet(rs));
		    }
		    return Optional.empty();
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(
		"Error finding mechanic by NIF: " + nif, e);
	}
    }

}
