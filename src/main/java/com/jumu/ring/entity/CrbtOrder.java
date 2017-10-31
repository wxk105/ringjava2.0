package com.jumu.ring.entity;

import com.jumu.ring.enums.PayStatus;
import com.jumu.ring.enums.PriceType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * 彩铃订单 Created by Administrator on 2017/8/22.
 */
@Entity
public class CrbtOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String phone; // 手机号

	@OneToOne(fetch = FetchType.EAGER)
	private MobileCrbt mobileCrbt;

	private PriceType priceType; // 价格类型

	private PayStatus payStatus; // 支付状态

	private double fee; // 费用

	@CreationTimestamp
	private Date createTime; // 创建时间

	private Byte status;

	private Date paySuccessDate; // 支付成功时间

	private String tgid;
	private String openid;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public MobileCrbt getMobileCrbt() {
		return mobileCrbt;
	}

	public void setMobileCrbt(MobileCrbt mobileCrbt) {
		this.mobileCrbt = mobileCrbt;
	}

	public PriceType getPriceType() {
		return priceType;
	}

	public void setPriceType(PriceType priceType) {
		this.priceType = priceType;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPaySuccessDate() {
		return paySuccessDate;
	}

	public void setPaySuccessDate(Date paySuccessDate) {
		this.paySuccessDate = paySuccessDate;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getTgid() {
		return tgid;
	}

	public void setTgid(String tgid) {
		this.tgid = tgid;
	}

	@Override
	public String toString() {
		return "CrbtOrder [id=" + id + ", phone=" + phone + ", mobileCrbt="
				+ mobileCrbt + ", priceType=" + priceType + ", payStatus="
				+ payStatus + ", fee=" + fee + ", createTime=" + createTime
				+ ", status=" + status + ", paySuccessDate=" + paySuccessDate
				+ ", tgid=" + tgid + ", openid=" + openid + "]";
	}

}
