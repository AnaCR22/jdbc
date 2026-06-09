package uo.ri.cws.application.persistence.workorder;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public class WorkOrderRecordAssembler {
    public static WorkOrderRecord fromResultSet(ResultSet rs) throws SQLException {
	WorkOrderRecord r = new WorkOrderRecord();
        r.id = rs.getString("id");
        r.description = rs.getString("description");
        r.date = rs.getTimestamp("date").toLocalDateTime();
        r.state = rs.getString("state");
        r.amount = rs.getDouble("amount");
        return r;
    }
}
