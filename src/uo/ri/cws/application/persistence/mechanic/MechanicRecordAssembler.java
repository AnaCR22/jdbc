package uo.ri.cws.application.persistence.mechanic;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public class MechanicRecordAssembler {

    public static MechanicRecord fromResultSet(ResultSet rs) throws SQLException {
        MechanicRecord r = new MechanicRecord();
        r.id = rs.getString("id");
        r.nif = rs.getString("nif");
        r.name = rs.getString("name");
        r.surname = rs.getString("surname");
        r.version = rs.getLong("version");
        return r;
    }
}
