package uo.ri.cws.application.persistence.professionalgroup.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupRecordAssembler;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class ProfessionalGroupGatewayImpl implements ProfessionalGroupGateway {

    @Override
    public void add(ProfessionalGroupRecord t) throws PersistenceException {	
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TPROFESSIONALGROUPS_ADD");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, t.id);
		pst.setString(2, t.name);
		pst.setDouble(3, t.trienniumPayment);
		pst.setDouble(4, t.productivityRate);
		pst.setLong(5, t.version);
		pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
		pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
		pst.setString(8, "ENABLED");

		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(
		"Error inserting professional group into TProfessionalGroups", e);
	}
    }

    @Override
    public void remove(String id) throws PersistenceException {	
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TPROFESSIONALGROUPS_DELETE");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, id);
		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(
		"Error deleting professional group with id: " + id, e);
	}
    }

    @Override
    public void update(ProfessionalGroupRecord t) throws PersistenceException {	
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TPROFESSIONALGROUPS_UPDATE");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setDouble(1, t.trienniumPayment);
		pst.setDouble(2, t.productivityRate);
		pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
		pst.setString(4, t.name);

		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException("Error updating professional group", e);
	}
    }

    @Override
    public Optional<ProfessionalGroupRecord> findById(String id)
	throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TPROFESSIONALGROUPS_FINDBYID");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			return Optional.of(
			    ProfessionalGroupRecordAssembler.fromResultSet(rs));
		    }
		    return Optional.empty();
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(
		"Error finding professional group by Id: " + id, e);
	}
    }

    @Override
    public List<ProfessionalGroupRecord> findAll() throws PersistenceException {
	List<ProfessionalGroupRecord> professionalGroups = new ArrayList<>();

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TPROFESSIONALGROUPS_FINDALL");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			professionalGroups.add(
			    ProfessionalGroupRecordAssembler.fromResultSet(rs));
		    }
		    return professionalGroups;
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException("Error finding professional groups", e);
	}
    }

    @Override
    public Optional<ProfessionalGroupRecord> findByName(String name)
	throws PersistenceException {
	try {
            Connection c = Jdbc.getCurrentConnection();
            String sql = Queries.getSQLSentence("TPROFESSIONALGROUPS_FINDBYNAME");
            try (PreparedStatement pst = c.prepareStatement(sql)) {
                pst.setString(1, name);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(
                            ProfessionalGroupRecordAssembler.fromResultSet(rs));
                    }
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error finding professional group by name", e);
        }
	
    }

}
