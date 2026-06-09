package uo.ri.cws.application.persistence.payroll;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollRecord;

public class PayrollRecordAssembler {

    public static PayrollRecord fromResultSet(ResultSet rs)
	throws SQLException {
	PayrollRecord r = new PayrollRecord();
	r.id = rs.getString("id");
	r.contractId = rs.getString("contract_id");
	r.date = rs.getDate("date").toLocalDate();

	r.baseSalary = rs.getDouble("baseSalary");
	r.extraSalary = rs.getDouble("extraSalary");
	r.productivityEarning = rs.getDouble("productivityEarning");
	r.trienniumEarning = rs.getDouble("trienniumEarning");

	r.taxDeduction = rs.getDouble("taxDeduction");
	r.nicDeduction = rs.getDouble("nicDeduction");

	r.version = rs.getLong("version");
	return r;
    }
}
