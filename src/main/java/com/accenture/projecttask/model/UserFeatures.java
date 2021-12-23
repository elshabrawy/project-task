package com.accenture.projecttask.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (uniqueConstraints = @UniqueConstraint(columnNames = {"feature_id","user_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserFeatures {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@ManyToOne
	private User user;
	@ManyToOne
	private Feature feature;	

}
