package com.franchise.branches.services;

import com.franchise.branches.model.Branch;
import com.franchise.branches.model.Franchise;
import com.franchise.branches.model.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IFranchiseService {

    Optional<Franchise> manageFranchise(Franchise franchise);
    Optional<Franchise> findByNameFranchise(String name);
    Optional<Franchise> manageBranch(String name, Branch updateBranch);
    Optional<Franchise> addProductToBranch(String franchiseName, String branchName, Product updateProduct);
    Optional<Franchise> removeProductFromBranch(String franchiseName, String branchName, String productName);
    List<Map<String, Object>> getProductsWithMaxStockByBranch(String franchiseName);
}
