package uo.ri.cws.application.persistence.intervention;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public class InterventionRecordAssembler {

    public static InterventionRecord fromResultSet(ResultSet rs) throws SQLException {
	InterventionRecord r = new InterventionRecord();
        r.id = rs.getString("id");
        r.mechanicId = rs.getString("mechanic_id");
        r.workOrderId = rs.getString("workorder_id");
        r.minutes = rs.getInt("minutes");
        r.version = rs.getLong("version");
        r.date = rs.getTimestamp("date").toLocalDateTime();
        return r;
    }

}
