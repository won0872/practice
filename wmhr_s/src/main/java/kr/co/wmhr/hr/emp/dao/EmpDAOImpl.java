package kr.co.wmhr.hr.emp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;
import kr.co.wmhr.hr.emp.to.EmpTO;

public class EmpDAOImpl implements EmpDAO {
	private DataSourceTransactionManager dataSourceTransactionManager;

	
	
	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	public EmpTO selectEmp(String name) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("select * from emp where emp_name=?");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			EmpTO emp = null;
			if (rs.next()) {
				emp = new EmpTO();
				emp.setEmpCode(rs.getString("emp_code"));
				emp.setEmpName(rs.getString("emp_name"));
				emp.setDeptName(rs.getString("dept_name"));
				emp.setPosition(rs.getString("position"));
				emp.setGender(rs.getString("gender"));
				emp.setMobileNumber(rs.getString("mobile_number"));
			}

			return emp;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	public ArrayList<EmpTO> selectEmpList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("select * from emp");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			rs = pstmt.executeQuery();
			ArrayList<EmpTO> list = new ArrayList<EmpTO>();
			while (rs.next()) {
				EmpTO emp = new EmpTO();
				emp.setEmpName(rs.getString("emp_name"));
				emp.setDeptName(rs.getString("dept_name"));
				emp.setPosition(rs.getString("position"));
				emp.setGender(rs.getString("gender"));
				emp.setMobileNumber(rs.getString("mobile_number"));
				emp.setEmpCode(rs.getString("emp_code"));
				emp.setAddress(rs.getString("address"));
				emp.setDetailAddress(rs.getString("detail_address"));
				emp.setBirthdate(rs.getString("birthdate"));
				emp.setPostNumber(rs.getString("post_number"));
				emp.setImgExtend(rs.getString("img_extend"));
				emp.setLastSchool(rs.getString("last_school"));
				emp.setEmail(rs.getString("email"));
				list.add(emp);
			}

			return list;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	public ArrayList<EmpTO> selectEmpListD(String dept) {
		ArrayList<EmpTO> list = new ArrayList<EmpTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append("select emp_name,dept_name,position,gender,mobile_number,emp_code, ");
			query.append("address, detail_address, to_CHAR(birthdate) birthdate, post_number, ");
			query.append("img_extend,last_school,email ");
			query.append("from emp where dept_name=?");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, dept);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				EmpTO emp = new EmpTO();
				emp.setEmpName(rs.getString("emp_name"));
				emp.setDeptName(rs.getString("dept_name"));
				emp.setPosition(rs.getString("position"));
				emp.setGender(rs.getString("gender"));
				emp.setMobileNumber(rs.getString("mobile_number"));
				emp.setEmpCode(rs.getString("emp_code"));
				emp.setAddress(rs.getString("address"));
				emp.setDetailAddress(rs.getString("detail_address"));
				emp.setBirthdate(rs.getString("birthdate"));
				emp.setPostNumber(rs.getString("post_number"));
				emp.setImgExtend(rs.getString("img_extend"));
				emp.setLastSchool(rs.getString("last_school"));
				emp.setEmail(rs.getString("email"));
				list.add(emp);
			}

			return list;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public void registEmployee(EmpTO empto) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("insert into emp values(?,?,TO_DATE(?,'YYYY/MM/DD'),?,?,?,?,?,?,?,?,?,?)");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());

			pstmt.setString(1, empto.getEmpCode());
			pstmt.setString(2, empto.getEmpName());
			pstmt.setString(3, empto.getBirthdate());
			pstmt.setString(4, empto.getGender());
			pstmt.setString(5, empto.getMobileNumber());
			pstmt.setString(6, empto.getAddress());
			pstmt.setString(7, empto.getDetailAddress());
			pstmt.setString(8, empto.getPostNumber());
			pstmt.setString(9, empto.getEmail());
			pstmt.setString(10, empto.getLastSchool());
			pstmt.setString(11, empto.getImgExtend());
			pstmt.setString(12, empto.getPosition());
			pstmt.setString(13, empto.getDeptName());

			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}

	@Override
	public String selectLastEmpCode() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			query.append("select emp_code from emp order by emp_code desc");

			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();
			rs.next();

			return rs.getString("emp_code");
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public ArrayList<EmpTO> selectEmpListN(String name) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<EmpTO> list = new ArrayList<EmpTO>();
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("select emp_name,dept_name,position,gender,mobile_number,emp_code, ");
			insertQuery.append("address, detail_address, to_CHAR(birthdate), post_number, ");
			insertQuery.append("img_extend,last_school,email ");
			insertQuery.append("from emp where emp_name=?");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());

			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			while (rs.next()) {

				EmpTO emp = new EmpTO();
				emp.setEmpName(rs.getString("emp_name"));
				emp.setDeptName(rs.getString("dept_name"));
				emp.setPosition(rs.getString("position"));
				emp.setGender(rs.getString("gender"));
				emp.setMobileNumber(rs.getString("mobile_number"));
				emp.setEmpCode(rs.getString("emp_code"));
				emp.setAddress(rs.getString("address"));
				emp.setDetailAddress(rs.getString("detail_address"));
				emp.setBirthdate(rs.getString("birthdate"));
				emp.setPostNumber(rs.getString("post_number"));
				emp.setImgExtend(rs.getString("img_extend"));
				emp.setLastSchool(rs.getString("last_school"));
				emp.setEmail(rs.getString("email"));
				list.add(emp);

			}

			return list;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public String getEmpCode(String name) {
		String empCode = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("select emp_code from emp where emp_name=?");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				empCode = rs.getString("emp_code");
			}

			return empCode;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public EmpTO selectEmployee(String empCode) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select emp_name,emp.dept_name,position,gender,mobile_number,emp_code, ");
			query.append("address,detail_address,TO_CHAR(birthdate,'YYYY/MM/DD') birthdate,post_number, ");
			query.append("img_extend,last_school,email from emp, dept ");
			query.append("where emp.dept_name=dept.dept_name ");
			query.append("and emp.emp_code=?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empCode);
			rs = pstmt.executeQuery();
			EmpTO emp = new EmpTO();
			if (rs.next()) {
				emp.setEmpName(rs.getString("emp_name"));
				emp.setDeptName(rs.getString("dept_name"));
				emp.setPosition(rs.getString("position"));
				emp.setGender(rs.getString("gender"));
				emp.setMobileNumber(rs.getString("mobile_number"));
				emp.setEmpCode(rs.getString("emp_code"));
				emp.setAddress(rs.getString("address"));
				emp.setDetailAddress(rs.getString("detail_address"));
				emp.setBirthdate(rs.getString("birthdate"));
				emp.setPostNumber(rs.getString("post_number"));
				emp.setImgExtend(rs.getString("img_extend"));
				emp.setLastSchool(rs.getString("last_school"));
				emp.setEmail(rs.getString("email"));
			}

			return emp;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public void updateEmployee(EmpTO emp) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			query.append("update emp set ");
			query.append("EMP_NAME = ?, BIRTHDATE = to_date(?,'YYYY/MM/DD'),GENDER= ?, MOBILE_NUMBER=?,  ");
			query.append("ADDRESS = ?, DETAIL_ADDRESS = ?, POST_NUMBER = ?, EMAIL= ?, LAST_SCHOOL=?, IMG_EXTEND=?,  ");
			query.append("POSITION=?, DEPT_NAME=? where emp_code = ? ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, emp.getEmpName());
			pstmt.setString(2, emp.getBirthdate());
			pstmt.setString(3, emp.getGender());
			pstmt.setString(4, emp.getMobileNumber());
			pstmt.setString(5, emp.getAddress());
			pstmt.setString(6, emp.getDetailAddress());
			pstmt.setString(7, emp.getPostNumber());
			pstmt.setString(8, emp.getEmail());
			pstmt.setString(9, emp.getLastSchool());
			pstmt.setString(10, emp.getImgExtend());
			pstmt.setString(11, emp.getPosition());
			pstmt.setString(12, emp.getDeptName());
			pstmt.setString(13, emp.getEmpCode());
			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}

	@Override
	public void deleteEmployee(EmpTO emp) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			query.append("delete from emp where emp_code = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, emp.getEmpCode());
			pstmt.executeUpdate();

		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}

}
