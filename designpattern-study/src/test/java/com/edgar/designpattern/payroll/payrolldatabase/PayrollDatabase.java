package com.edgar.designpattern.payroll.payrolldatabase;

import com.edgar.designpattern.payroll.payrolldomain.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayrollDatabase {

	private static final Map<String, Employee> employees = new HashMap<String, Employee>();;
	private static final Map<String, String> unionMembers = new HashMap<String, String>();;

	public static Employee getEmployee(String empId) {
		return employees.get(empId);
	}

	public static void addEmployee(String empId, Employee e) {
		employees.put(empId, e);
	}

	public static void deleteEmployee(String empId) {
		employees.remove(empId);
	}

	public static void addUnionMember(String memberId, Employee employee) {
		unionMembers.put(memberId, employee.getEmpId());
	}

	public static Employee getUnionMember(String memberId) {
		String empId = unionMembers.get(memberId);
		return getEmployee(empId);
	}

	public static void removeUnionMember(String memeberId) {
		unionMembers.remove(memeberId);
	}

	public static List<String> getEmployeeIds() {
		return new ArrayList<String>(employees.keySet());
	}

}
