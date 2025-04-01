package com.franchise.branches.controller;

import com.franchise.branches.model.Branch;
import com.franchise.branches.model.Franchise;
import com.franchise.branches.model.Product;
import com.franchise.branches.services.IFranchiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FranchiseController {

    private final IFranchiseService franchiseService;

    @GetMapping("/search-franchise")
    public ResponseEntity<Franchise> getFranchiseByName(@RequestBody Franchise request){
        return franchiseService.findByNameFranchise(request.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/save/franchise")
    public ResponseEntity<Franchise> registerFranchise(@RequestBody Franchise request){
        return franchiseService.manageFranchise(request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/update-branch/{name}")
    public ResponseEntity<Franchise> updateBranch(@PathVariable String name, @RequestBody Branch request){
        return franchiseService.manageBranch(name, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/{franchiseName}/branches/{branchName}/products")
    public ResponseEntity<Franchise> addProductToBranch(
            @PathVariable String franchiseName, @PathVariable String branchName, @RequestBody Product product) {
        return franchiseService.addProductToBranch(franchiseName, branchName, product)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{franchiseName}/branches/{branchName}/products/{productName}")
    public ResponseEntity<Franchise> removeProductFromBranch(
            @PathVariable String franchiseName,
            @PathVariable String branchName,
            @PathVariable String productName) {
        return franchiseService.removeProductFromBranch(franchiseName, branchName, productName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{franchiseName}/max-stock-products")
    public ResponseEntity<List<Map<String, Object>>> getProductsWithMaxStockByBranch(
            @PathVariable String franchiseName) {
        List<Map<String, Object>> result = franchiseService.getProductsWithMaxStockByBranch(franchiseName);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
