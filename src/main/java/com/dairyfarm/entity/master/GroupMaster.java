package com.dairyfarm.entity.master;

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
	@NamedQuery(name = "GroupMaster.findByAll", query = "from GroupMaster order by id"),
    @NamedQuery(name = "GroupMaster.findById", query = "from GroupMaster where id = :id"),
    @NamedQuery(name = "GroupMaster.findByIdStatus", query = "from GroupMaster where id = :id AND status = :status"),
    @NamedQuery(name = "GroupMaster.findByPartyName", query = "from GroupMaster where groupName = :groupName")
})
@Table(name = "group_master", catalog = "dairyfarm")
@Where(clause = "status > 0")
public class GroupMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "groupName")
	private String groupName;
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
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	
	@Override
	public String toString() {
		return "GroupMaster [id=" + id + "]";
	}
}