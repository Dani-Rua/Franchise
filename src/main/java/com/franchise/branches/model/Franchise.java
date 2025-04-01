package com.franchise.branches.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "franchise")
public class Franchise {

    @Id
    private String name;
    private List<Branch> branchList;
}
