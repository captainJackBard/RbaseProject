package com.templesalad.repository;

import com.templesalad.domain.Branch;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Branch entity.
 */
@SuppressWarnings("unused")
public interface BranchRepository extends JpaRepository<Branch,Long> {

    @Query("select branch from Branch branch where branch.user.login = ?#{principal.username}")
    List<Branch> findByUserIsCurrentUser();

}
