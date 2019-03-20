package kr.co.wmhr.base.dao;

import java.util.ArrayList;

import kr.co.wmhr.base.to.AuthorityTO;

public interface AuthorityDAO {

	ArrayList<AuthorityTO> selectAuthority(String deptCode, String positionCode);

	void insertAuthority(AuthorityTO authority);

	void deleteAuthority(AuthorityTO authority);

}
