package kr.co.wmhr.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.wmhr.base.to.DetailCodeTO;
import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;

public class DetailCodeDAOImpl implements DetailCodeDAO {

	private DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager.getInstance();

	public ArrayList<DetailCodeTO> selectDetailCodeList(String codetype) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select * from detail_code where code_number = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, codetype);
			rs = pstmt.executeQuery();

			ArrayList<DetailCodeTO> detailCodeList = new ArrayList<DetailCodeTO>();
			DetailCodeTO detailCode = null;
			while (rs.next()) {
				detailCode = new DetailCodeTO();
				detailCode.setCodeNumber(rs.getString("code_number"));
				detailCode.setDetailCodeNumber(rs.getString("detail_code_number"));
				detailCode.setDetailCodeName(rs.getString("detail_code_name"));
				detailCode.setDetailCodeNameusing(rs.getString("detail_code_nameusing"));
				detailCodeList.add(detailCode);
			}

			return detailCodeList;
		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public ArrayList<DetailCodeTO> selectDetailCodeListRest(String code1, String code2, String code3) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append(
					"select * from detail_code where DETAIL_CODE_NUMBER = ? OR DETAIL_CODE_NUMBER = ? OR DETAIL_CODE_NUMBER = ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, code1);
			pstmt.setString(2, code2);
			pstmt.setString(3, code3);
			rs = pstmt.executeQuery();

			ArrayList<DetailCodeTO> detailCodeList = new ArrayList<DetailCodeTO>();
			DetailCodeTO detailCode = null;
			while (rs.next()) {
				detailCode = new DetailCodeTO();
				detailCode.setCodeNumber(rs.getString("code_number"));
				detailCode.setDetailCodeNumber(rs.getString("detail_code_number"));
				detailCode.setDetailCodeName(rs.getString("detail_code_name"));
				detailCode.setDetailCodeNameusing(rs.getString("detail_code_nameusing"));
				detailCodeList.add(detailCode);
			}

			return detailCodeList;
		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	@Override
	public void registDetailCode(DetailCodeTO detailCodeto) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			StringBuffer insertQuery = new StringBuffer();
			insertQuery.append("insert into detail_code values(?,?,?,?)");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(insertQuery.toString());

			pstmt.setString(1, detailCodeto.getDetailCodeNumber());
			pstmt.setString(2, detailCodeto.getCodeNumber());
			pstmt.setString(3, detailCodeto.getDetailCodeName());
			pstmt.setString(4, detailCodeto.getDetailCodeNameusing());

			pstmt.executeUpdate();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}

	@Override
	public void deleteDetailCode(DetailCodeTO detailCodeto) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("DELETE FROM DETAIL_CODE WHERE DETAIL_CODE_NUMBER = ? AND DETAIL_CODE_NAME = ? ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, detailCodeto.getDetailCodeNumber());
			pstmt.setString(2, detailCodeto.getDetailCodeName());
			pstmt.executeUpdate();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt);
		}

	}

	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

}
