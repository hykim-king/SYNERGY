package com.pcwk.ehr.car;

import java.sql.Date;

public class CarDTO {
	private int car_code;
	private String product_name;
	private String car_mf;
	private String cartype;
	private String org_fn;
	private String mod_fn;
	private String path;
	private int price;
	private String fuel;
	private int ef;
	private String engine;
	private int dpm;
	private int battery;
	private int mf_dt;
	private Date regDt;
	private String regId;
	private Date modDt;
	private String modId;
	
	
	
	public CarDTO() {
		
	}



	/**
	 * @return the car_code
	 */
	public int getCar_code() {
		return car_code;
	}



	/**
	 * @param car_code the car_code to set
	 */
	public void setCar_code(int car_code) {
		this.car_code = car_code;
	}



	/**
	 * @return the product_name
	 */
	public String getProduct_name() {
		return product_name;
	}



	/**
	 * @param product_name the product_name to set
	 */
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}



	/**
	 * @return the car_mf
	 */
	public String getCar_mf() {
		return car_mf;
	}



	/**
	 * @param car_mf the car_mf to set
	 */
	public void setCar_mf(String car_mf) {
		this.car_mf = car_mf;
	}



	/**
	 * @return the cartype
	 */
	public String getCartype() {
		return cartype;
	}



	/**
	 * @param cartype the cartype to set
	 */
	public void setCartype(String cartype) {
		this.cartype = cartype;
	}



	/**
	 * @return the org_fn
	 */
	public String getOrg_fn() {
		return org_fn;
	}



	/**
	 * @param org_fn the org_fn to set
	 */
	public void setOrg_fn(String org_fn) {
		this.org_fn = org_fn;
	}



	/**
	 * @return the mod_fn
	 */
	public String getMod_fn() {
		return mod_fn;
	}



	/**
	 * @param mod_fn the mod_fn to set
	 */
	public void setMod_fn(String mod_fn) {
		this.mod_fn = mod_fn;
	}



	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}



	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}



	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}



	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}



	/**
	 * @return the fuel
	 */
	public String getFuel() {
		return fuel;
	}



	/**
	 * @param fuel the fuel to set
	 */
	public void setFuel(String fuel) {
		this.fuel = fuel;
	}



	/**
	 * @return the ef
	 */
	public int getEf() {
		return ef;
	}



	/**
	 * @param ef the ef to set
	 */
	public void setEf(int ef) {
		this.ef = ef;
	}



	/**
	 * @return the engine
	 */
	public String getEngine() {
		return engine;
	}



	/**
	 * @param engine the engine to set
	 */
	public void setEngine(String engine) {
		this.engine = engine;
	}



	/**
	 * @return the dpm
	 */
	public int getDpm() {
		return dpm;
	}



	/**
	 * @param dpm the dpm to set
	 */
	public void setDpm(int dpm) {
		this.dpm = dpm;
	}



	/**
	 * @return the battery
	 */
	public int getBattery() {
		return battery;
	}



	/**
	 * @param battery the battery to set
	 */
	public void setBattery(int battery) {
		this.battery = battery;
	}



	/**
	 * @return the mf_dt
	 */
	public int getMf_dt() {
		return mf_dt;
	}



	/**
	 * @param mf_dt the mf_dt to set
	 */
	public void setMf_dt(int mf_dt) {
		this.mf_dt = mf_dt;
	}



	/**
	 * @return the regDt
	 */
	public Date getRegDt() {
		return regDt;
	}



	/**
	 * @param regDt the regDt to set
	 */
	public void setRegDt(Date regDt) {
		this.regDt = regDt;
	}



	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}



	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}



	/**
	 * @return the modDt
	 */
	public Date getModDt() {
		return modDt;
	}



	/**
	 * @param modDt the modDt to set
	 */
	public void setModDt(Date modDt) {
		this.modDt = modDt;
	}



	/**
	 * @return the modId
	 */
	public String getModId() {
		return modId;
	}



	/**
	 * @param modId the modId to set
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}



	@Override
	public String toString() {
		return "CarDTO [car_code=" + car_code + ", product_name=" + product_name + ", car_mf=" + car_mf + ", cartype="
				+ cartype + ", org_fn=" + org_fn + ", mod_fn=" + mod_fn + ", path=" + path + ", price=" + price
				+ ", fuel=" + fuel + ", ef=" + ef + ", engine=" + engine + ", dpm=" + dpm + ", battery=" + battery
				+ ", mf_dt=" + mf_dt + ", regDt=" + regDt + ", regId=" + regId + ", modDt=" + modDt + ", modId=" + modId
				+ "]";
	}
	
	
	
}
