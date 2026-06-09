package uo.ri.cws.application.persistence.contracttype;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeRecord;

public class ContractTypeRecordAssembler {

    public static ContractTypeRecord fromResultSet(ResultSet rs) throws SQLException {
	ContractTypeRecord r = new ContractTypeRecord();
        r.id = rs.getString("id");
        r.name = rs.getString("name");
        r.compensationDaysPerYear = rs.getDouble("compensationDaysPerYear");
        r.version = rs.getLong("version");
        return r;
    }
}
