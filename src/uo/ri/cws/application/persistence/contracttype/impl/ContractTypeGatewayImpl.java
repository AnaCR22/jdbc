package uo.ri.cws.application.persistence.contracttype.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeRecordAssembler;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class ContractTypeGatewayImpl implements ContractTypeGateway {

    @Override
    public void add(ContractTypeRecord t) throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TCONTRACTTYPES_ADD");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, t.id);
		pst.setString(2, t.name);
		pst.setDouble(3, t.compensationDaysPerYear);
		pst.setLong(4, t.version);
		pst.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
		pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
		pst.setString(7, "ENABLED");

		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(
		"Error inserting contract type into TContractTypes", e);
	}
    }

    @Override
    public void remove(String id) throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TCONTRACTTYPES_DELETE");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, id);
		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(
		"Error deleting contract type with id: " + id, e);
	}
    }

    @Override
    public void update(ContractTypeRecord t) throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TCONTRACTTYPES_UPDATE");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setDouble(1, t.compensationDaysPerYear);
		pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
		pst.setString(3, t.name);

		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException("Error updating contract type", e);
	}
    }

    @Override
    public Optional<ContractTypeRecord> findById(String id)
	throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TCONTRACTTYPES_FINDBYID");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			return Optional.of(
			    ContractTypeRecordAssembler.fromResultSet(rs));
		    }
		    return Optional.empty();
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(
		"Error finding contract type by Id: " + id, e);
	}
    }

    @Override
    public List<ContractTypeRecord> findAll() throws PersistenceException {
	List<ContractTypeRecord> contractTypes = new ArrayList<>();
	;

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TCONTRACTTYPES_FINDALL");

	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			contractTypes.add(
			    ContractTypeRecordAssembler.fromResultSet(rs));
		    }
		    return contractTypes;
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException("Error finding contracts", e);
	}
    }

    @Override
    public Optional<ContractTypeRecord> findByName(String name)
	throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TCONTRACTTYPES_FINDBYNAME");
	    try (PreparedStatement pst = c.prepareStatement(sql)) {
		pst.setString(1, name);
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			return Optional.of(
			    ContractTypeRecordAssembler.fromResultSet(rs));
		    }
		    return Optional.empty();
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(
		"Error finding contract type by name", e);
	}
    }
}
