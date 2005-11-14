package examples.dao;

import java.io.Serializable;
import java.sql.Timestamp;

public class Employee implements Serializable {

	public static final String TABLE = "EMP";

	public static final int department_RELNO = 0;

	public static final String timestamp_COLUMN = "tstamp";

	private long empno;

	private String ename;

	private String job;

	private Short mgr;

	private java.util.Date hiredate;

	private Float sal;

	private Float comm;

	private int deptno;

	private Timestamp timestamp;

	private Department department;

	public Employee() {
	}

	public Employee(long empno) {
		this.empno = empno;
	}

	public long getEmpno() {
		return this.empno;
	}

	public void setEmpno(long empno) {
		this.empno = empno;
	}

	public java.lang.String getEname() {
		return this.ename;
	}

	public void setEname(java.lang.String ename) {
		this.ename = ename;
	}

	public java.lang.String getJob() {
		return this.job;
	}

	public void setJob(java.lang.String job) {
		this.job = job;
	}

	public Short getMgr() {
		return this.mgr;
	}

	public void setMgr(Short mgr) {
		this.mgr = mgr;
	}

	public java.util.Date getHiredate() {
		return this.hiredate;
	}

	public void setHiredate(java.util.Date hiredate) {
		this.hiredate = hiredate;
	}

	public Float getSal() {
		return this.sal;
	}

	public void setSal(Float sal) {
		this.sal = sal;
	}

	public Float getComm() {
		return this.comm;
	}

	public void setComm(Float comm) {
		this.comm = comm;
	}

	public int getDeptno() {
		return this.deptno;
	}

	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public boolean equals(Object other) {
		if (!(other instanceof Employee))
			return false;
		Employee castOther = (Employee) other;
		return this.getEmpno() == castOther.getEmpno();
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(empno).append(", ");
		buf.append(ename).append(", ");
		buf.append(job).append(", ");
		buf.append(mgr).append(", ");
		buf.append(hiredate).append(", ");
		buf.append(sal).append(", ");
		buf.append(comm).append(", ");
		buf.append(deptno).append(", ");
		buf.append(timestamp).append(" {");
		buf.append(department).append("}");
		return buf.toString();
	}

	public int hashCode() {
		return (int) this.getEmpno();
	}
}