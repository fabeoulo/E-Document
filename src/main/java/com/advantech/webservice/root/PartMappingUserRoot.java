//
// 此檔案是由 JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 所產生 
// 請參閱 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 一旦重新編譯來源綱要, 對此檔案所做的任何修改都將會遺失. 
// 產生時間: 2017.11.01 於 05:23:26 PM CST 
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
 *         &lt;element name="PART_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="USER_IDs" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "users",
    "extdep"
})
@XmlRootElement(name = "root")
public class PartMappingUserRoot {

    @XmlElement(name = "METHOD", required = true)
    protected PartMappingUserRoot.METHOD method;
    @XmlElement(name = "TxPart_Mapping_User", required = true)
    protected PartMappingUserRoot.PartMappingUsers users;
    @XmlElement(name = "EXT_DEPT", required = true)
    protected String extdep = "PD03";

    public PartMappingUserRoot() {
        this.method = new PartMappingUserRoot.METHOD();
        this.users = new PartMappingUserRoot.PartMappingUsers();
    }

    /**
     * 取得 method 特性的值.
     *
     * @return possible object is {@link Root.METHOD }
     *
     */
    public PartMappingUserRoot.METHOD getMETHOD() {
        return method;
    }

    /**
     * 設定 method 特性的值.
     *
     * @param value allowed object is {@link Root.METHOD }
     *
     */
    public void setMETHOD(PartMappingUserRoot.METHOD value) {
        this.method = value;
    }

    public PartMappingUsers getUsers() {
        return users;
    }

    public void setUsers(PartMappingUsers users) {
        this.users = users;
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
        protected String id = "Advantech.QAM.PQE.BLL.TxPart_Mapping_User";

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

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "useriDs",
        "partno",
        "type"
    })
    public static class PartMappingUsers {

        @XmlElement(name = "PART_NO", required = true, nillable = true)
        protected String partno;
        @XmlElement(name = "USER_IDs", required = true, nillable = true)
        protected String useriDs;
        @XmlElement(name = "TYPE", required = true, nillable = true)
        protected String type;

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
         * 取得 useriDs 特性的值.
         *
         * @return possible object is {@link String }
         *
         */
        public String getUSERIDs() {
            return useriDs;
        }

        /**
         * 設定 useriDs 特性的值.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setUSERIDs(String value) {
            this.useriDs = value;
        }

        /**
         * 取得 type 特性的值.
         *
         * @return possible object is {@link String }
         *
         */
        public String getTYPE() {
            return type;
        }

        /**
         * 設定 type 特性的值.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setTYPE(String value) {
            this.type = value;
        }
    }
}
