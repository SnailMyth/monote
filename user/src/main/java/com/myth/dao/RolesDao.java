package com.myth.dao;

import com.myth.base.BaseRepository;
import com.myth.domain.Roles;
import com.myth.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesDao extends BaseRepository<Roles,Integer> {

}
