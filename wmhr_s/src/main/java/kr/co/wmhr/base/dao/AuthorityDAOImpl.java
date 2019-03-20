package kr.co.wmhr.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.wmhr.base.to.AuthorityTO;
import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;

public class AuthorityDAOImpl implements AuthorityDAO{
private DataSourceTransactionManager dataSourceTransactionManager;
	
	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}
	
	
	@Override
	public ArrayList<AuthorityTO> selectAuthority(String deptCode, String positionCode) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("SELECT MA.MENU_CODE, M.MENU_NAME, MA.POSITION_CODE, P.POSITION, MA.DEPT_CODE, D.DEPT_NAME ");
			insertQuery.append("FROM (SELECT * FROM MENU_AUTHORITY) MA, DEPT D, POSITION P, MENU M ");
			insertQuery.append("WHERE MA.DEPT_CODE = D.DEPT_CODE AND MA.POSITION_CODE = P.POSITION_CODE ");
			insertQuery.append("AND MA.MENU_CODE = M.MENU_CODE AND MA.POSITION_CODE=? AND MA.DEPT_CODE=? ORDER BY 2, 6");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(1, positionCode);
			pstmt.setString(2, deptCode);
			rs=pstmt.executeQuery();
			ArrayList<AuthorityTO> list=new ArrayList<AuthorityTO>(); 
			while(rs.next()){
				AuthorityTO authority=new AuthorityTO();
				authority.setMenuCode(rs.getString("MENU_CODE"));
				authority.setMenuName(rs.getString("MENU_NAME"));
				authority.setPositionCode(rs.getString("POSITION_CODE"));
				authority.setPosition(rs.getString("POSITION"));
				authority.setDeptCode(rs.getString("DEPT_CODE"));
				authority.setDeptName(rs.getString("DEPT_NAME"));
				list.add(authority);
			}
			return list;
		} catch(Exception sqle) {
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public void insertAuthority(AuthorityTO authority) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("insert into MENU_AUTHORITY values(?,?,?)");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(1, authority.getMenuCode());
			pstmt.setString(2, authority.getPositionCode());
			pstmt.setString(3, authority.getDeptCode());
			pstmt.executeUpdate();
			
			
		} catch(Exception sqle) {
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}

	@Override
	public void deleteAuthority(AuthorityTO authority) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("delete MENU_AUTHORITY where menu_code = ? and position_Code = ? and dept_code = ?");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());
			pstmt.setString(1, authority.getMenuCode());
			pstmt.setString(2, authority.getPositionCode());
			pstmt.setString(3, authority.getDeptCode());
			pstmt.executeUpdate();
			
		} catch(Exception sqle) {
			throw new DataAccessException(sqle.getMessage());			
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}


}
