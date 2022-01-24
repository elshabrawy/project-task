package com.accenture.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFeatureDTO {
    private String userName;
    private List<String> allEnabledFeatures;
    private List<String> allUsersEnabledFeatures;
}
