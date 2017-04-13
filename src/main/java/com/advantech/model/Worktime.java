package com.advantech.model;
// Generated 2017/4/7 下午 02:26:06 by Hibernate Tools 4.3.1

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;
import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

/**
 * Worktime generated by hbm2java
 */
@Entity
@Table(name = "Worktime",
         schema = "dbo",
         catalog = "E_Document",
         uniqueConstraints = @UniqueConstraint(columnNames = "model_name")
)
@DynamicInsert(true)
@DynamicUpdate(true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Audited(targetAuditMode = NOT_AUDITED) 
public class Worktime implements java.io.Serializable {

    private int id;
    private Floor floor;
    private Flow flowByTestFlowId;
    private Flow flowByPackingFlowId;
    private Flow flowByBabFlowId;
    private Identit identitByEeOwnerId;
    private Identit identitByQcOwnerId;
    private Identit identitBySpeOwnerId;
    private Pending pending;
    private PreAssy preAssy;
    private Type type;
    private String modelName;
    private BigDecimal totalModule;
    private BigDecimal cleanPanel;
    private BigDecimal assy;
    private BigDecimal t1;
    private BigDecimal t2;
    private BigDecimal t3;
    private BigDecimal t4;
    private BigDecimal packing;
    private BigDecimal upBiRi;
    private BigDecimal downBiRi;
    private BigDecimal biCost;
    private Integer vibration;
    private Integer hiPotLeakage;
    private BigDecimal coldBoot;
    private BigDecimal warmBoot;
    private BigDecimal pendingTime;
    private String burnIn;
    private BigDecimal biTime;
    private BigDecimal biTemperature;
    private String assyPackingSop;
    private String testSop;
    private Integer keypartA;
    private Integer keypartB;
    private Character partLink;
    private int ce;
    private int ul;
    private int rohs;
    private int weee;
    private int madeInTaiwan;
    private int fcc;
    private int eac;
    private BigDecimal NInOneCollectionBox;
    private char partNoAttributeMaintain;
    private BigDecimal assyLeadTime;
    private BigDecimal packingLeadTime;
    private Date modifiedDate;
    private BigDecimal productionWt;
    private BigDecimal setupTime;
    private BigDecimal assyToT1;
    private BigDecimal t2ToPacking;
    private Integer assyStation;
    private Integer packingStation;
    private BigDecimal assyKanbanTime;
    private BigDecimal packingKanbanTime;
    private BigDecimal cleanPanelAndAssembly;

    public Worktime() {
    }

    public Worktime(int id, Floor floor, Identit identitByEeOwnerId, Identit identitByQcOwnerId, Identit identitBySpeOwnerId, Pending pending, Type type, String modelName, BigDecimal pendingTime, String burnIn, BigDecimal biTime, BigDecimal biTemperature, int ce, int ul, int rohs, int weee, int madeInTaiwan, int fcc, int eac, char partNoAttributeMaintain) {
        this.id = id;
        this.floor = floor;
        this.identitByEeOwnerId = identitByEeOwnerId;
        this.identitByQcOwnerId = identitByQcOwnerId;
        this.identitBySpeOwnerId = identitBySpeOwnerId;
        this.pending = pending;
        this.type = type;
        this.modelName = modelName;
        this.pendingTime = pendingTime;
        this.burnIn = burnIn;
        this.biTime = biTime;
        this.biTemperature = biTemperature;
        this.ce = ce;
        this.ul = ul;
        this.rohs = rohs;
        this.weee = weee;
        this.madeInTaiwan = madeInTaiwan;
        this.fcc = fcc;
        this.eac = eac;
        this.partNoAttributeMaintain = partNoAttributeMaintain;
    }

    public Worktime(int id, Floor floor, Flow flowByTestFlowId, Flow flowByPackingFlowId, Flow flowByBabFlowId, Identit identitByEeOwnerId, Identit identitByQcOwnerId, Identit identitBySpeOwnerId, Pending pending, PreAssy preAssy, Type type, String modelName, BigDecimal totalModule, BigDecimal cleanPanel, BigDecimal assy, BigDecimal t1, BigDecimal t2, BigDecimal t3, BigDecimal t4, BigDecimal packing, BigDecimal upBiRi, BigDecimal downBiRi, BigDecimal biCost, Integer vibration, Integer hiPotLeakage, BigDecimal coldBoot, BigDecimal warmBoot, BigDecimal pendingTime, String burnIn, BigDecimal biTime, BigDecimal biTemperature, String assyPackingSop, String testSop, Integer keypartA, Integer keypartB, Character partLink, int ce, int ul, int rohs, int weee, int madeInTaiwan, int fcc, int eac, BigDecimal NInOneCollectionBox, char partNoAttributeMaintain, BigDecimal assyLeadTime, BigDecimal packingLeadTime, Date modifiedDate) {
        this.id = id;
        this.floor = floor;
        this.flowByTestFlowId = flowByTestFlowId;
        this.flowByPackingFlowId = flowByPackingFlowId;
        this.flowByBabFlowId = flowByBabFlowId;
        this.identitByEeOwnerId = identitByEeOwnerId;
        this.identitByQcOwnerId = identitByQcOwnerId;
        this.identitBySpeOwnerId = identitBySpeOwnerId;
        this.pending = pending;
        this.preAssy = preAssy;
        this.type = type;
        this.modelName = modelName;
        this.totalModule = totalModule;
        this.cleanPanel = cleanPanel;
        this.assy = assy;
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.packing = packing;
        this.upBiRi = upBiRi;
        this.downBiRi = downBiRi;
        this.biCost = biCost;
        this.vibration = vibration;
        this.hiPotLeakage = hiPotLeakage;
        this.coldBoot = coldBoot;
        this.warmBoot = warmBoot;
        this.pendingTime = pendingTime;
        this.burnIn = burnIn;
        this.biTime = biTime;
        this.biTemperature = biTemperature;
        this.assyPackingSop = assyPackingSop;
        this.testSop = testSop;
        this.keypartA = keypartA;
        this.keypartB = keypartB;
        this.partLink = partLink;
        this.ce = ce;
        this.ul = ul;
        this.rohs = rohs;
        this.weee = weee;
        this.madeInTaiwan = madeInTaiwan;
        this.fcc = fcc;
        this.eac = eac;
        this.NInOneCollectionBox = NInOneCollectionBox;
        this.partNoAttributeMaintain = partNoAttributeMaintain;
        this.assyLeadTime = assyLeadTime;
        this.packingLeadTime = packingLeadTime;
        this.modifiedDate = modifiedDate;
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
    @JoinColumn(name = "floor_id", nullable = false)
    public Floor getFloor() {
        return this.floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_flow_id")
    public Flow getFlowByTestFlowId() {
        return this.flowByTestFlowId;
    }

    public void setFlowByTestFlowId(Flow flowByTestFlowId) {
        this.flowByTestFlowId = flowByTestFlowId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packing_flow_id")
    public Flow getFlowByPackingFlowId() {
        return this.flowByPackingFlowId;
    }

    public void setFlowByPackingFlowId(Flow flowByPackingFlowId) {
        this.flowByPackingFlowId = flowByPackingFlowId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bab_flow_id")
    public Flow getFlowByBabFlowId() {
        return this.flowByBabFlowId;
    }

    public void setFlowByBabFlowId(Flow flowByBabFlowId) {
        this.flowByBabFlowId = flowByBabFlowId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ee_owner_id", nullable = false)
    public Identit getIdentitByEeOwnerId() {
        return this.identitByEeOwnerId;
    }

    public void setIdentitByEeOwnerId(Identit identitByEeOwnerId) {
        this.identitByEeOwnerId = identitByEeOwnerId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qc_owner_id", nullable = false)
    public Identit getIdentitByQcOwnerId() {
        return this.identitByQcOwnerId;
    }

    public void setIdentitByQcOwnerId(Identit identitByQcOwnerId) {
        this.identitByQcOwnerId = identitByQcOwnerId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spe_owner_id", nullable = false)
    public Identit getIdentitBySpeOwnerId() {
        return this.identitBySpeOwnerId;
    }

    public void setIdentitBySpeOwnerId(Identit identitBySpeOwnerId) {
        this.identitBySpeOwnerId = identitBySpeOwnerId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pending_id", nullable = false)
    public Pending getPending() {
        return this.pending;
    }

    public void setPending(Pending pending) {
        this.pending = pending;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pre_assy_id")
    public PreAssy getPreAssy() {
        return this.preAssy;
    }

    public void setPreAssy(PreAssy preAssy) {
        this.preAssy = preAssy;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Column(name = "model_name", unique = true, nullable = false, length = 50)
    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Column(name = "total_module", precision = 10, scale = 1)
    public BigDecimal getTotalModule() {
        return this.totalModule;
    }

    public void setTotalModule(BigDecimal totalModule) {
        this.totalModule = totalModule;
    }

    @Column(name = "clean_panel", precision = 10, scale = 1)
    public BigDecimal getCleanPanel() {
        return this.cleanPanel;
    }

    public void setCleanPanel(BigDecimal cleanPanel) {
        this.cleanPanel = cleanPanel;
    }

    @Column(name = "assy", precision = 10, scale = 1)
    public BigDecimal getAssy() {
        return this.assy;
    }

    public void setAssy(BigDecimal assy) {
        this.assy = assy;
    }

    @Column(name = "t1", precision = 10, scale = 1)
    public BigDecimal getT1() {
        return this.t1;
    }

    public void setT1(BigDecimal t1) {
        this.t1 = t1;
    }

    @Column(name = "t2", precision = 10, scale = 1)
    public BigDecimal getT2() {
        return this.t2;
    }

    public void setT2(BigDecimal t2) {
        this.t2 = t2;
    }

    @Column(name = "t3", precision = 10, scale = 1)
    public BigDecimal getT3() {
        return this.t3;
    }

    public void setT3(BigDecimal t3) {
        this.t3 = t3;
    }

    @Column(name = "t4", precision = 10, scale = 1)
    public BigDecimal getT4() {
        return this.t4;
    }

    public void setT4(BigDecimal t4) {
        this.t4 = t4;
    }

    @Column(name = "packing", precision = 10, scale = 1)
    public BigDecimal getPacking() {
        return this.packing;
    }

    public void setPacking(BigDecimal packing) {
        this.packing = packing;
    }

    @Column(name = "up_bi_ri", precision = 10, scale = 1)
    public BigDecimal getUpBiRi() {
        return this.upBiRi;
    }

    public void setUpBiRi(BigDecimal upBiRi) {
        this.upBiRi = upBiRi;
    }

    @Column(name = "down_bi_ri", precision = 10, scale = 1)
    public BigDecimal getDownBiRi() {
        return this.downBiRi;
    }

    public void setDownBiRi(BigDecimal downBiRi) {
        this.downBiRi = downBiRi;
    }

    @Column(name = "bi_cost", precision = 10, scale = 1)
    public BigDecimal getBiCost() {
        return this.biCost;
    }

    public void setBiCost(BigDecimal biCost) {
        this.biCost = biCost;
    }

    @Column(name = "vibration")
    public Integer getVibration() {
        return this.vibration;
    }

    public void setVibration(Integer vibration) {
        this.vibration = vibration;
    }

    @Column(name = "hi_pot_leakage")
    public Integer getHiPotLeakage() {
        return this.hiPotLeakage;
    }

    public void setHiPotLeakage(Integer hiPotLeakage) {
        this.hiPotLeakage = hiPotLeakage;
    }

    @Column(name = "cold_boot", precision = 10, scale = 1)
    public BigDecimal getColdBoot() {
        return this.coldBoot;
    }

    public void setColdBoot(BigDecimal coldBoot) {
        this.coldBoot = coldBoot;
    }

    @Column(name = "warm_boot", precision = 10, scale = 1)
    public BigDecimal getWarmBoot() {
        return this.warmBoot;
    }

    public void setWarmBoot(BigDecimal warmBoot) {
        this.warmBoot = warmBoot;
    }

    @Column(name = "pending_time", nullable = false, precision = 10, scale = 1)
    public BigDecimal getPendingTime() {
        return this.pendingTime;
    }

    public void setPendingTime(BigDecimal pendingTime) {
        this.pendingTime = pendingTime;
    }

    @Column(name = "burn_in", nullable = false, length = 10)
    public String getBurnIn() {
        return this.burnIn;
    }

    public void setBurnIn(String burnIn) {
        this.burnIn = burnIn;
    }

    @Column(name = "bi_time", nullable = false, precision = 10, scale = 1)
    public BigDecimal getBiTime() {
        return this.biTime;
    }

    public void setBiTime(BigDecimal biTime) {
        this.biTime = biTime;
    }

    @Column(name = "bi_temperature", nullable = false, precision = 10, scale = 1)
    public BigDecimal getBiTemperature() {
        return this.biTemperature;
    }

    public void setBiTemperature(BigDecimal biTemperature) {
        this.biTemperature = biTemperature;
    }

    @Column(name = "assy_packing_sop", length = 500)
    public String getAssyPackingSop() {
        return this.assyPackingSop;
    }

    public void setAssyPackingSop(String assyPackingSop) {
        this.assyPackingSop = assyPackingSop;
    }

    @Column(name = "test_sop", length = 500)
    public String getTestSop() {
        return this.testSop;
    }

    public void setTestSop(String testSop) {
        this.testSop = testSop;
    }

    @Column(name = "keypart_a")
    public Integer getKeypartA() {
        return this.keypartA;
    }

    public void setKeypartA(Integer keypartA) {
        this.keypartA = keypartA;
    }

    @Column(name = "keypart_b")
    public Integer getKeypartB() {
        return this.keypartB;
    }

    public void setKeypartB(Integer keypartB) {
        this.keypartB = keypartB;
    }

    @Column(name = "part_link", length = 1)
    public Character getPartLink() {
        return this.partLink;
    }

    public void setPartLink(Character partLink) {
        this.partLink = partLink;
    }

    @Column(name = "ce", nullable = false)
    public int getCe() {
        return this.ce;
    }

    public void setCe(int ce) {
        this.ce = ce;
    }

    @Column(name = "ul", nullable = false)
    public int getUl() {
        return this.ul;
    }

    public void setUl(int ul) {
        this.ul = ul;
    }

    @Column(name = "rohs", nullable = false)
    public int getRohs() {
        return this.rohs;
    }

    public void setRohs(int rohs) {
        this.rohs = rohs;
    }

    @Column(name = "weee", nullable = false)
    public int getWeee() {
        return this.weee;
    }

    public void setWeee(int weee) {
        this.weee = weee;
    }

    @Column(name = "made_in_taiwan", nullable = false)
    public int getMadeInTaiwan() {
        return this.madeInTaiwan;
    }

    public void setMadeInTaiwan(int madeInTaiwan) {
        this.madeInTaiwan = madeInTaiwan;
    }

    @Column(name = "fcc", nullable = false)
    public int getFcc() {
        return this.fcc;
    }

    public void setFcc(int fcc) {
        this.fcc = fcc;
    }

    @Column(name = "eac", nullable = false)
    public int getEac() {
        return this.eac;
    }

    public void setEac(int eac) {
        this.eac = eac;
    }

    @Column(name = "n_in_one_collection_box", precision = 10, scale = 1)
    public BigDecimal getNInOneCollectionBox() {
        return this.NInOneCollectionBox;
    }

    public void setNInOneCollectionBox(BigDecimal NInOneCollectionBox) {
        this.NInOneCollectionBox = NInOneCollectionBox;
    }

    @Column(name = "part_no_attribute_maintain", nullable = false, length = 1)
    public char getPartNoAttributeMaintain() {
        return this.partNoAttributeMaintain;
    }

    public void setPartNoAttributeMaintain(char partNoAttributeMaintain) {
        this.partNoAttributeMaintain = partNoAttributeMaintain;
    }

    @Column(name = "assy_lead_time", precision = 10, scale = 1)
    public BigDecimal getAssyLeadTime() {
        return this.assyLeadTime;
    }

    public void setAssyLeadTime(BigDecimal assyLeadTime) {
        this.assyLeadTime = assyLeadTime;
    }

    @Column(name = "packing_lead_time", precision = 10, scale = 1)
    public BigDecimal getPackingLeadTime() {
        return this.packingLeadTime;
    }

    public void setPackingLeadTime(BigDecimal packingLeadTime) {
        this.packingLeadTime = packingLeadTime;
    }

    @Column(name = "productionWt", precision = 10, scale = 1)
    public BigDecimal getProductionWt() {
        return productionWt;
    }

    public void setProductionWt(BigDecimal productionWt) {
        this.productionWt = productionWt;
    }

    @Column(name = "setup_time", precision = 10, scale = 1)
    public BigDecimal getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(BigDecimal setupTime) {
        this.setupTime = setupTime;
    }

    @Column(name = "assy_to_t1", precision = 10, scale = 1)
    public BigDecimal getAssyToT1() {
        return assyToT1;
    }

    public void setAssyToT1(BigDecimal assyToT1) {
        this.assyToT1 = assyToT1;
    }

    @Column(name = "t2_to_packing", precision = 10, scale = 1)
    public BigDecimal getT2ToPacking() {
        return t2ToPacking;
    }

    public void setT2ToPacking(BigDecimal t2ToPacking) {
        this.t2ToPacking = t2ToPacking;
    }

    @Column(name = "assy_station")
    public Integer getAssyStation() {
        return assyStation;
    }

    public void setAssyStation(Integer assyStation) {
        this.assyStation = assyStation;
    }

    @Column(name = "packing_station")
    public Integer getPackingStation() {
        return packingStation;
    }

    public void setPackingStation(Integer packingStation) {
        this.packingStation = packingStation;
    }

    @Column(name = "assy_kanban_time", precision = 10, scale = 1)
    public BigDecimal getAssyKanbanTime() {
        return assyKanbanTime;
    }

    public void setAssyKanbanTime(BigDecimal assyKanbanTime) {
        this.assyKanbanTime = assyKanbanTime;
    }

    @Column(name = "packing_kanban_time", precision = 10, scale = 1)
    public BigDecimal getPackingKanbanTime() {
        return packingKanbanTime;
    }

    public void setPackingKanbanTime(BigDecimal packingKanbanTime) {
        this.packingKanbanTime = packingKanbanTime;
    }

    @Column(name = "clean_panel_and_assembly", precision = 10, scale = 1)
    public BigDecimal getCleanPanelAndAssembly() {
        return cleanPanelAndAssembly;
    }

    public void setCleanPanelAndAssembly(BigDecimal cleanPanelAndAssembly) {
        this.cleanPanelAndAssembly = cleanPanelAndAssembly;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date", length = 23)
    public Date getModifiedDate() {
        return this.modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}
