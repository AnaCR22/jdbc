package uo.ri.cws.application.persistence.professionalgroup;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupRecord;

public class ProfessionalGroupRecordAssembler {

    public static ProfessionalGroupRecord fromResultSet(ResultSet rs) throws SQLException {
	ProfessionalGroupRecord r = new ProfessionalGroupRecord();
        r.id = rs.getString("id");
        r.name = rs.getString("name");
        r.trienniumPayment = rs.getDouble("trienniumPayment");
        r.productivityRate = rs.getDouble("productivityRate");
        r.version = rs.getLong("version");
        return r;
    }
}
