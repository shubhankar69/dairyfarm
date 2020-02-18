package com.dairyfarm.entity.settings;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Where;

@Entity
@NamedQueries({
	@NamedQuery(name = "MiscSettings.findByAll", query = "from MiscSettings order by id"),
    @NamedQuery(name = "MiscSettings.findById", query = "from MiscSettings where id = :id"),
    @NamedQuery(name = "MiscSettings.findByName", query = "from MiscSettings where name = :name"),
    @NamedQuery(name = "MiscSettings.findByType", query = "from MiscSettings where typeName = :typeName"),
    @NamedQuery(name = "MiscSettings.findByTypeName", query = "from MiscSettings where typeName = :typeName AND name = :name")
})
@Table(name = "misc_settings", catalog = "dairyfarm")
@Where(clause = "status > 0")
public class MiscSettings implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "typeName")
	private String typeName;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "value")
	private Double value;
	
	@Column(name = "operation")
	private String operation;
	
	@Column(name = "operationOn")
	private String operationOn;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "createdOn")
	private Date createdOn;
	
	@Column(name = "updatedOn")
	private Date updatedOn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOperationOn() {
		return operationOn;
	}

	public void setOperationOn(String operationOn) {
		this.operationOn = operationOn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}