package org.example.info.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.info.model.Owner;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryDto {
    private String name;
    private Owner owner;
    private boolean fork;
}