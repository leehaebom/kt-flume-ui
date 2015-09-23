package Flume.Dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Flume.Domain.MetricStat;

@Repository
public class MetricStatDao {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	public SqlSession sessionOpen() {
		return sqlSessionFactory.openSession();
	}
	
	public List<MetricStat> selectList() {
		SqlSession sqlSession = sessionOpen();
		try {
			return sqlSession.selectList("selectALL");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			sqlSession.close();
		}
	}
	
	public void insertStat(Map<String, Object> map) {
		SqlSession sqlSession = sessionOpen();
		try{
		System.out.println(map);
		sqlSession.insert("insertSTAT",map);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
	}
	
	
	
	public HashMap<String, Long> selectDiff(String host_name,String flow_num) {
		SqlSession sqlSession = sessionOpen();
		try{
			HashMap<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("host_name",host_name);
			paramMap.put("flow_num", flow_num);
			System.out.println(host_name+","+flow_num);
			return sqlSession.selectOne("selectDiff",paramMap);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			sqlSession.close();
		}
		
		
	}
}
