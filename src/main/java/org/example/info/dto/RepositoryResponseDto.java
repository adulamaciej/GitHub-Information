package org.example.info.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.info.model.Branch;
import org.example.info.model.Owner;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryResponseDto {
    private String name;
    private Owner owner;
    private List<Branch> branches;
}