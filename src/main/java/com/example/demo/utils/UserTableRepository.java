package com.example.demo.utils;

import com.example.demo.DTO.LoginDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("com.example.demo.utils.UserTableRepository")
public interface UserTableRepository extends JpaRepository<LoginDTO, String> {


}
