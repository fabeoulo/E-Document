//
// 此檔案是由 JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 所產生 
// 請參閱 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 一旦重新編譯來源綱要, 對此檔案所做的任何修改都將會遺失. 
// 產生時間: 2017.09.04 於 11:28:54 AM CST 
//
package com.advantech.webservice.root;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

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
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="SOP_INFO">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PART_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="TYPE_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ITEM_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="SOP_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="STATION_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="SOP_PAGE_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="TYPE_NO_OLD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ITEM_NO_OLD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="SOP_NAME_OLD" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "sopinfo",
    "extdep"
})
@XmlRootElement(name = "root")
public class SopRoot {

    @XmlElement(name = "METHOD", required = true)
    protected SopRoot.METHOD method;
    @XmlElement(name = "SOP_INFO", required = true)
    protected SopRoot.SOPINFO sopinfo;
    @XmlElement(name = "EXT_DEPT", required = true)
    protected String extdep = "PD03";

    public SopRoot() {
        this.method = new SopRoot.METHOD();
        this.sopinfo = new SopRoot.SOPINFO();
    }

    /**
     * 取得 method 特性的值.
     *
     * @return possible object is {@link Root.METHOD }
     *
     */
    public SopRoot.METHOD getMETHOD() {
        return method;
    }

    /**
     * 設定 method 特性的值.
     *
     * @param value allowed object is {@link Root.METHOD }
     *
     */
    public void setMETHOD(SopRoot.METHOD value) {
        this.method = value;
    }

    /**
     * 取得 sopinfo 特性的值.
     *
     * @return possible object is {@link Root.SOPINFO }
     *
     */
    public SopRoot.SOPINFO getSOPINFO() {
        return sopinfo;
    }

    /**
     * 設定 sopinfo 特性的值.
     *
     * @param value allowed object is {@link Root.SOPINFO }
     *
     */
    public void setSOPINFO(SopRoot.SOPINFO value) {
        this.sopinfo = value;
    }

    public String getExtdep() {
        return extdep;
    }

    public void setExtdep(String extdep) {
        this.extdep = extdep;
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
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class METHOD {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "ID")
        protected String id = "Advantech.SFC.PRA.BLL.TxSopInfo001";

        /**
         * 取得 value 特性的值.
         *
         * @return possible object is {@link String }
         *
         */
        public String getValue() {
            return value;
        }

        /**
         * 設定 value 特性的值.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setValue(String value) {
            this.value = value;
        }

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
     *         &lt;element name="PART_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="TYPE_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ITEM_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="SOP_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="STATION_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="SOP_PAGE_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="TYPE_NO_OLD" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ITEM_NO_OLD" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="SOP_NAME_OLD" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "partno",
        "typeno",
        "itemno",
        "sopname",
        "stationno",
        "soppageno",
        "typenoold",
        "itemnoold",
        "sopnameold"
    })
    public static class SOPINFO {

        @XmlElement(name = "PART_NO", required = true, nillable = true)
        protected String partno;
        @XmlElement(name = "TYPE_NO", required = true, nillable = true)
        protected String typeno;
        @XmlElement(name = "ITEM_NO", required = true, nillable = true)
        protected String itemno;
        @XmlElement(name = "SOP_NAME", required = true, nillable = true)
        protected String sopname;
        @XmlElement(name = "STATION_NO", required = true, nillable = true)
        protected String stationno;
        @XmlElement(name = "SOP_PAGE_NO", required = true, nillable = true)
        protected String soppageno;
        @XmlElement(name = "TYPE_NO_OLD", required = true, nillable = true)
        protected String typenoold;
        @XmlElement(name = "ITEM_NO_OLD", required = true, nillable = true)
        protected String itemnoold;
        @XmlElement(name = "SOP_NAME_OLD", required = true, nillable = true)
        protected String sopnameold;

        /**
         * 取得 partno 特性的值.
         *
         * @return possible object is {@link String }
         *
         */
        public String getPARTNO() {
            return partno;
        }

        /**
         * 設定 partno 特性的值.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setPARTNO(String value) {
            this.partno = value;
        }

        /**
         * 取得 typeno 特性的值.
         *
         * @return possible object is {@link String }
         *
         */
        public String getTYPENO() {
            return typeno;
        }

        /**
         * 設定 typeno 特性的值.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setTYPENO(String value) {
            this.typeno = value;
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
         * 取得 sopname 特性的值.
         *
         * @return possible object is {@link String }
         *
         */
        public String getSOPNAME() {
            return sopname;
        }

        /**
         * 設定 sopname 特性的值.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setSOPNAME(String value) {
            this.sopname = value;
        }

        /**
         * 取得 stationno 特性的值.
         *
         * @return possible object is {@link String }
         *
         */
        public String getSTATIONNO() {
            return stationno;
        }

        /**
         * 設定 stationno 特性的值.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setSTATIONNO(String value) {
            this.stationno = value;
        }

        /**
         * 取得 soppageno 特性的值.
         *
         * @return possible object is {@link String }
         *
         */
        public String getSOPPAGENO() {
            return soppageno;
        }

        /**
         * 設定 soppageno 特性的值.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setSOPPAGENO(String value) {
            this.soppageno = value;
        }

        /**
         * 取得 typenoold 特性的值.
         *
         * @return possible object is {@link String }
         *
         */
        public String getTYPENOOLD() {
            return typenoold;
        }

        /**
         * 設定 typenoold 特性的值.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setTYPENOOLD(String value) {
            this.typenoold = value;
        }

        /**
         * 取得 itemnoold 特性的值.
         *
         * @return possible object is {@link String }
         *
         */
        public String getITEMNOOLD() {
            return itemnoold;
        }

        /**
         * 設定 itemnoold 特性的值.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setITEMNOOLD(String value) {
            this.itemnoold = value;
        }

        /**
         * 取得 sopnameold 特性的值.
         *
         * @return possible object is {@link String }
         *
         */
        public String getSOPNAMEOLD() {
            return sopnameold;
        }

        /**
         * 設定 sopnameold 特性的值.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setSOPNAMEOLD(String value) {
            this.sopnameold = value;
        }

    }

}
