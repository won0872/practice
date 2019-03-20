package kr.co.wmhr.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.wmhr.base.to.CodeTO;
import kr.co.wmhr.base.to.DetailCodeTO;
import kr.co.wmhr.base.to.MenuTO;
import kr.co.wmhr.common.exception.DataAccessException;
import kr.co.wmhr.common.transaction.DataSourceTransactionManager;

public class MenuDAOImpl implements MenuDAO {
	private DataSourceTransactionManager dataSourceTransactionManager;
	
		
	
	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	@Override
	public ArrayList<MenuTO> selectMenuList() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MenuTO> menuList = new ArrayList<MenuTO>();

		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("select * from menu");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MenuTO menuto = new MenuTO();
				menuto.setMenuCode(rs.getString("menu_code"));
				menuto.setSuperMenuCode(rs.getString("super_menu_code"));
				menuto.setMenuName(rs.getString("menu_name"));
				menuto.setMenuUrl(rs.getString("menu_url"));
				menuList.add(menuto);
			}

			return menuList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(con, pstmt, rs);
		}
	}

	@Override
	public ArrayList<CodeTO> selectCodeList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<CodeTO> codeList = new ArrayList<CodeTO>();

		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("select * from division_code");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CodeTO codeto = new CodeTO();
				codeto.setCodeNumber(rs.getString("code_number"));
				codeto.setCodeName(rs.getString("code_name"));
				codeto.setModifiable(rs.getString("modifiable"));
				codeList.add(codeto);
			}
			return codeList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(con, pstmt, rs);
		}
	}

	@Override
	public ArrayList<DetailCodeTO> selectDetailCodeList(String code) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<DetailCodeTO> detailCodeList = new ArrayList<DetailCodeTO>();

		try {
			StringBuffer selectQuery = new StringBuffer();
			selectQuery.append("select * from detail_code where code_number=?");
			con = dataSourceTransactionManager.getConnection();
			pstmt = con.prepareStatement(selectQuery.toString());
			pstmt.setString(1, code);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				DetailCodeTO detailcodeto = new DetailCodeTO();
				detailcodeto.setCodeNumber(rs.getString("code_number"));
				detailcodeto.setDetailCodeName(rs.getString("detail_code_name"));
				detailcodeto.setDetailCodeNameusing(rs.getString("detail_code_nameusing"));
				detailcodeto.setDetailCodeNumber(rs.getString("detail_code_number"));
				detailCodeList.add(detailcodeto);
			}
			return detailCodeList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(con, pstmt, rs);
		}
	}

	@Override
	public ArrayList<MenuTO> selectMenuList(String empCode) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			query.append(" SELECT M.MENU_CODE, M.MENU_NAME, M.SUPER_MENU_CODE, M.MENU_URL  ");
			query.append(" FROM EMP E, MENU_AUTHORITY MA, MENU M ");
			query.append(" AND MA.MENU_CODE = M.MENU_CODE ");
			query.append(" AND E.EMP_CODE=? ");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, empCode);
			rs = pstmt.executeQuery();

			ArrayList<MenuTO> menuList = new ArrayList<MenuTO>();
			MenuTO menu = null;
			while (rs.next()) {
				menu = new MenuTO();
				menu.setMenuCode(rs.getString("MENU_CODE"));
				menu.setSuperMenuCode(rs.getString("SUPER_MENU_CODE"));
				menu.setMenuName(rs.getString("MENU_NAME"));
				menu.setMenuUrl(rs.getString("MENU_URL"));
				menuList.add(menu);
			}
			return menuList;
		} catch (Exception sqle) {
			throw new DataAccessException(sqle.getMessage());
		} finally {
			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

}
