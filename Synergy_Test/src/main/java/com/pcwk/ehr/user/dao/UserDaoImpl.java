/**
 * Package Name : com.pcwk.ehr.user.dao <br/>
 * 파일명: UserDao.java <br/>
 */
package com.pcwk.ehr.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pcwk.ehr.user.domain.Level;
import com.pcwk.ehr.user.domain.UserDTO;

public class UserDaoImpl implements UserDao {

	public UserDaoImpl() {
	}
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private RowMapper<UserDTO> rowMapper = new RowMapper<UserDTO>() {

		@Override
		public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserDTO outVO = new UserDTO();
			outVO.setUserId(rs.getString("user_id"));
			outVO.setName(rs.getString("name"));
			outVO.setPassword(rs.getString("password"));
			outVO.setRegDt(rs.getString("reg_dt_str"));
			outVO.setLogin(rs.getInt("login"));
			outVO.setRecommand(rs.getInt("recommand"));
			outVO.setGrade(Level.valueOf(rs.getInt("grade")));
			outVO.setEmail(rs.getNString("email"));
			log.debug("3 outVO:" + outVO);
			return outVO;
		}
	};

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;

		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	Logger log = LogManager.getLogger(getClass());


	@Override
	public int getCount() throws SQLException {
		// select count(*) total_cnt from member

		int count = 0;
		StringBuilder sb = new StringBuilder(200);
		sb.append("select count(*) total_cnt from member \n");

		log.debug("1.sql:\n" + sb.toString());
		count = jdbcTemplate.queryForObject(sb.toString(), Integer.class);
		log.debug("2.count:" + count);

		return count;
	}

	@Override
	public List<UserDTO> doretrieve(UserDTO param) {
		List<UserDTO> list = new ArrayList<UserDTO>();
		StringBuilder sb = new StringBuilder(200);
		sb.append("SELECT A.*,B.*                                                     \n");
		sb.append(" FROM(                                                             \n");
		sb.append("SELECT tt3.RNUM as no,                                                   \n");
		sb.append("       tt3.user_id,                                                \n");
		sb.append("       tt3.name,                                                   \n");
		sb.append("       tt3.password,                                               \n");
		sb.append("       tt3.login,                                                  \n");
		sb.append("       tt3.recommand,                                              \n");
		sb.append("       tt3.grade,                                                  \n");
		sb.append("       tt3.email,                                                  \n");
		sb.append("       TO_CHAR(tt3.reg_dt,'YYYY/MM/DD HH24:MI:SS') AS reg_dt_str   \n");
		sb.append(" FROM                                                              \n");
		sb.append("(                                                                  \n");
		sb.append("SELECT ROWNUM AS RNUM,                                             \n");
		sb.append("       tt2.*                                                       \n");
		sb.append("    FROM(                                                          \n");
		sb.append("        SELECT t1.*                                                \n");
		sb.append("          FROM member t1                                           \n");
		sb.append("        ORDER BY t1.reg_dt desc                                    \n");
		sb.append(")tt2                                                               \n");
		sb.append("WHERE ROWNUM <= 10                                                 \n");
		sb.append(")tt3                                                               \n");
		sb.append("WHERE RNUM >=1                                                     \n");
		sb.append(")A                                                                 \n");
		sb.append("CROSS JOIN                                                         \n");
		sb.append("(SELECT COUNT(*) AS total_cnt                                      \n");
		sb.append(" FROM member)B                                                     \n");

		log.debug("2.sql:\n" + sb.toString());
		Object[] args = {};

		RowMapper<UserDTO> rowMapper = new RowMapper<UserDTO>() {

			@Override
			public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserDTO outVO = new UserDTO();
				outVO.setUserId(rs.getString("user_id"));
				outVO.setName(rs.getString("name"));
				outVO.setPassword(rs.getString("password"));
				outVO.setRegDt(rs.getString("reg_dt_str"));
				outVO.setLogin(rs.getInt("login"));
				outVO.setRecommand(rs.getInt("recommand"));
				outVO.setGrade(Level.valueOf(rs.getInt("grade")));
				outVO.setEmail(rs.getNString("email"));
				outVO.setNo(rs.getInt("no"));
				outVO.setTotalCnt(rs.getInt("total_cnt"));

				log.debug("3 outVO:" + outVO);
				return outVO;
			}
		};
		list = jdbcTemplate.query(sb.toString(), rowMapper);

		return list;
	}

	@Override
	public int saveAll() {
		int flag = 0;
		StringBuilder sb = new StringBuilder(200);
		sb.append("INSERT INTO MEMBER                                      \n");
		sb.append("SELECT 'jamesol' ||  level as user_id,                  \n");
		sb.append("       '이상무' || level as name,                        \n");
		sb.append("       '4321_' || level as password,                    \n");
		sb.append("       MOD(level,10)    as login,                       \n");
		sb.append("       MOD(level,2)     as recommand,                   \n");
		sb.append("       DECODE(MOD(level,3),0,3,MOD(level,3)) as grade,  \n");
		sb.append("       'dlwhd0614@naver.com' as email,                  \n");
		sb.append("       SYSDATE - level as reg_dt                        \n");
		sb.append(" FROM dual                                              \n");
		sb.append(" CONNECT BY LEVEL <= 502                                \n");

		flag = jdbcTemplate.update(sb.toString());

		return flag;
	}

	@Override
	public int doDelete(UserDTO param) {
		int flag = 0;
		StringBuilder sb = new StringBuilder(200);

		sb.append(" delete from member \n");
		sb.append("WHERE                    \n");
		sb.append("   user_id  = ?          \n");

		log.debug("2.sql:\n" + sb.toString());
		Object[] args = { param.getUserId() };

		flag = jdbcTemplate.update(sb.toString(), args);

		return flag;
	}

	@Override
	public int doUpdate(UserDTO param) {
		int flag = 0;
		StringBuilder sb = new StringBuilder(200);
		sb.append("UPDATE member            \n");
		sb.append("SET  name      = ?,      \n");
		sb.append("     password  = ?,      \n");
		sb.append("     login     = ?,      \n");
		sb.append("     recommand = ?,      \n");
		sb.append("     grade     = ?,      \n");
		sb.append("     email     = ?,      \n");
		sb.append("   reg_dt    = SYSDATE  \n");
		sb.append("WHERE                    \n");
		sb.append("   user_id  = ?          \n");

		Object[] args = { param.getName(), param.getPassword(), param.getLogin(), param.getRecommand(),
				param.getGrade().getValue(), param.getEmail(), param.getUserId() };
		log.debug("param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		flag = jdbcTemplate.update(sb.toString(), args);

		return flag;

	}

	@Override
	public List<UserDTO> getAll() {

		List<UserDTO> userList = new ArrayList<UserDTO>();
		StringBuilder sb = new StringBuilder(200);
		sb.append("SELECT                                                    \n");
		sb.append("    user_id,                                              \n");
		sb.append("    name,                                                 \n");
		sb.append("    password,                                             \n");
		sb.append("    login,                                                \n");
		sb.append("    recommand,                                            \n");
		sb.append("    grade,                                                \n");
		sb.append("    email,                                                \n");
		sb.append("    TO_CHAR(reg_dt,'YYYY/MM/DD HH24:MI:SS') AS reg_dt_str \n");
		sb.append("FROM                                                      \n");
		sb.append("    member                                                \n");
		sb.append("ORDER BY user_id                                          \n");
		log.debug("2.sql:\n" + sb.toString());
		userList = jdbcTemplate.query(sb.toString(), rowMapper);
		return userList;
	}

	/**
	 * 전체 삭제
	 * 
	 * @throws SQLException
	 */
	@Override
	public void deleteAll() throws SQLException {
		StringBuilder sb = new StringBuilder(200);
		sb.append(" delete from member \n");
		log.debug("2.sql:\n" + sb.toString());
		jdbcTemplate.update(sb.toString());

	}

	/**
	 * 단건 조회
	 * 
	 * @param param
	 * @return UserDTO
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public UserDTO doSelectOne(UserDTO param) throws SQLException {
		UserDTO outDTO = null;
		// 3.1 sql
		StringBuilder sb = new StringBuilder(200);
		sb.append("SELECT                                                    \n");
		sb.append("    user_id,                                              \n");
		sb.append("    name,                                                 \n");
		sb.append("    password,                                             \n");
		sb.append("    login,                                                \n");
		sb.append("    recommand,                                            \n");
		sb.append("    grade,                                                \n");
		sb.append("    email,                                                \n");
		sb.append("    TO_CHAR(reg_dt,'YYYY/MM/DD HH24:MI:SS') AS reg_dt_str \n");
		sb.append("FROM                                                      \n");
		sb.append("    member                                                \n");
		sb.append("WHERE user_id = ?                                         \n");
		log.debug("2. sql:\n" + sb.toString());

		Object[] args = { param.getUserId() };

		log.debug("2.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}
		outDTO = jdbcTemplate.queryForObject(sb.toString(), rowMapper, args);

		if (outDTO == null) {
			throw new EmptyResultDataAccessException(param.getUserId() + "(아이디)를 확인하세요.", 0);

		}

		return outDTO;
	}

	/**
	 * 단건 등록
	 * 
	 * @param param
	 * @return 1(성공)/0(실패)
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Override
	public int doSave(UserDTO param) throws SQLException {
		int flag = 0;

		StringBuilder sb = new StringBuilder(200);
		sb.append("INSERT INTO member (  \n");
		sb.append("    user_id,          \n");
		sb.append("    name,             \n");
		sb.append("    password,         \n");
		sb.append("    login,            \n");
		sb.append("    recommand,        \n");
		sb.append("    grade,            \n");
		sb.append("    email,            \n");
		sb.append("    reg_dt            \n");
		sb.append(") VALUES ( ?,         \n");
		sb.append("           ?,         \n");
		sb.append("           ?,         \n");
		sb.append("           ?,         \n");
		sb.append("           ?,         \n");
		sb.append("           ?,         \n");
		sb.append("           ?,         \n");
		sb.append("           SYSDATE )  \n");
		log.debug("2.sql\n" + sb.toString());

		Object[] args = { param.getUserId(), param.getName(), param.getPassword(), param.getLogin(),
				param.getRecommand(), param.getGrade().getValue(), param.getEmail() };
		log.debug("param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		flag = jdbcTemplate.update(sb.toString(), args);

		return flag;
	}
}