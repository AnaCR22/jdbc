package uo.ri.cws.application.persistence.professionalgroup.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class ProfessionalGroupGatewayImpl implements ProfessionalGroupGateway {

    @Override
    public void add(ProfessionalGroupRecord t) throws PersistenceException {	
    }

    @Override
    public void remove(String id) throws PersistenceException {	
    }

    @Override
    public void update(ProfessionalGroupRecord t) throws PersistenceException {	
    }

    @Override
    public Optional<ProfessionalGroupRecord> findById(String id)
	throws PersistenceException {
	return Optional.empty();
    }

    @Override
    public List<ProfessionalGroupRecord> findAll() throws PersistenceException {
	return null;
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
                	ProfessionalGroupRecord rec = new ProfessionalGroupRecord();
                        rec.id = rs.getString("id");
                        rec.name = rs.getString("name");
                        rec.trienniumPayment = rs.getDouble("trienniumPayment");
                        rec.productivityRate = rs.getDouble("productivityRate");
                        rec.version = rs.getLong("version");
                        return Optional.of(rec);
                    }
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error finding professional group by name", e);
        }
	
    }

}
