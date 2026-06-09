package uo.ri.cws.application.persistence.contract.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractRecordAssembler;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class ContractGatewayImpl implements ContractGateway {

    @Override
    public void add(ContractRecord t) throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TCONTRACTS_ADD");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, t.id);
		pst.setString(2, t.mechanicId);
		pst.setString(3, t.professionalGroupId);
		pst.setString(4, t.contractTypeId);
		pst.setDouble(5, t.annualBaseSalary);
		pst.setDouble(6, t.taxRate);
		pst.setDouble(7, t.settlement);
		pst.setString(8, t.state);
		pst.setDate(9, Date.valueOf(t.startDate));
		
		if (t.endDate != null)
		    pst.setDate(10, Date.valueOf(t.endDate));
		else
		    pst.setNull(10, Types.DATE);		
		
		pst.setLong(11, t.version);

		pst.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
		pst.setTimestamp(13, new Timestamp(System.currentTimeMillis()));
		pst.setString(14, "ENABLED");

		pst.executeUpdate();

	    }
	} catch (SQLException e) {
	    throw new PersistenceException(
		"Error inserting contract into TContracts", e);
	}
    }

    @Override
    public void remove(String id) throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TCONTRACTS_DELETE");

            try (PreparedStatement pst = c.prepareStatement(sql)) {
                pst.setString(1, id);
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error deleting contract with id: " + id, e);
        }
    }

    @Override
    public void update(ContractRecord t) throws PersistenceException {
        try {
            Connection c = Jdbc.getCurrentConnection();
            String sql = Queries.getSQLSentence("TCONTRACTS_UPDATE");
            try (PreparedStatement pst = c.prepareStatement(sql)) {
        	pst.setDouble(1, t.annualBaseSalary);
                pst.setDate(2, t.endDate != null ? Date.valueOf(t.endDate) : null);
                pst.setDouble(3, t.taxRate);
                pst.setLong(4, t.version);
                pst.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                pst.setDouble(6, t.settlement);
                pst.setString(7, t.state);
                pst.setString(8, t.id);

                pst.executeUpdate();
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error updating contract " + t.id, e);
        }
    }


    @Override
    public Optional<ContractRecord> findById(String id)
	throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TCONTRACTS_FINDBYID");
	    
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			return Optional.of(
			    ContractRecordAssembler.fromResultSet(rs));
		    }
		    return Optional.empty();
		}
	    }
	}
	catch (SQLException e) {
	    throw new PersistenceException(
		"Error finding contract by id: " + id, e);
	}
    }

    @Override
    public List<ContractRecord> findAll() throws PersistenceException {
	List<ContractRecord> contracts = new ArrayList<>();;

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TCONTRACTS_FINDALL");
	    
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			contracts.add(ContractRecordAssembler.fromResultSet(rs));
		    }
		    return contracts;
		}
	    }
	}
	catch (SQLException e) {
	    throw new PersistenceException(
		"Error finding contracts", e);
	}
    }

    @Override
    public Optional<ContractRecord> findInForceByMechanic(String id) {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TCONTRACTS_FIND_IN_FORCE_BY_MECHANIC");
	    
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			return Optional.of(
			    ContractRecordAssembler.fromResultSet(rs));
		    }
		    return Optional.empty();
		}
	    }
	}
	catch (SQLException e) {
	    throw new PersistenceException(
		"Error finding contract by mechanicId: " + id, e);
	}
    }

    @Override
    public List<ContractRecord> findInForce() {
	List<ContractRecord> inForceContracts = new ArrayList<>();;
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TCONTRACTS_FIND_IN_FORCE");
	    
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			inForceContracts.add(ContractRecordAssembler.fromResultSet(rs));
		    }
		    
		}
	    }
	    return inForceContracts;
	}
	catch (SQLException e) {
	    throw new PersistenceException(
		"Error finding in force contracts", e);
	}
    }

    @Override
    public List<ContractRecord> findByMechanicId(String idMechanic) {
	List<ContractRecord> result = new ArrayList<>();

	try {
            Connection c = Jdbc.getCurrentConnection();
            String sql = Queries.getSQLSentence("TCONTRACTS_FINDBYMECHANICID");

            try (PreparedStatement pst = c.prepareStatement(sql)) {
                pst.setString(1, idMechanic);

                try (ResultSet rs = pst.executeQuery()) {                    
                    while (rs.next()) {
                	result.add(ContractRecordAssembler.fromResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error finding contracts by mechanic ID", e);
        }
	
        return result;
    }

    @Override
    public boolean existsByContractTypeId(String contractTypeId) {
        try {
            Connection c = Jdbc.getCurrentConnection();
            String sql = Queries.getSQLSentence("TCONTRACTS_COUNT_BY_TYPE");

            try (PreparedStatement pst = c.prepareStatement(sql)) {
                pst.setString(1, contractTypeId);
                try (ResultSet rs = pst.executeQuery()) {
                    rs.next();
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error checking contracts by contract type", e);
        }
    }



}
