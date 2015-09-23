package Flume.Dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Flume.Domain.MetricInfo;

@Repository
public class MetricInfoDao {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	public SqlSession sessionOpen() {
		return sqlSessionFactory.openSession();
	}
	
	public List<MetricInfo> selectList() {
		SqlSession sqlSession = sessionOpen();
		try {
			return sqlSession.selectList("selectTest");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			sqlSession.close();
		}
	}
}
