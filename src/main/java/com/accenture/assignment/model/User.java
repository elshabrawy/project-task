package com.accenture.assignment.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table (uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue

	private Long id;
	@Column(nullable = false)
	private String name;
	private String password;
	@ManyToMany
	private List<Role> roles;
	@ManyToMany
	private List<Feature> features;
}
