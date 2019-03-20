package kr.co.wmhr.base.dao;

import java.util.ArrayList;

import kr.co.wmhr.base.to.DetailCodeTO;

public interface DetailCodeDAO {
	public ArrayList<DetailCodeTO> selectDetailCodeList(String codetype);
	public ArrayList<DetailCodeTO> selectDetailCodeListRest(String code1,String code2, String code3);
	
	public void registDetailCode(DetailCodeTO detailCodeto);
	public void deleteDetailCode(DetailCodeTO detailCodeto);
}
