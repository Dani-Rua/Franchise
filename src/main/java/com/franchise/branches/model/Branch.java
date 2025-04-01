package com.franchise.branches.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Branch {

    private String name;
    private List<Product> productList;
}
