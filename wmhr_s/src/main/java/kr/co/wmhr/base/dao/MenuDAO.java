package kr.co.wmhr.base.dao;

import java.util.ArrayList;

import kr.co.wmhr.base.to.CodeTO;
import kr.co.wmhr.base.to.DetailCodeTO;
import kr.co.wmhr.base.to.MenuTO;

public interface MenuDAO {
	public ArrayList<MenuTO> selectMenuList();
	public ArrayList<CodeTO> selectCodeList();
	public ArrayList<DetailCodeTO> selectDetailCodeList(String code);
	ArrayList<MenuTO> selectMenuList(String empCode);
}