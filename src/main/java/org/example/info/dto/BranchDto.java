package org.example.info.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.info.model.Commit;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto {
    private String name;
    private Commit commit;
}

