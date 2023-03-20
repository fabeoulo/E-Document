//
// 此檔案是由 JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 所產生 
// 請參閱 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 一旦重新編譯來源綱要, 對此檔案所做的任何修改都將會遺失. 
// 產生時間: 2017.12.13 於 11:28:01 AM CST 
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
 * <p>anonymous complex type 的 Java 類別.
 * 
 * <p>下列綱要片段會指定此類別中包含的預期內容.
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
 *         &lt;element name="MAT_PROPERTY">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="MAT_PROPERTY_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "matproperty",
    "extdept"
})
@XmlRootElement(name = "root")
public class MaterialPropertyQueryRoot {

    @XmlElement(name = "METHOD", required = true)
    protected MaterialPropertyQueryRoot.METHOD method;
    @XmlElement(name = "MAT_PROPERTY", required = true)
    protected MaterialPropertyQueryRoot.MATPROPERTY matproperty;
    @XmlElement(name = "EXT_DEPT", required = true)
    protected String extdept = "PD03";
    
    public MaterialPropertyQueryRoot(){
        this.method = new MaterialPropertyQueryRoot.METHOD();
        this.matproperty = new MaterialPropertyQueryRoot.MATPROPERTY();
    }
    
    public MaterialPropertyQueryRoot(String matPropNo){
        this.method = new MaterialPropertyQueryRoot.METHOD();
        this.matproperty = new MaterialPropertyQueryRoot.MATPROPERTY(matPropNo);
    }

    /**
     * 取得 method 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Root.METHOD }
     *     
     */
    public MaterialPropertyQueryRoot.METHOD getMETHOD() {
        return method;
    }

    /**
     * 設定 method 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Root.METHOD }
     *     
     */
    public void setMETHOD(MaterialPropertyQueryRoot.METHOD value) {
        this.method = value;
    }

    /**
     * 取得 matproperty 特性的值.
     * 
     * @return
     *     possible object is
     *     {@link Root.MATPROPERTY }
     *     
     */
    public MaterialPropertyQueryRoot.MATPROPERTY getMATPROPERTY() {
        return matproperty;
    }

    /**
     * 設定 matproperty 特性的值.
     * 
     * @param value
     *     allowed object is
     *     {@link Root.MATPROPERTY }
     *     
     */
    public void setMATPROPERTY(MaterialPropertyQueryRoot.MATPROPERTY value) {
        this.matproperty = value;
    }

    public String getExtdept() {
        return extdept;
    }

    public void setExtdept(String extdept) {
        this.extdept = extdept;
    }


    /**
     * <p>anonymous complex type 的 Java 類別.
     * 
     * <p>下列綱要片段會指定此類別中包含的預期內容.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="MAT_PROPERTY_NO" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "matpropertyno"
    })
    public static class MATPROPERTY {

        @XmlElement(name = "MAT_PROPERTY_NO", required = true)
        protected String matpropertyno;
        
        public MATPROPERTY(){
        
        }
        
        public MATPROPERTY(String matpropertyno){
            this.matpropertyno = matpropertyno;
        }

        /**
         * 取得 matpropertyno 特性的值.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMATPROPERTYNO() {
            return matpropertyno;
        }

        /**
         * 設定 matpropertyno 特性的值.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMATPROPERTYNO(String value) {
            this.matpropertyno = value;
        }

    }


    /**
     * <p>anonymous complex type 的 Java 類別.
     * 
     * <p>下列綱要片段會指定此類別中包含的預期內容.
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
        protected String id = "Advantech.SFC.MAM.BLL.QryMatProperty";

        /**
         * 取得 value 特性的值.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * 設定 value 特性的值.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * 取得 id 特性的值.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getID() {
            return id;
        }

        /**
         * 設定 id 特性的值.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setID(String value) {
            this.id = value;
        }

    }

}
