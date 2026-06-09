package uo.ri.cws.application.persistence.intervention.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.cws.application.persistence.intervention.InterventionRecordAssembler;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class InterventionGatewayImpl implements InterventionGateway{

    @Override
    public void add(InterventionRecord t) throws PersistenceException {
    }

    @Override
    public void remove(String id) throws PersistenceException {
    }

    @Override
    public void update(InterventionRecord t) throws PersistenceException {
    }

    @Override
    public Optional<InterventionRecord> findById(String id)
	throws PersistenceException {
	return Optional.empty();
    }

    @Override
    public List<InterventionRecord> findAll() throws PersistenceException {
	return null;
    }

    @Override
    public List<InterventionRecord> findByMechanicId(String idMechanic) {
        List<InterventionRecord> result = new ArrayList<>();

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    String sql = Queries.getSQLSentence("TINTERVENTIONS_FINDBYMECHANICID");
	    
            try (PreparedStatement pst = c.prepareStatement(sql)) {
                pst.setString(1, idMechanic);
                
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                	result.add(InterventionRecordAssembler.fromResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error finding interventions by mechanic ID", e);
        }
	
        return result;

    }

 

}
