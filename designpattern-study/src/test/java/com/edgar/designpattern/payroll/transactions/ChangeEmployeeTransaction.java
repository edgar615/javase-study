package com.edgar.designpattern.payroll.transactions;

import com.edgar.designpattern.payroll.payrolldatabase.PayrollDatabase;
import com.edgar.designpattern.payroll.payrolldomain.Employee;
import com.edgar.designpattern.payroll.payrolldomain.Transaction;

public abstract class ChangeEmployeeTransaction implements Transaction {

	private String empId;

    public ChangeEmployeeTransaction(String empId) {
        this.empId = empId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    @Override
	public void execute() {
		Employee e = PayrollDatabase.getEmployee(empId);
		change(e);
	}

	public abstract void change(Employee e);

}