package com.nxsol.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nxsol.model.User;

@Transactional
public interface UserRepository extends CrudRepository<User, Long>{

	User findByName(String name);

	@Modifying
	@Query("delete from User u where u.id=:id")
	void deleteUserByQuery(@Param("id") long id);


}
