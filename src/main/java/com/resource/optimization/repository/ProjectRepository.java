package com.resource.optimization.repository;

import com.resource.optimization.entity.Account;
import com.resource.optimization.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByName(String name);

    List<Project> findAllByOwner(Account owner);
}
