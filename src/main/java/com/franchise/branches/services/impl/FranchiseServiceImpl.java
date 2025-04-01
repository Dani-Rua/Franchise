package com.franchise.branches.services.impl;

import com.franchise.branches.model.Branch;
import com.franchise.branches.model.Franchise;
import com.franchise.branches.model.Product;
import com.franchise.branches.repository.FranchiseRepository;
import com.franchise.branches.services.IFranchiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FranchiseServiceImpl implements IFranchiseService {

    private final FranchiseRepository franchiseRepository;

    @Override
    public Optional<Franchise> manageFranchise(Franchise franchise) {
        return Optional.ofNullable(franchise)
                .map(franchiseRepository::save);
    }

    @Override
    public Optional<Franchise> findByNameFranchise(String name) {
        return franchiseRepository.findByName(name);
    }

    @Override
    public Optional<Franchise> manageBranch(String name, Branch updateBranch) {
        Optional<Franchise> franchiseOptional = franchiseRepository.findByName(name);
        if (franchiseOptional.isPresent()) {
            Franchise franchise = franchiseOptional.get();
            List<Branch> branchList = franchise.getBranchList();

            // Inicializar la lista si es null
            if (branchList == null) {
                branchList = new ArrayList<>();
                franchise.setBranchList(branchList); // Importante: actualizar la franquicia con la nueva lista
            }

            Optional<Branch> existingBranch = branchList.stream()
                    .filter(branch -> branch.getName().equals(updateBranch.getName()))
                    .findFirst();

            if (existingBranch.isPresent()) {
                int index = branchList.indexOf(existingBranch.get());
                branchList.set(index, updateBranch);
            } else {
                branchList.add(updateBranch);
            }

            franchiseRepository.save(franchise);
            return Optional.of(franchise);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Franchise> addProductToBranch(String franchiseName, String branchName, Product updateProduct) {
        Optional<Franchise> franchiseOptional = franchiseRepository.findByName(franchiseName);
        if (franchiseOptional.isPresent()) {
            Franchise franchise = franchiseOptional.get();
            for (Branch branch : franchise.getBranchList()) {
                if (branch.getName().equals(branchName)) {
                    List<Product> productList = branch.getProductList();
                    if (productList == null) {
                        productList = new ArrayList<>();
                        branch.setProductList(productList);
                    }
                    Optional<Product> existingProduct = productList.stream()
                            .filter(product -> product.getName().equals(updateProduct.getName()))
                            .findFirst();

                    if (existingProduct.isPresent()) {
                        Product productToUpdate = existingProduct.get();
                        productToUpdate.setStock(productToUpdate.getStock() + updateProduct.getStock());
                    } else {
                        productList.add(updateProduct);
                    }

                    franchiseRepository.save(franchise);
                    return Optional.of(franchise);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Franchise> removeProductFromBranch(String franchiseName, String branchName, String productName) {
        Optional<Franchise> franchiseOptional = franchiseRepository.findByName(franchiseName);

        if (franchiseOptional.isPresent()) {
            Franchise franchise = franchiseOptional.get();
            List<Branch> branchList = franchise.getBranchList();

            if (branchList != null) {
                for (Branch branch : branchList) {
                    if (branch.getName().equals(branchName)) {
                        List<Product> productList = branch.getProductList();
                        if (productList != null) {
                            productList.removeIf(product -> product.getName().equals(productName));
                            branch.setProductList(productList);
                            franchiseRepository.save(franchise);
                            return Optional.of(franchise);
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Map<String, Object>> getProductsWithMaxStockByBranch(String franchiseName) {
        Optional<Franchise> franchiseOptional = franchiseRepository.findByName(franchiseName);

        if (franchiseOptional.isPresent()) {
            Franchise franchise = franchiseOptional.get();
            List<Map<String, Object>> result = new ArrayList<>();

            if (franchise.getBranchList() != null) {
                for (Branch branch : franchise.getBranchList()) {
                    if (branch.getProductList() != null && !branch.getProductList().isEmpty()) {
                        Product maxStockProduct = branch.getProductList().get(0);
                        for (Product product : branch.getProductList()) {
                            if (product.getStock() > maxStockProduct.getStock()) {
                                maxStockProduct = product;
                            }
                        }

                        Map<String, Object> productInfo = new HashMap<>();
                        productInfo.put("branchName", branch.getName());
                        productInfo.put("productName", maxStockProduct.getName());
                        productInfo.put("stock", maxStockProduct.getStock());
                        result.add(productInfo);
                    }
                }
            }
            return result;
        }
        return null;
    }
}




