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
	@NamedQuery(name = "StdMilkRate.findByAll", query = "from StdMilkRate order by id"),
    @NamedQuery(name = "StdMilkRate.findById", query = "from StdMilkRate where id = :id"),
    @NamedQuery(name = "StdMilkRate.findByPageName", query = "from StdMilkRate where pageName = :pageName"),
    @NamedQuery(name = "StdMilkRate.findByType", query = "from StdMilkRate where type = :type"),
    @NamedQuery(name = "StdMilkRate.findByTypePageName", query = "from StdMilkRate where type = :type AND pageName = :pageName")
})
@Table(name = "std_milk_rate", catalog = "dairyfarm")
@Where(clause = "status > 0")
public class StdMilkRate implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "type")
	private String type;
	@Column(name = "goodFat")
	private Double goodFat;
	@Column(name = "goodSnf")
	private Double goodSnf;
	@Column(name = "badFat")
	private Double badFat;
	@Column(name = "badSnf")
	private Double badSnf;
	@Column(name = "pageName")
	private String pageName;
	@Column(name = "updatedOn")
	private Date updatedOn;
	@Column(name = "status")
	private int status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getGoodFat() {
		return goodFat;
	}
	public void setGoodFat(Double goodFat) {
		this.goodFat = goodFat;
	}
	public Double getGoodSnf() {
		return goodSnf;
	}
	public void setGoodSnf(Double goodSnf) {
		this.goodSnf = goodSnf;
	}
	public Double getBadFat() {
		return badFat;
	}
	public void setBadFat(Double badFat) {
		this.badFat = badFat;
	}
	public Double getBadSnf() {
		return badSnf;
	}
	public void setBadSnf(Double badSnf) {
		this.badSnf = badSnf;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
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
	@Override
	public String toString() {
		return "StdMilkRate [id=" + id + "]";
	}
}