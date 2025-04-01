package com.franchise.branches.repository;

import com.franchise.branches.model.Franchise;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FranchiseRepository extends MongoRepository<Franchise, String> {
    Optional<Franchise> findByName(String nombre);
}
