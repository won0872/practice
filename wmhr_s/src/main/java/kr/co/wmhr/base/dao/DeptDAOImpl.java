package kr.co.wmhr.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.wmhr.base.to.DeptTO;
import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;

public class DeptDAOImpl implements DeptDAO{
	private DataSourceTransactionManager dataSourceTransactionManager;
	
	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}
	

	@Override
	public DeptTO selectDept(String dept) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("select * from dept where dept_name=?");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(1, dept);
			rs=pstmt.executeQuery();
			DeptTO deptto=null;
			if(rs.next()){
				deptto=new DeptTO();
				deptto.setDeptCode(rs.getString("dept_code"));
				deptto.setDeptName(rs.getString("dept_name"));
				deptto.setDeptTel(rs.getString("dept_tel"));
			}
			
			return deptto;
		} catch(Exception sqle) {
			throw new DataAccessException(sqle.getMessage());			
		} finally {
				dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public ArrayList<DeptTO> selectDeptList() {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			System.out.println("4");
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("select * from dept");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			rs=pstmt.executeQuery();
			ArrayList<DeptTO> list=new ArrayList<DeptTO>(); 
			while(rs.next()){
				DeptTO dept=new DeptTO();
				dept.setDeptCode(rs.getString("dept_code"));
				dept.setDeptName(rs.getString("dept_name"));
				dept.setDeptTel(rs.getString("dept_tel"));
				list.add(dept);
			}
			System.out.println("5");
			return list;
		} catch(Exception sqle) {
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	@Override
	public void registDept(DeptTO deptto) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("insert into dept values(?,?,?)");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(1, deptto.getDeptCode());
			pstmt.setString(2, deptto.getDeptName());
			pstmt.setString(3, deptto.getDeptTel());
			pstmt.executeUpdate();
			
			
		} catch(Exception sqle) {
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}
	@Override
	public void deleteDept(DeptTO deptto) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("delete dept where dept_code = ?");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(1, deptto.getDeptCode());
			pstmt.executeUpdate();
			
			
		} catch(Exception sqle) {
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}

	

}