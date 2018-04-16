package com.myth.dao.user;

import com.myth.base.BaseRepository;
import com.myth.domain.user.Roles;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesDao extends BaseRepository<Roles,Integer> {

}
