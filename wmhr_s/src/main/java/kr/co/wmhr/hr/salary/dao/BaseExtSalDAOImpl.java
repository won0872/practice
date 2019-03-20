package kr.co.wmhr.hr.salary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;
import kr.co.wmhr.hr.salary.to.BaseExtSalTO;

public class BaseExtSalDAOImpl implements BaseExtSalDAO{
	private DataSourceTransactionManager dataSourceTransactionManager;
	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}
	@Override
	public ArrayList<BaseExtSalTO> selectBaseExtSalList() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select * from base_ext_sal ");
			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();

			ArrayList<BaseExtSalTO> baseExtSalList=new ArrayList<BaseExtSalTO>();
			BaseExtSalTO baseExtSal = null;
			while (rs.next()) {
				baseExtSal = new BaseExtSalTO();
				baseExtSal.setExtSalCode(rs.getString("ext_sal_code"));
				baseExtSal.setExtSalName(rs.getString("ext_sal_name"));
				baseExtSal.setRatio(rs.getString("ratio"));
				baseExtSalList.add(baseExtSal);
			}

			return baseExtSalList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}
	@Override
	public void updateBaseExtSal(BaseExtSalTO baseExtSal) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("update base_ext_sal set ");
			query.append("ratio = ? ");
			query.append("where ext_sal_code = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, baseExtSal.getRatio());
			pstmt.setString(2, baseExtSal.getExtSalCode());
			pstmt.executeUpdate();
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}
		
	}
}
