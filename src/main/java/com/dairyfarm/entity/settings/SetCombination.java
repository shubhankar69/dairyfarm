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

@Entity
@NamedQueries({
	@NamedQuery(name = "SetCombination.findByAll", query = "from SetCombination"),
	@NamedQuery(name = "SetCombination.findById", query = "from SetCombination where id = :id")
})
@Table(name = "set_combination", catalog = "dairyfarm")
public class SetCombination  implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "setRate")
	private String setRate;
	
	@Column(name = "setStandard")
	private String setStandard;
	
	@Column(name = "updatedOn")
	private Date updatedOn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSetRate() {
		return setRate;
	}

	public void setSetRate(String setRate) {
		this.setRate = setRate;
	}

	public String getSetStandard() {
		return setStandard;
	}

	public void setSetStandard(String setStandard) {
		this.setStandard = setStandard;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Override
	public String toString() {
		return "SetCombination [id=" + id + "]";
	}	
	
}
