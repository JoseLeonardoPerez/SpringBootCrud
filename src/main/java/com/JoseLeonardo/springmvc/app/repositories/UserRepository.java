package com.JoseLeonardo.springmvc.app.repositories;

import com.JoseLeonardo.springmvc.app.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{
}
