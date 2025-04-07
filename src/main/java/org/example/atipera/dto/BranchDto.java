package org.example.atipera.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.atipera.model.Commit;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto {
    private String name;
    private Commit commit;
}

