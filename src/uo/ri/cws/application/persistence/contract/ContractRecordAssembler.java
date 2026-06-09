package uo.ri.cws.application.persistence.contract;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.contract.ContractGateway.ContractRecord;

public class ContractRecordAssembler {

    public static ContractRecord fromResultSet(ResultSet rs) throws SQLException {
	ContractRecord r = new ContractRecord();
        r.id = rs.getString("id");
        r.mechanicId = rs.getString("mechanic_id");
        r.contractTypeId = rs.getString("contracttype_id");
        r.professionalGroupId = rs.getString("professionalgroup_id");
        r.startDate = rs.getDate("startDate").toLocalDate();
        //Check if it is null. If not convert it and store it
        r.endDate = rs.getDate("endDate") != null ? rs.getDate("endDate").toLocalDate() : null;
        r.annualBaseSalary = rs.getDouble("annualBaseSalary");
        r.taxRate = rs.getDouble("taxRate");
        r.settlement = rs.getDouble("settlement");
        r.state =rs.getString("state");
        r.version = rs.getLong("version");
        return r;
    }

}
