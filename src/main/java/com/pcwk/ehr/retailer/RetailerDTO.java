package com.pcwk.ehr.retailer;
import java.sql.Date;
/**
 * @author USER
 *
 */
public class RetailerDTO {
	 private int retailerCode;
	    private String productName;
	    private String retailerName;
	    private String carMf;
	    private String area;
	    private String address;
	    private String telephone;
	    private Date regDt;
	    private String regId;
	    private Date modDt;
	    private String modId;
	   
	    public RetailerDTO() {}
	   
	   
		/**
		 * @param retailerCode
		 * @param productName
		 * @param retailerName
		 * @param carMf
		 * @param area
		 * @param address
		 * @param telephone
		 * @param regDt
		 * @param regId
		 * @param modDt
		 * @param modId
		 */
		public RetailerDTO(int retailerCode, String productName, String retailerName, String carMf, String area,
				String address, String telephone, Date regDt, String regId, Date modDt, String modId) {
			super();
			this.retailerCode = retailerCode;
			this.productName = productName;
			this.retailerName = retailerName;
			this.carMf = carMf;
			this.area = area;
			this.address = address;
			this.telephone = telephone;
			this.regDt = regDt;
			this.regId = regId;
			this.modDt = modDt;
			this.modId = modId;
		}
		/**
		 * @return the retailerCode
		 */
		public int getRetailerCode() {
			return retailerCode;
		}
		/**
		 * @param retailerCode the retailerCode to set
		 */
		public void setRetailerCode(int retailerCode) {
			this.retailerCode = retailerCode;
		}
		/**
		 * @return the productName
		 */
		public String getProductName() {
			return productName;
		}
		/**
		 * @param productName the productName to set
		 */
		public void setProductName(String productName) {
			this.productName = productName;
		}
		/**
		 * @return the retailerName
		 */
		public String getRetailerName() {
			return retailerName;
		}
		/**
		 * @param retailerName the retailerName to set
		 */
		public void setRetailerName(String retailerName) {
			this.retailerName = retailerName;
		}
		/**
		 * @return the carMf
		 */
		public String getCarMf() {
			return carMf;
		}
		/**
		 * @param carMf the carMf to set
		 */
		public void setCarMf(String carMf) {
			this.carMf = carMf;
		}
		/**
		 * @return the area
		 */
		public String getArea() {
			return area;
		}
		/**
		 * @param area the area to set
		 */
		public void setArea(String area) {
			this.area = area;
		}
		/**
		 * @return the address
		 */
		public String getAddress() {
			return address;
		}
		/**
		 * @param address the address to set
		 */
		public void setAddress(String address) {
			this.address = address;
		}
		/**
		 * @return the telephone
		 */
		public String getTelephone() {
			return telephone;
		}
		/**
		 * @param telephone the telephone to set
		 */
		public void setTelephone(String telephone) {
			this.telephone = telephone;
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
			return "RetailerDTO [retailerCode=" + retailerCode + ", productName=" + productName + ", retailerName="
					+ retailerName + ", carMf=" + carMf + ", area=" + area + ", address=" + address + ", telephone="
					+ telephone + ", regDt=" + regDt + ", regId=" + regId + ", modDt=" + modDt + ", modId=" + modId
					+ "]";
		}
	   
	   
}