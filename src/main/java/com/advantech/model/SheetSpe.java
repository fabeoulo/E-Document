package com.advantech.model;
// Generated 2017/3/15 上午 09:14:05 by Hibernate Tools 4.3.1

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SheetSpe generated by hbm2java
 */
@Entity
@Table(name = "Sheet_SPE",
        schema = "dbo",
        catalog = "E_Document"
)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SheetSpe implements java.io.Serializable {

    private int id;
    private Floor floor;
    private Model model;
    private Type type;
    private BigDecimal cleanPanel;
    private BigDecimal assy;
    private BigDecimal packing;
    private BigDecimal biCost;
    private Integer vibration;
    private Integer hiPotLeakage;
    private BigDecimal coldBoot;
    private BigDecimal warmBoot;
    private BigDecimal assyToT1;
    private BigDecimal t2ToPacking;
    private String pending;
    private BigDecimal pendingTime;
    private String burnIn;
    private BigDecimal biTime;
    private BigDecimal biTemperature;
    private Integer speOwnerId;
    private Integer eeOwnerId;
    private Integer qcOwnerId;
    private String assyPackingSop;
    private Integer keypartA;
    private Integer keypartB;
    private String preAssy;
    private String babFlow;
    private String testFlow;
    private String packingFlow;
    private String partLink;
    private String nIn1CollectionBox;
    private String partNoAttrMaintain;
    private Date modifiedDate;

    @JsonIgnore
    private Set<LabelInfo> labelInfos = new HashSet<LabelInfo>(0);

    public SheetSpe() {
    }

    public SheetSpe(int id) {
        this.id = id;
    }

    public SheetSpe(int id, Floor floor, Model model, Type type, BigDecimal cleanPanel, BigDecimal assy, BigDecimal packing, BigDecimal biCost, Integer vibration, Integer hiPotLeakage, BigDecimal coldBoot, BigDecimal warmBoot, BigDecimal assyToT1, BigDecimal t2ToPacking, String pending, BigDecimal pendingTime, String burnIn, BigDecimal biTime, BigDecimal biTemperature, Integer speOwnerId, Integer eeOwnerId, Integer qcOwnerId, String assyPackingSop, Integer keypartA, Integer keypartB, String preAssy, String babFlow, String testFlow, String packingFlow, String partLink, String nIn1CollectionBox, String partNoAttrMaintain, Date modifiedDate, Set<LabelInfo> labelInfos) {
        this.id = id;
        this.floor = floor;
        this.model = model;
        this.type = type;
        this.cleanPanel = cleanPanel;
        this.assy = assy;
        this.packing = packing;
        this.biCost = biCost;
        this.vibration = vibration;
        this.hiPotLeakage = hiPotLeakage;
        this.coldBoot = coldBoot;
        this.warmBoot = warmBoot;
        this.assyToT1 = assyToT1;
        this.t2ToPacking = t2ToPacking;
        this.pending = pending;
        this.pendingTime = pendingTime;
        this.burnIn = burnIn;
        this.biTime = biTime;
        this.biTemperature = biTemperature;
        this.speOwnerId = speOwnerId;
        this.eeOwnerId = eeOwnerId;
        this.qcOwnerId = qcOwnerId;
        this.assyPackingSop = assyPackingSop;
        this.keypartA = keypartA;
        this.keypartB = keypartB;
        this.preAssy = preAssy;
        this.babFlow = babFlow;
        this.testFlow = testFlow;
        this.packingFlow = packingFlow;
        this.partLink = partLink;
        this.nIn1CollectionBox = nIn1CollectionBox;
        this.partNoAttrMaintain = partNoAttrMaintain;
        this.modifiedDate = modifiedDate;
        this.labelInfos = labelInfos;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Floor_id")
    public Floor getFloor() {
        return this.floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Model_id")
    public Model getModel() {
        return this.model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Type_id")
    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Column(name = "CleanPanel", precision = 10, scale = 1)
    public BigDecimal getCleanPanel() {
        return this.cleanPanel;
    }

    public void setCleanPanel(BigDecimal cleanPanel) {
        this.cleanPanel = cleanPanel;
    }

    @Column(name = "ASSY", precision = 10, scale = 1)
    public BigDecimal getAssy() {
        return this.assy;
    }

    public void setAssy(BigDecimal assy) {
        this.assy = assy;
    }

    @Column(name = "Packing", precision = 10, scale = 1)
    public BigDecimal getPacking() {
        return this.packing;
    }

    public void setPacking(BigDecimal packing) {
        this.packing = packing;
    }

    @Column(name = "BI_Cost", precision = 10, scale = 1)
    public BigDecimal getBiCost() {
        return this.biCost;
    }

    public void setBiCost(BigDecimal biCost) {
        this.biCost = biCost;
    }

    @Column(name = "Vibration")
    public Integer getVibration() {
        return this.vibration;
    }

    public void setVibration(Integer vibration) {
        this.vibration = vibration;
    }

    @Column(name = "Hi_Pot_Leakage")
    public Integer getHiPotLeakage() {
        return this.hiPotLeakage;
    }

    public void setHiPotLeakage(Integer hiPotLeakage) {
        this.hiPotLeakage = hiPotLeakage;
    }

    @Column(name = "Cold_Boot", precision = 10, scale = 1)
    public BigDecimal getColdBoot() {
        return this.coldBoot;
    }

    public void setColdBoot(BigDecimal coldBoot) {
        this.coldBoot = coldBoot;
    }

    @Column(name = "Warm_Boot", precision = 10, scale = 1)
    public BigDecimal getWarmBoot() {
        return this.warmBoot;
    }

    public void setWarmBoot(BigDecimal warmBoot) {
        this.warmBoot = warmBoot;
    }

    @Column(name = "ASSY_to_T1", precision = 10, scale = 1)
    public BigDecimal getAssyToT1() {
        return this.assyToT1;
    }

    public void setAssyToT1(BigDecimal assyToT1) {
        this.assyToT1 = assyToT1;
    }

    @Column(name = "T2_to_Packing", precision = 10, scale = 1)
    public BigDecimal getT2ToPacking() {
        return this.t2ToPacking;
    }

    public void setT2ToPacking(BigDecimal t2ToPacking) {
        this.t2ToPacking = t2ToPacking;
    }

    @Column(name = "Pending")
    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }

    @Column(name = "Pending_Time", precision = 10, scale = 1)
    public BigDecimal getPendingTime() {
        return pendingTime;
    }

    public void setPendingTime(BigDecimal pendingTime) {
        this.pendingTime = pendingTime;
    }
    
    

    @Column(name = "BurnIn", length = 10)
    public String getBurnIn() {
        return this.burnIn;
    }

    public void setBurnIn(String burnIn) {
        this.burnIn = burnIn;
    }

    @Column(name = "BI_Time", precision = 10, scale = 1)
    public BigDecimal getBiTime() {
        return this.biTime;
    }

    public void setBiTime(BigDecimal biTime) {
        this.biTime = biTime;
    }

    @Column(name = "BI_Temperature", precision = 10, scale = 1)
    public BigDecimal getBiTemperature() {
        return this.biTemperature;
    }

    public void setBiTemperature(BigDecimal biTemperature) {
        this.biTemperature = biTemperature;
    }

    @Column(name = "SPE_owner_id")
    public Integer getSpeOwnerId() {
        return this.speOwnerId;
    }

    public void setSpeOwnerId(Integer speOwnerId) {
        this.speOwnerId = speOwnerId;
    }

    @Column(name = "EE_owner_id")
    public Integer getEeOwnerId() {
        return this.eeOwnerId;
    }

    public void setEeOwnerId(Integer eeOwnerId) {
        this.eeOwnerId = eeOwnerId;
    }

    @Column(name = "QC_owner_id")
    public Integer getQcOwnerId() {
        return this.qcOwnerId;
    }

    public void setQcOwnerId(Integer qcOwnerId) {
        this.qcOwnerId = qcOwnerId;
    }

    @Column(name = "ASSY_Packing_SOP", length = 500)
    public String getAssyPackingSop() {
        return this.assyPackingSop;
    }

    public void setAssyPackingSop(String assyPackingSop) {
        this.assyPackingSop = assyPackingSop;
    }

    @Column(name = "Keypart_A")
    public Integer getKeypartA() {
        return this.keypartA;
    }

    public void setKeypartA(Integer keypartA) {
        this.keypartA = keypartA;
    }

    @Column(name = "Keypart_B")
    public Integer getKeypartB() {
        return this.keypartB;
    }

    public void setKeypartB(Integer keypartB) {
        this.keypartB = keypartB;
    }

    @Column(name = "Pre_ASSY", length = 50)
    public String getPreAssy() {
        return this.preAssy;
    }

    public void setPreAssy(String preAssy) {
        this.preAssy = preAssy;
    }

    @Column(name = "BAB_Flow", length = 100)
    public String getBabFlow() {
        return this.babFlow;
    }

    public void setBabFlow(String babFlow) {
        this.babFlow = babFlow;
    }

    @Column(name = "Test_Flow", length = 100)
    public String getTestFlow() {
        return this.testFlow;
    }

    public void setTestFlow(String testFlow) {
        this.testFlow = testFlow;
    }

    @Column(name = "Packing_Flow", length = 50)
    public String getPackingFlow() {
        return this.packingFlow;
    }

    public void setPackingFlow(String packingFlow) {
        this.packingFlow = packingFlow;
    }

    @Column(name = "Part_Link", length = 50)
    public String getPartLink() {
        return this.partLink;
    }

    public void setPartLink(String partLink) {
        this.partLink = partLink;
    }

    @Column(name = "N_In_1_collection_box", length = 50)
    public String getnIn1CollectionBox() {
        return nIn1CollectionBox;
    }

    public void setnIn1CollectionBox(String nIn1CollectionBox) {
        this.nIn1CollectionBox = nIn1CollectionBox;
    }

    @Column(name = "PartNo_attr_maintain", length = 50)
    public String getPartNoAttrMaintain() {
        return this.partNoAttrMaintain;
    }

    public void setPartNoAttrMaintain(String partNoAttrMaintain) {
        this.partNoAttrMaintain = partNoAttrMaintain;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Modified_Date", length = 23, updatable = false)
    public Date getModifiedDate() {
        return this.modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sheetSpe")
    public Set<LabelInfo> getLabelInfos() {
        return this.labelInfos;
    }

    public void setLabelInfos(Set<LabelInfo> labelInfos) {
        this.labelInfos = labelInfos;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.floor);
        hash = 97 * hash + Objects.hashCode(this.model);
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + Objects.hashCode(this.cleanPanel);
        hash = 97 * hash + Objects.hashCode(this.assy);
        hash = 97 * hash + Objects.hashCode(this.packing);
        hash = 97 * hash + Objects.hashCode(this.biCost);
        hash = 97 * hash + Objects.hashCode(this.vibration);
        hash = 97 * hash + Objects.hashCode(this.hiPotLeakage);
        hash = 97 * hash + Objects.hashCode(this.coldBoot);
        hash = 97 * hash + Objects.hashCode(this.warmBoot);
        hash = 97 * hash + Objects.hashCode(this.assyToT1);
        hash = 97 * hash + Objects.hashCode(this.t2ToPacking);
        hash = 97 * hash + Objects.hashCode(this.burnIn);
        hash = 97 * hash + Objects.hashCode(this.biTime);
        hash = 97 * hash + Objects.hashCode(this.biTemperature);
        hash = 97 * hash + Objects.hashCode(this.speOwnerId);
        hash = 97 * hash + Objects.hashCode(this.eeOwnerId);
        hash = 97 * hash + Objects.hashCode(this.qcOwnerId);
        hash = 97 * hash + Objects.hashCode(this.assyPackingSop);
        hash = 97 * hash + Objects.hashCode(this.keypartA);
        hash = 97 * hash + Objects.hashCode(this.keypartB);
        hash = 97 * hash + Objects.hashCode(this.preAssy);
        hash = 97 * hash + Objects.hashCode(this.babFlow);
        hash = 97 * hash + Objects.hashCode(this.testFlow);
        hash = 97 * hash + Objects.hashCode(this.packingFlow);
        hash = 97 * hash + Objects.hashCode(this.partLink);
        hash = 97 * hash + Objects.hashCode(this.nIn1CollectionBox);
        hash = 97 * hash + Objects.hashCode(this.partNoAttrMaintain);
        hash = 97 * hash + Objects.hashCode(this.modifiedDate);
        hash = 97 * hash + Objects.hashCode(this.labelInfos);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SheetSpe other = (SheetSpe) obj;
        if (!Objects.equals(this.burnIn, other.burnIn)) {
            return false;
        }
        if (!Objects.equals(this.assyPackingSop, other.assyPackingSop)) {
            return false;
        }
        if (!Objects.equals(this.preAssy, other.preAssy)) {
            return false;
        }
        if (!Objects.equals(this.babFlow, other.babFlow)) {
            return false;
        }
        if (!Objects.equals(this.testFlow, other.testFlow)) {
            return false;
        }
        if (!Objects.equals(this.packingFlow, other.packingFlow)) {
            return false;
        }
        if (!Objects.equals(this.partLink, other.partLink)) {
            return false;
        }
        if (!Objects.equals(this.nIn1CollectionBox, other.nIn1CollectionBox)) {
            return false;
        }
        if (!Objects.equals(this.partNoAttrMaintain, other.partNoAttrMaintain)) {
            return false;
        }
        if (!Objects.equals(this.floor, other.floor)) {
            return false;
        }
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.cleanPanel, other.cleanPanel)) {
            return false;
        }
        if (!Objects.equals(this.assy, other.assy)) {
            return false;
        }
        if (!Objects.equals(this.packing, other.packing)) {
            return false;
        }
        if (!Objects.equals(this.biCost, other.biCost)) {
            return false;
        }
        if (!Objects.equals(this.vibration, other.vibration)) {
            return false;
        }
        if (!Objects.equals(this.hiPotLeakage, other.hiPotLeakage)) {
            return false;
        }
        if (!Objects.equals(this.coldBoot, other.coldBoot)) {
            return false;
        }
        if (!Objects.equals(this.warmBoot, other.warmBoot)) {
            return false;
        }
        if (!Objects.equals(this.assyToT1, other.assyToT1)) {
            return false;
        }
        if (!Objects.equals(this.t2ToPacking, other.t2ToPacking)) {
            return false;
        }
        if (!Objects.equals(this.biTime, other.biTime)) {
            return false;
        }
        if (!Objects.equals(this.biTemperature, other.biTemperature)) {
            return false;
        }
        if (!Objects.equals(this.speOwnerId, other.speOwnerId)) {
            return false;
        }
        if (!Objects.equals(this.eeOwnerId, other.eeOwnerId)) {
            return false;
        }
        if (!Objects.equals(this.qcOwnerId, other.qcOwnerId)) {
            return false;
        }
        if (!Objects.equals(this.keypartA, other.keypartA)) {
            return false;
        }
        if (!Objects.equals(this.keypartB, other.keypartB)) {
            return false;
        }
        if (!Objects.equals(this.modifiedDate, other.modifiedDate)) {
            return false;
        }
        if (!Objects.equals(this.labelInfos, other.labelInfos)) {
            return false;
        }
        return true;
    }

}
