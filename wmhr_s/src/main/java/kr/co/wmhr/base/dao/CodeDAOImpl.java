package kr.co.wmhr.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.wmhr.base.to.CodeTO;
import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;

public class CodeDAOImpl implements CodeDAO {
	private DataSourceTransactionManager dataSourceTransactionManager;

	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	public ArrayList<CodeTO> selectCode() {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM DIVISION_CODE ORDER BY CODE_NUMBER");

			pstmt = con.prepareStatement(query.toString());
			rs = pstmt.executeQuery();

			ArrayList<CodeTO> codeList=new ArrayList<CodeTO>();
			CodeTO code= null;
			while(rs.next()){
				code=new CodeTO();
				code.setCodeName(rs.getString("CODE_NAME"));
				code.setCodeNumber(rs.getString("CODE_NUMBER"));
				code.setModifiable(rs.getString("MODIFIABLE"));
				codeList.add(code);
			}

			return codeList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

}
