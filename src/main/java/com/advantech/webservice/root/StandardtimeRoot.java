//
// 此檔案是由 JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 所產生 
// 請參閱 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 一旦重新編譯來源綱要, 對此檔案所做的任何修改都將會遺失. 
// 產生時間: 2017.07.18 於 01:29:51 PM CST 
//
package com.advantech.webservice.root;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * anonymous complex type 的 Java 類別.
 *
 * <p>
 * 下列綱要片段會指定此類別中包含的預期內容.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="METHOD">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="STANDARD_WORKTIME">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="UNIT_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="STATION_ID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="LINE_ID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="ITEM_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="TOTAL_CT" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element name="FIRST_TIME" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="CT" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="SIDE" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="OP_CNT" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="KP_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="MACHINE_CNT" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "method",
    "standardworktime"
})
@XmlRootElement(name = "root")
public class StandardtimeRoot {

    @XmlElement(name = "METHOD", required = true)
    protected StandardtimeRoot.METHOD method;
    @XmlElement(name = "STANDARD_WORKTIME", required = true)
    protected StandardtimeRoot.STANDARDWORKTIME standardworktime;

    public StandardtimeRoot() {
        this.method = new StandardtimeRoot.METHOD();
        this.standardworktime = new StandardtimeRoot.STANDARDWORKTIME();
    }

    /**
     * 取得 method 特性的值.
     *
     * @return possible object is {@link Root.METHOD }
     *
     */
    public StandardtimeRoot.METHOD getMETHOD() {
        return method;
    }

    /**
     * 設定 method 特性的值.
     *
     * @param value allowed object is {@link Root.METHOD }
     *
     */
    public void setMETHOD(StandardtimeRoot.METHOD value) {
        this.method = value;
    }

    /**
     * 取得 standardworktime 特性的值.
     *
     * @return possible object is {@link Root.STANDARDWORKTIME }
     *
     */
    public StandardtimeRoot.STANDARDWORKTIME getSTANDARDWORKTIME() {
        return standardworktime;
    }

    /**
     * 設定 standardworktime 特性的值.
     *
     * @param value allowed object is {@link Root.STANDARDWORKTIME }
     *
     */
    public void setSTANDARDWORKTIME(StandardtimeRoot.STANDARDWORKTIME value) {
        this.standardworktime = value;
    }

    /**
     * <p>
     * anonymous complex type 的 Java 類別.
     *
     * <p>
     * 下列綱要片段會指定此類別中包含的預期內容.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class METHOD {

        @XmlAttribute(name = "ID")
        protected String id = "WBASSO.TxStandardWorktime";

        /**
         * 取得 id 特性的值.
         *
         * @return possible object is {@link String }
         *
         */
        public String getID() {
            return id;
        }

        /**
         * 設定 id 特性的值.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setID(String value) {
            this.id = value;
        }

    }

    /**
     * <p>
     * anonymous complex type 的 Java 類別.
     *
     * <p>
     * 下列綱要片段會指定此類別中包含的預期內容.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="UNIT_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="STATION_ID" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="LINE_ID" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="ITEM_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="TOTAL_CT" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element name="FIRST_TIME" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="CT" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="SIDE" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="OP_CNT" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="KP_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="MACHINE_CNT" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "standardid",
        "unitno",
        "stationid",
        "lineid",
        "itemno",
        "totalct",
        "firsttime",
        "ct",
        "side",
        "opcnt",
        "kptype",
        "machinecnt",
        "mixct",
        "autoct",
        "changeReasonNo"
    })
    public static class STANDARDWORKTIME {

        @XmlElement(name = "STANDARD_ID", required = true)
        protected Integer standardid;
        
        @XmlElement(name = "UNIT_NO", required = true, nillable = true)
        protected String unitno;
        
        @XmlElement(name = "ITEM_NO", required = true, nillable = true)
        protected String itemno;
        
        @XmlElement(name = "LINE_ID", required = true, nillable = true)
        protected Integer lineid;
        
        @XmlElement(name = "OP_CNT", required = true, nillable = true)
        protected Integer opcnt;
        
        @XmlElement(name = "FIRST_TIME", required = true, nillable = true)
        protected BigDecimal firsttime;
        
        @XmlElement(name = "TOTAL_CT", required = true, nillable = true)
        protected BigDecimal totalct;
        
        @XmlElement(name = "MIX_CT", required = true, nillable = true)
        protected BigDecimal mixct;
        
        @XmlElement(name = "AUTO_CT", required = true, nillable = true)
        protected BigDecimal autoct;
        
        @XmlElement(name = "CT", required = true, nillable = true)
        protected BigDecimal ct;
        
        @XmlElement(name = "MACHINE_CNT", required = true, nillable = true)
        protected Integer machinecnt;
        
        @XmlElement(name = "SIDE", required = true, nillable = true)
        protected Integer side;
        
        @XmlElement(name = "STATION_ID", required = true, nillable = true)
        protected Integer stationid;
        
        @XmlElement(name = "KP_TYPE", required = true, nillable = true)
        protected String kptype;
        
        @XmlElement(name = "CHANGE_REASON_NO", required = true, nillable = true)
        protected String changeReasonNo;

        /**
         * 取得 unitno 特性的值.
         *
         * @return possible object is {@link String }
         *
         */
        public String getUNITNO() {
            return unitno;
        }

        /**
         * 設定 unitno 特性的值.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setUNITNO(String value) {
            this.unitno = value;
        }

        /**
         * 取得 stationid 特性的值.
         *
         */
        public Integer getSTATIONID() {
            return stationid;
        }

        /**
         * 設定 stationid 特性的值.
         *
         */
        public void setSTATIONID(Integer value) {
            this.stationid = value;
        }

        /**
         * 取得 lineid 特性的值.
         *
         */
        public Integer getLINEID() {
            return lineid;
        }

        /**
         * 設定 lineid 特性的值.
         *
         */
        public void setLINEID(Integer value) {
            this.lineid = value;
        }

        /**
         * 取得 itemno 特性的值.
         *
         * @return possible object is {@link String }
         *
         */
        public String getITEMNO() {
            return itemno;
        }

        /**
         * 設定 itemno 特性的值.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setITEMNO(String value) {
            this.itemno = value;
        }

        /**
         * 取得 totalct 特性的值.
         *
         * @return possible object is {@link BigDecimal }
         *
         */
        public BigDecimal getTOTALCT() {
            return totalct;
        }

        /**
         * 設定 totalct 特性的值.
         *
         * @param value allowed object is {@link BigDecimal }
         *
         */
        public void setTOTALCT(BigDecimal value) {
            this.totalct = value;
        }

        /**
         * 取得 firsttime 特性的值.
         *
         */
        public BigDecimal getFIRSTTIME() {
            return firsttime;
        }

        /**
         * 設定 firsttime 特性的值.
         *
         */
        public void setFIRSTTIME(BigDecimal value) {
            this.firsttime = value;
        }

        /**
         * 取得 ct 特性的值.
         *
         */
        public BigDecimal getCT() {
            return ct;
        }

        /**
         * 設定 ct 特性的值.
         *
         */
        public void setCT(BigDecimal value) {
            this.ct = value;
        }

        /**
         * 取得 side 特性的值.
         *
         */
        public Integer getSIDE() {
            return side;
        }

        /**
         * 設定 side 特性的值.
         *
         */
        public void setSIDE(Integer value) {
            this.side = value;
        }

        /**
         * 取得 opcnt 特性的值.
         *
         */
        public Integer getOPCNT() {
            return opcnt;
        }

        /**
         * 設定 opcnt 特性的值.
         *
         */
        public void setOPCNT(Integer value) {
            this.opcnt = value;
        }

        /**
         * 取得 kptype 特性的值.
         *
         * @return possible object is {@link String }
         *
         */
        public String getKPTYPE() {
            return kptype;
        }

        /**
         * 設定 kptype 特性的值.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setKPTYPE(String value) {
            this.kptype = value;
        }

        /**
         * 取得 machinecnt 特性的值.
         *
         */
        public Integer getMACHINECNT() {
            return machinecnt;
        }

        /**
         * 設定 machinecnt 特性的值.
         *
         */
        public void setMACHINECNT(Integer value) {
            this.machinecnt = value;
        }

        /**
         * 取得 mixct 特性的值.
         *
         */
        public BigDecimal getMIXCT() {
            return mixct;
        }

        /**
         * 設定 mixct 特性的值.
         *
         */
        public void setMIXCT(BigDecimal value) {
            this.mixct = value;
        }

        /**
         * 取得 autoct 特性的值.
         *
         */
        public BigDecimal getAUTOCT() {
            return autoct;
        }

        /**
         * 設定 autoct 特性的值.
         *
         */
        public void setAUTOCT(BigDecimal value) {
            this.autoct = value;
        }

        /**
         * 設定 changeReasonNo 特性的值.
         *
         */
        public String getCHANGEREASONNO() {
            return changeReasonNo;
        }

        public void setCHANGEREASONNO(String changeReasonNo) {
            this.changeReasonNo = changeReasonNo;
        }

        /**
         * 設定 standardId 特性的值.
         *
         */
        public Integer getStANDARDID() {
            return standardid;
        }

        public void setSTANDARDID(Integer standardId) {
            this.standardid = standardId;
        }        

    }

}
