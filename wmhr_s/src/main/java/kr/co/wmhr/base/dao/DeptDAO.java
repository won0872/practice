package kr.co.wmhr.base.dao;

import java.util.ArrayList;

import kr.co.wmhr.base.to.DeptTO;

public interface DeptDAO {
	public DeptTO selectDept(String dept);
	public ArrayList<DeptTO> selectDeptList();
	public void registDept(DeptTO dept);
	public void deleteDept(DeptTO dept);
}
