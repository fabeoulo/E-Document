/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.model.db2;

import com.advantech.model.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.AutoPopulatingList;

/**
 *
 * @author Justin.Yeh
 */
@Entity
@Table(name = "Worktime",
        uniqueConstraints = @UniqueConstraint(columnNames = "model_name")
)
@DynamicInsert(true)
@DynamicUpdate(true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = WorktimeM4f.class)
@Audited(targetAuditMode = NOT_AUDITED, withModifiedFlag = true)
public class WorktimeM4f implements java.io.Serializable, IWorktimeForWebService {

    @JsonView(View.Public.class)
    private int id;

    @JsonView(View.Public.class)
    private FloorM4f floor;

    @JsonView(View.Public.class)
    private FlowM4f flowByTestFlowId;

    @JsonView(View.Public.class)
    private FlowM4f flowByPackingFlowId;

    @JsonView(View.Public.class)
    private FlowM4f flowByBabFlowId;

    @JsonView(View.Public.class)
    private UserM4f userByEeOwnerId; //EE teams name has been changed to BPE

    @JsonView(View.Public.class)
    private UserM4f userByQcOwnerId;

    @JsonView(View.Public.class)
    private UserM4f userBySpeOwnerId;

    @JsonView(View.Public.class)
    private UserM4f userByMpmOwnerId;

    @JsonView(View.Public.class)
    private PendingM4f pending;

    @JsonView(View.Public.class)
    private PreAssyM4f preAssy;

    @JsonView(View.Public.class)
    private TypeM4f type;

    @JsonView(View.Public.class)
    private BusinessGroupM4f businessGroup;

    @JsonView(View.Public.class)
    private String modelName;

    @JsonView(View.Public.class)
    private List<BwFieldM4f> bwField = new AutoPopulatingList<>(BwFieldM4f.class);

    @JsonView(View.Internal.class)
    private List<WorktimeFormulaSettingM4f> worktimeFormulaSettings = new AutoPopulatingList<WorktimeFormulaSettingM4f>(WorktimeFormulaSettingM4f.class);

    @JsonView(View.Public.class)
    private String workCenter;

    @JsonView(View.Public.class)
    private BigDecimal totalModule = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal cleanPanel = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private int preAssyModuleQty;

    @JsonView(View.Public.class)
    private BigDecimal assy = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal t1 = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal t2 = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal t3 = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal t4 = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal packing = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal upBiRi = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal downBiRi = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal upRi = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal downRi = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal biCost = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal vibration = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal hiPotLeakage = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal coldBoot = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal warmBoot = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal pendingTime;

    @JsonView(View.Public.class)
    private String pendingStation;

    @JsonView(View.Public.class)
    private String biSampling = "N";

    @JsonView(View.Public.class)
    private String burnIn = "N";

    @JsonView(View.Public.class)
    private BigDecimal riTime = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal biTime = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal biTemperature = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private String biPower;

    @JsonView(View.Public.class)
    private String assyPackingSop;

    @JsonView(View.Public.class)
    private String testSop;

    @JsonView(View.Public.class)
    private Integer keypartA = 0;

    @JsonView(View.Public.class)
    private Integer keypartB = 0;

    @JsonView(View.Public.class)
    private Integer macTotalQty = 0;

    @JsonView(View.Public.class)
    private Integer macPrintedQty = 0;

    @JsonView(View.Public.class)
    private Integer macPackingQty = 0;

    @JsonView(View.Public.class)
    private Integer macPackingCount = 0;

    @JsonView(View.Public.class)
    private String visionInspect = "N";

    @JsonView(View.Public.class)
    private Integer visionInspectQty = 0;

    @JsonView(View.Public.class)
    private Character partLink;

    @JsonView(View.Public.class)
    private int ce;

    @JsonView(View.Public.class)
    private int ul;

    @JsonView(View.Public.class)
    private int rohs;

    @JsonView(View.Public.class)
    private int weee;

    @JsonView(View.Public.class)
    private int madeInTaiwan;

    @JsonView(View.Public.class)
    private int fcc;

    @JsonView(View.Public.class)
    private int eac;

    @JsonView(View.Public.class)
    private int kc;

    @JsonView(View.Public.class)
    private BigDecimal nsInOneCollectionBox = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private char partNoAttributeMaintain;

    @JsonView(View.Public.class)
    private String acwVoltage = "0";

    @JsonView(View.Public.class)
    private String dcwVoltage = "0";

    @JsonView(View.Public.class)
    private String irVoltage = "0";

    @JsonView(View.Public.class)
    private String testProfile = "0";

    @JsonView(View.Public.class)
    private String lltValue = "0";

    @JsonView(View.Public.class)
    private String gndValue = "0";

    @JsonView(View.Public.class)
    private BigDecimal weight = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal weightAff = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal tolerance = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal assyLeadTime = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal packingLeadTime = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal packingPalletTime = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal productionWt = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal setupTime = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal assyToT1 = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal t2ToPacking = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private Integer burnInQuantity = 0;

    @JsonView(View.Public.class)
    private Integer assyStation = 0;

    @JsonView(View.Public.class)
    private Integer packingStation = 0;

    @JsonView(View.Public.class)
    private BigDecimal assyKanbanTime = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal packingKanbanTime = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal cleanPanelAndAssembly = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal machineWorktime = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private Integer t1StatusQty = 0;

    @JsonView(View.Public.class)
    private Integer t1ItemsQty = 0;

    @JsonView(View.Public.class)
    private Integer t2StatusQty = 0;

    @JsonView(View.Public.class)
    private Integer t2ItemsQty = 0;

    @JsonView(View.Public.class)
    private Date createDate;

    @JsonView(View.Public.class)
    private Date modifiedDate;

    @JsonView(View.Public.class)
    private String reasonCode;

    @JsonView(View.Public.class)
    private String worktimeModReason;

    @JsonView(View.Public.class)
    private int twm2Flag = 0;

    @JsonView(View.Public.class)
    private BigDecimal cobotAutoWt = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal cobotManualWt;

    //This value almost equals to productionWt in sap
    @JsonView(View.Public.class)
    private BigDecimal sapWt = BigDecimal.ZERO;

    @JsonView(View.Internal.class)
    private Set<CobotM4f> cobots = new HashSet<CobotM4f>(0);

    @JsonView(View.Public.class)
    private String labelMac;

    @JsonView(View.Public.class)
    private String macPrintedLocation;

    @JsonView(View.Public.class)
    private String macPrintedFrom;

    @JsonView(View.Public.class)
    private String etlVariable1;

    @JsonView(View.Public.class)
    private String etlVariable2;

    @JsonView(View.Public.class)
    private String etlVariable3;

    @JsonView(View.Public.class)
    private String etlVariable1Aff;

    @JsonView(View.Public.class)
    private String etlVariable2Aff;

    @JsonView(View.Public.class)
    private String etlVariable3Aff;

    @JsonView(View.Public.class)
    private Character labelYN;

    @JsonView(View.Public.class)
    private OutLabelM4f labelOuterId;

    @JsonView(View.Public.class)
    private String labelOuterCustom;

    @JsonView(View.Public.class)
    private CartonLabelM4f labelCartonId;

    @JsonView(View.Public.class)
    private String labelCartonCustom;

    @JsonView(View.Public.class)
    private String labelBigCarton;

    @JsonView(View.Public.class)
    private String label2D;

    @JsonView(View.Public.class)
    private String labelCustomerSn;

    @JsonView(View.Public.class)
    private String labelSn;

    @JsonView(View.Public.class)
    private String labelPn;

    @JsonView(View.Public.class)
    private String labelNmodelA;

    @JsonView(View.Public.class)
    private String labelNmodelB;

    @JsonView(View.Public.class)
    private String labelVariable1;

    @JsonView(View.Public.class)
    private String labelVariable2;

    @JsonView(View.Public.class)
    private String labelVariable3;

    @JsonView(View.Public.class)
    private String labelVariable4;

    @JsonView(View.Public.class)
    private String labelVariable5;

    @JsonView(View.Public.class)
    private String labelVariable6;

    @JsonView(View.Public.class)
    private String labelVariable7;

    @JsonView(View.Public.class)
    private String labelVariable8;

    @JsonView(View.Public.class)
    private String labelVariable9;

    @JsonView(View.Public.class)
    private String labelVariable10;

    @JsonView(View.Public.class)
    private String labelVariable1Aff;

    @JsonView(View.Public.class)
    private String labelVariable2Aff;

    @JsonView(View.Public.class)
    private String labelVariable3Aff;

    @JsonView(View.Public.class)
    private String labelVariable4Aff;

    @JsonView(View.Public.class)
    private String labelVariable5Aff;

    @JsonView(View.Public.class)
    private String labelVariable6Aff;

    @JsonView(View.Public.class)
    private String labelVariable7Aff;

    @JsonView(View.Public.class)
    private String labelVariable8Aff;

    @JsonView(View.Public.class)
    private String labelVariable9Aff;

    @JsonView(View.Public.class)
    private String labelVariable10Aff;

    @JsonView(View.Public.class)
    private String labelPacking1;

    @JsonView(View.Public.class)
    private String labelPacking2;

    @JsonView(View.Public.class)
    private String labelPacking3;

    @JsonView(View.Public.class)
    private String labelPacking4;

    @JsonView(View.Public.class)
    private String labelPacking5;

    @JsonView(View.Public.class)
    private String labelPacking6;

    @JsonView(View.Public.class)
    private String labelPacking7;

    @JsonView(View.Public.class)
    private String labelPacking8;

    @JsonView(View.Public.class)
    private String labelPacking9;

    @JsonView(View.Public.class)
    private String labelPacking10;

    @JsonView(View.Public.class)
    private BigDecimal wifi = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal wwan = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal t0 = BigDecimal.ZERO;

    @JsonView(View.Public.class)
    private BigDecimal loadDefault = BigDecimal.ZERO;

    public WorktimeM4f() {
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

    @NotAudited
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id", nullable = false)
    public FloorM4f getFloor() {
        return this.floor;
    }

    public void setFloor(FloorM4f floor) {
        this.floor = floor;
    }

//    @NotNull(message = "TEST_FLOW不可為空")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_flow_id")
    public FlowM4f getFlowByTestFlowId() {
        return this.flowByTestFlowId;
    }

    public void setFlowByTestFlowId(FlowM4f flowByTestFlowId) {
        this.flowByTestFlowId = flowByTestFlowId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packing_flow_id")
    public FlowM4f getFlowByPackingFlowId() {
        return this.flowByPackingFlowId;
    }

    public void setFlowByPackingFlowId(FlowM4f flowByPackingFlowId) {
        this.flowByPackingFlowId = flowByPackingFlowId;
    }

//    @NotNull(message = "BAB_FLOW不可為空")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bab_flow_id")
    public FlowM4f getFlowByBabFlowId() {
        return this.flowByBabFlowId;
    }

    public void setFlowByBabFlowId(FlowM4f flowByBabFlowId) {
        this.flowByBabFlowId = flowByBabFlowId;
    }

//    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ee_owner_id", nullable = true)
    @Override
    public UserM4f getUserByEeOwnerId() {
        return this.userByEeOwnerId;
    }

    public void setUserByEeOwnerId(UserM4f userByEeOwnerId) {
        this.userByEeOwnerId = userByEeOwnerId;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qc_owner_id", nullable = true)
    @Override
    public UserM4f getUserByQcOwnerId() {
        return this.userByQcOwnerId;
    }

    public void setUserByQcOwnerId(UserM4f userByQcOwnerId) {
        this.userByQcOwnerId = userByQcOwnerId;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spe_owner_id", nullable = true)
    @Override
    public UserM4f getUserBySpeOwnerId() {
        return this.userBySpeOwnerId;
    }

    public void setUserBySpeOwnerId(UserM4f userBySpeOwnerId) {
        this.userBySpeOwnerId = userBySpeOwnerId;
    }

//    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mpm_owner_id", nullable = true)
    @Override
    public UserM4f getUserByMpmOwnerId() {
        return userByMpmOwnerId;
    }

    public void setUserByMpmOwnerId(UserM4f userByMpmOwnerId) {
        this.userByMpmOwnerId = userByMpmOwnerId;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pending_id", nullable = false)
    public PendingM4f getPending() {
        return this.pending;
    }

    public void setPending(PendingM4f pending) {
        this.pending = pending;
    }

//    @NotNull(message = "PRE-ASSY不可為空")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pre_assy_id")
    public PreAssyM4f getPreAssy() {
        return this.preAssy;
    }

    public void setPreAssy(PreAssyM4f preAssy) {
        this.preAssy = preAssy;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[type_id]", nullable = false)
    public TypeM4f getType() {
        return this.type;
    }

    public void setType(TypeM4f type) {
        this.type = type;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "businessGroup_id")
    public BusinessGroupM4f getBusinessGroup() {
        return businessGroup;
    }

    public void setBusinessGroup(BusinessGroupM4f businessGroup) {
        this.businessGroup = businessGroup;
    }

    @NotNull
    @NotEmpty
    @Size(min = 0, max = 50)
    @Column(name = "model_name", unique = true, nullable = false, length = 50)
    @Override
    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Size(min = 0, max = 50)
    @Column(name = "work_center", length = 50)
    public String getWorkCenter() {
        return workCenter;
    }

    public void setWorkCenter(String workCenter) {
        this.workCenter = workCenter;
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "total_module", precision = 10, scale = 1)
    public BigDecimal getTotalModule() {
        return this.totalModule;
    }

    public void setTotalModule(BigDecimal totalModule) {
        this.totalModule = autoFixScale(totalModule, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "clean_panel", precision = 10, scale = 1)
    public BigDecimal getCleanPanel() {
        return this.cleanPanel;
    }

    public void setCleanPanel(BigDecimal cleanPanel) {
        this.cleanPanel = autoFixScale(cleanPanel, 1);
    }

    @Column(name = "pre_assy_moduleQty", nullable = true)
    public int getPreAssyModuleQty() {
        return preAssyModuleQty;
    }

    public void setPreAssyModuleQty(int preAssyModuleQty) {
        this.preAssyModuleQty = preAssyModuleQty;
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "assy", precision = 10, scale = 1)
    public BigDecimal getAssy() {
        return this.assy;
    }

    public void setAssy(BigDecimal assy) {
        this.assy = autoFixScale(assy, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "t1", precision = 10, scale = 1)
    public BigDecimal getT1() {
        return this.t1;
    }

    public void setT1(BigDecimal t1) {
        this.t1 = autoFixScale(t1, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "t2", precision = 10, scale = 1)
    public BigDecimal getT2() {
        return this.t2;
    }

    public void setT2(BigDecimal t2) {
        this.t2 = autoFixScale(t2, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "t3", precision = 10, scale = 1)
    public BigDecimal getT3() {
        return this.t3;
    }

    public void setT3(BigDecimal t3) {
        this.t3 = autoFixScale(t3, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "t4", precision = 10, scale = 1)
    public BigDecimal getT4() {
        return this.t4;
    }

    public void setT4(BigDecimal t4) {
        this.t4 = autoFixScale(t4, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "packing", precision = 10, scale = 1)
    public BigDecimal getPacking() {
        return this.packing;
    }

    public void setPacking(BigDecimal packing) {
        this.packing = autoFixScale(packing, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "up_bi_ri", precision = 10, scale = 1)
    public BigDecimal getUpBiRi() {
        return this.upBiRi;
    }

    public void setUpBiRi(BigDecimal upBiRi) {
        this.upBiRi = autoFixScale(upBiRi, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "down_bi_ri", precision = 10, scale = 1)
    public BigDecimal getDownBiRi() {
        return this.downBiRi;
    }

    public void setDownBiRi(BigDecimal downBiRi) {
        this.downBiRi = autoFixScale(downBiRi, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "up_ri", precision = 10, scale = 1)
    public BigDecimal getUpRi() {
        return upRi;
    }

    public void setUpRi(BigDecimal upRi) {
        this.upRi = autoFixScale(upRi, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "down_ri", precision = 10, scale = 1)
    public BigDecimal getDownRi() {
        return downRi;
    }

    public void setDownRi(BigDecimal downRi) {
        this.downRi = autoFixScale(downRi, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 2 /*scale*/)
    @Column(name = "bi_cost", precision = 10, scale = 2)
    public BigDecimal getBiCost() {
        return this.biCost;
    }

    public void setBiCost(BigDecimal biCost) {
        this.biCost = autoFixScale(biCost, 2);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "vibration", precision = 10, scale = 1)
    public BigDecimal getVibration() {
        return this.vibration;
    }

    public void setVibration(BigDecimal vibration) {
        this.vibration = autoFixScale(vibration, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "hi_pot_leakage", precision = 10, scale = 1)
    public BigDecimal getHiPotLeakage() {
        return this.hiPotLeakage;
    }

    public void setHiPotLeakage(BigDecimal hiPotLeakage) {
        this.hiPotLeakage = autoFixScale(hiPotLeakage, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "cold_boot", precision = 10, scale = 1)
    public BigDecimal getColdBoot() {
        return this.coldBoot;
    }

    public void setColdBoot(BigDecimal coldBoot) {
        this.coldBoot = autoFixScale(coldBoot, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "warm_boot", precision = 10, scale = 1)
    public BigDecimal getWarmBoot() {
        return this.warmBoot;
    }

    public void setWarmBoot(BigDecimal warmBoot) {
        this.warmBoot = autoFixScale(warmBoot, 1);
    }

    @NotNull(message = "Pending Time 不可為空")
    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "pending_time", nullable = false, precision = 10, scale = 1)
    public BigDecimal getPendingTime() {
        return this.pendingTime;
    }

    public void setPendingTime(BigDecimal pendingTime) {
        this.pendingTime = autoFixScale(pendingTime, 1);
    }

    @NotNull
    @Size(min = 0, max = 150)
    @Column(name = "pending_station", length = 150)
    public String getPendingStation() {
        return pendingStation;
    }

    public void setPendingStation(String pendingStation) {
        this.pendingStation = pendingStation;
    }

    @NotNull
    @NotEmpty
    @Column(name = "burn_in", nullable = false, length = 10)
    public String getBurnIn() {
        return this.burnIn;
    }

    public void setBurnIn(String burnIn) {
        this.burnIn = burnIn;
    }

    @NotNull(message = "Ri Time 不可為空")
    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "ri_time", nullable = false, precision = 10, scale = 1)
    public BigDecimal getRiTime() {
        return riTime;
    }

    public void setRiTime(BigDecimal riTime) {
        this.riTime = riTime;
    }

    @NotNull
    @NotEmpty
    @Column(name = "bi_sampling", nullable = false, length = 10)
    public String getBiSampling() {
        return biSampling;
    }

    public void setBiSampling(String biSampling) {
        this.biSampling = biSampling;
    }

    @NotNull(message = "Bi Time 不可為空")
    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "bi_time", nullable = false, precision = 10, scale = 1)
    public BigDecimal getBiTime() {
        return this.biTime;
    }

    public void setBiTime(BigDecimal biTime) {
        this.biTime = autoFixScale(biTime, 1);
    }

    @NotNull(message = "Bi Temperature 不可為空")
    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "bi_temperature", nullable = false, precision = 10, scale = 1)
    public BigDecimal getBiTemperature() {
        return this.biTemperature;
    }

    public void setBiTemperature(BigDecimal biTemperature) {
        this.biTemperature = autoFixScale(biTemperature, 1);
    }

    @NotNull
    @Column(name = "bi_power", nullable = false, length = 30)
    public String getBiPower() {
        return biPower;
    }

    public void setBiPower(String biPower) {
        this.biPower = biPower;
    }

    @Size(min = 0, max = 500)
    @Column(name = "assy_packing_sop", length = 500)
    public String getAssyPackingSop() {
        return this.assyPackingSop != null ? ("".equals(this.assyPackingSop.trim()) ? null : this.assyPackingSop.trim()) : null;
    }

    public void setAssyPackingSop(String assyPackingSop) {
        this.assyPackingSop = assyPackingSop;
    }

    @Size(min = 0, max = 500)
    @Column(name = "test_sop", length = 500)
    public String getTestSop() {
        return this.testSop != null ? ("".equals(this.testSop.trim()) ? null : this.testSop.trim()) : null;
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

    @NotNull
    @Column(name = "mac_totalQty")
    public Integer getMacTotalQty() {
        return macTotalQty;
    }

    public void setMacTotalQty(Integer macTotalQty) {
        this.macTotalQty = macTotalQty;
    }

    @NotNull
    @Column(name = "mac_printedQty")
    public Integer getMacPrintedQty() {
        return macPrintedQty;
    }

    public void setMacPrintedQty(Integer macPrintedQty) {
        this.macPrintedQty = macPrintedQty;
    }

    @NotNull
    @Column(name = "mac_packingQty")
    public Integer getMacPackingQty() {
        return macPackingQty;
    }

    public void setMacPackingQty(Integer macPackingQty) {
        this.macPackingQty = macPackingQty;
    }

    @NotNull
    @Column(name = "mac_packingCount")
    public Integer getMacPackingCount() {
        return macPackingCount;
    }

    public void setMacPackingCount(Integer macPackingCount) {
        this.macPackingCount = macPackingCount;
    }

    @NotNull
    @NotEmpty
    @Column(name = "vision_inspect", nullable = false, length = 10)
    public String getVisionInspect() {
        return visionInspect;
    }

    public void setVisionInspect(String visionInspect) {
        this.visionInspect = visionInspect;
    }

    @NotNull
    @Column(name = "vision_inspect_qty", nullable = false)
    public Integer getVisionInspectQty() {
        return visionInspectQty;
    }

    public void setVisionInspectQty(Integer visionInspectQty) {
        this.visionInspectQty = visionInspectQty;
    }

    @NotNull
    @Column(name = "part_link", length = 1)
    public Character getPartLink() {
        return this.partLink;
    }

    public void setPartLink(Character partLink) {
        this.partLink = partLink;
    }

    @NotNull
    @Column(name = "ce", nullable = false)
    public int getCe() {
        return this.ce;
    }

    public void setCe(int ce) {
        this.ce = ce;
    }

    @NotNull
    @Column(name = "ul", nullable = false)
    public int getUl() {
        return this.ul;
    }

    public void setUl(int ul) {
        this.ul = ul;
    }

    @NotNull
    @Column(name = "rohs", nullable = false)
    public int getRohs() {
        return this.rohs;
    }

    public void setRohs(int rohs) {
        this.rohs = rohs;
    }

    @NotNull
    @Column(name = "weee", nullable = false)
    public int getWeee() {
        return this.weee;
    }

    public void setWeee(int weee) {
        this.weee = weee;
    }

    @NotNull
    @Column(name = "made_in_taiwan", nullable = false)
    public int getMadeInTaiwan() {
        return this.madeInTaiwan;
    }

    public void setMadeInTaiwan(int madeInTaiwan) {
        this.madeInTaiwan = madeInTaiwan;
    }

    @NotNull
    @Column(name = "fcc", nullable = false)
    public int getFcc() {
        return this.fcc;
    }

    public void setFcc(int fcc) {
        this.fcc = fcc;
    }

    @NotNull
    @Column(name = "eac", nullable = false)
    public int getEac() {
        return this.eac;
    }

    public void setEac(int eac) {
        this.eac = eac;
    }

    @NotNull
    @Column(name = "kc", nullable = false)
    public int getKc() {
        return this.kc;
    }

    public void setKc(int kc) {
        this.kc = kc;
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "ns_in_one_collection_box", precision = 10, scale = 1)
    public BigDecimal getNsInOneCollectionBox() {
        return this.nsInOneCollectionBox;
    }

    public void setNsInOneCollectionBox(BigDecimal NsInOneCollectionBox) {
        this.nsInOneCollectionBox = autoFixScale(NsInOneCollectionBox, 1);
    }

    @NotNull
    @Column(name = "part_no_attribute_maintain", nullable = false, length = 1)
    public char getPartNoAttributeMaintain() {
        return this.partNoAttributeMaintain;
    }

    public void setPartNoAttributeMaintain(char partNoAttributeMaintain) {
        this.partNoAttributeMaintain = partNoAttributeMaintain;
    }

    @Digits(integer = 10 /*precision*/, fraction = 4 /*scale*/)
    @Column(name = "[weight]", nullable = false, precision = 10, scale = 4)
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = this.autoFixScale(weight, 4);
    }

    //附加屬性質給使用者維護(特例)
    @Digits(integer = 10 /*precision*/, fraction = 4 /*scale*/)
    @Column(name = "weight_aff", nullable = false, precision = 10, scale = 4)
    public BigDecimal getWeightAff() {
        return weightAff;
    }

    public void setWeightAff(BigDecimal weightAff) {
        this.weightAff = this.autoFixScale(weightAff, 4);
    }

    @NotNull
    @Column(name = "acw_voltage", nullable = false, length = 30)
    public String getAcwVoltage() {
        return acwVoltage;
    }

    public void setAcwVoltage(String acwVoltage) {
        this.acwVoltage = acwVoltage;
    }

    @NotNull
    @Column(name = "dcw_voltage", nullable = false, length = 30)
    public String getDcwVoltage() {
        return dcwVoltage;
    }

    public void setDcwVoltage(String dcwVoltage) {
        this.dcwVoltage = dcwVoltage;
    }

    @NotNull
    @Column(name = "ir_voltage", nullable = false, length = 30)
    public String getIrVoltage() {
        return irVoltage;
    }

    public void setIrVoltage(String irVoltage) {
        this.irVoltage = irVoltage;
    }

    @NotNull
    @Column(name = "test_profile", nullable = false, length = 30)
    public String getTestProfile() {
        return testProfile;
    }

    public void setTestProfile(String testProfile) {
        this.testProfile = testProfile;
    }

    @NotNull
    @Column(name = "llt_value", nullable = false, length = 100)
    public String getLltValue() {
        return lltValue;
    }

    public void setLltValue(String lltValue) {
        this.lltValue = lltValue;
    }

    @NotNull
    @Column(name = "gnd_value", nullable = false, length = 30)
    public String getGndValue() {
        return gndValue;
    }

    public void setGndValue(String gndValue) {
        this.gndValue = gndValue;
    }

    @Digits(integer = 10 /*precision*/, fraction = 4 /*scale*/)
    @Column(name = "tolerance", nullable = false, precision = 10, scale = 4)
    public BigDecimal getTolerance() {
        return tolerance;
    }

    public void setTolerance(BigDecimal tolerance) {
        this.tolerance = this.autoFixScale(tolerance, 4);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "assy_lead_time", precision = 10, scale = 1)
    public BigDecimal getAssyLeadTime() {
        return this.assyLeadTime;
    }

    public void setAssyLeadTime(BigDecimal assyLeadTime) {
        this.assyLeadTime = autoFixScale(assyLeadTime, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "packing_lead_time", precision = 10, scale = 1)
    public BigDecimal getPackingLeadTime() {
        return this.packingLeadTime;
    }

    public void setPackingLeadTime(BigDecimal packingLeadTime) {
        this.packingLeadTime = autoFixScale(packingLeadTime, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "packing_pallet_time", precision = 10, scale = 1)
    public BigDecimal getPackingPalletTime() {
        return packingPalletTime;
    }

    public void setPackingPalletTime(BigDecimal packingPalletTime) {
        this.packingPalletTime = packingPalletTime;
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "productionWt", precision = 10, scale = 1)
    public BigDecimal getProductionWt() {
        return productionWt;
    }

    public void setProductionWt(BigDecimal productionWt) {
        this.productionWt = autoFixScale(productionWt, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "setup_time", precision = 10, scale = 1)
    public BigDecimal getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(BigDecimal setupTime) {
        this.setupTime = autoFixScale(setupTime, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "assy_to_t1", precision = 10, scale = 1)
    public BigDecimal getAssyToT1() {
        return assyToT1;
    }

    public void setAssyToT1(BigDecimal assyToT1) {
        this.assyToT1 = autoFixScale(assyToT1, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "t2_to_packing", precision = 10, scale = 1)
    public BigDecimal getT2ToPacking() {
        return t2ToPacking;
    }

    public void setT2ToPacking(BigDecimal t2ToPacking) {
        this.t2ToPacking = autoFixScale(t2ToPacking, 1);
    }

    @Column(name = "burn_in_quantity")
    public Integer getBurnInQuantity() {
        return burnInQuantity;
    }

    public void setBurnInQuantity(Integer burnInQuantity) {
        this.burnInQuantity = burnInQuantity;
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

    @Digits(integer = 10 /*precision*/, fraction = 2 /*scale*/)
    @Column(name = "assy_kanban_time", precision = 10, scale = 2)
    public BigDecimal getAssyKanbanTime() {
        return assyKanbanTime;
    }

    public void setAssyKanbanTime(BigDecimal assyKanbanTime) {
        this.assyKanbanTime = autoFixScale(assyKanbanTime, 2);
    }

    @Digits(integer = 10 /*precision*/, fraction = 2 /*scale*/)
    @Column(name = "packing_kanban_time", precision = 10, scale = 2)
    public BigDecimal getPackingKanbanTime() {
        return packingKanbanTime;
    }

    public void setPackingKanbanTime(BigDecimal packingKanbanTime) {
        this.packingKanbanTime = autoFixScale(packingKanbanTime, 2);
    }

    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "clean_panel_and_assembly", precision = 10, scale = 1)
    public BigDecimal getCleanPanelAndAssembly() {
        return cleanPanelAndAssembly;
    }

    public void setCleanPanelAndAssembly(BigDecimal cleanPanelAndAssembly) {
        this.cleanPanelAndAssembly = autoFixScale(cleanPanelAndAssembly, 1);
    }

    @Digits(integer = 10 /*precision*/, fraction = 2 /*scale*/)
    @Column(name = "machine_worktime", precision = 10, scale = 2)
    public BigDecimal getMachineWorktime() {
        return machineWorktime;
    }

    public void setMachineWorktime(BigDecimal machineWorktime) {
        this.machineWorktime = machineWorktime;
    }

    private BigDecimal autoFixScale(BigDecimal d, int scale) {
        return d == null ? null : d.setScale(scale, RoundingMode.HALF_UP);
    }

    @NotAudited
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", length = 23, updatable = false)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @NotAudited
    @UpdateTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd'T'kk:mm:ss.SSS'Z'", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date", length = 23)
    public Date getModifiedDate() {
        return this.modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "worktime", orphanRemoval = true)
    public List<WorktimeFormulaSettingM4f> getWorktimeFormulaSettings() {
        return this.worktimeFormulaSettings;
    }

    public void setWorktimeFormulaSettings(List<WorktimeFormulaSettingM4f> worktimeFormulaSettings) {
        this.worktimeFormulaSettings = worktimeFormulaSettings;
    }

    @NotAudited
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "worktime")
    public List<BwFieldM4f> getBwFields() {
        return bwField;
    }

    public void setBwFields(List<BwFieldM4f> bwFields) {
        this.bwField = bwFields;
    }

    @Audited(withModifiedFlag = false)
    @Column(name = "reasonCode", length = 10)
    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    @Audited(withModifiedFlag = false)
    @Column(name = "worktime_mod_reason", length = 150)
    public String getWorktimeModReason() {
        return worktimeModReason;
    }

    public void setWorktimeModReason(String worktimeModReason) {
        this.worktimeModReason = worktimeModReason;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_mac", length = 150)
    public String getLabelMac() {
        return labelMac;
    }

    public void setLabelMac(String labelMac) {
        this.labelMac = labelMac;
    }

    @NotAudited
    @Column(name = "twm2_flag", nullable = false)
    public int getTwm2Flag() {
        return twm2Flag;
    }

    public void setTwm2Flag(int twm2Flag) {
        this.twm2Flag = twm2Flag;
    }

    @Transient
    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    public BigDecimal getCobotAutoWt() {
        return cobotAutoWt;
    }

    public void setCobotAutoWt(BigDecimal cobotAutoWt) {
        this.cobotAutoWt = cobotAutoWt;
    }

    @NotNull
    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "cobotManualWt", precision = 10, scale = 1, nullable = false)
    public BigDecimal getCobotManualWt() {
        return cobotManualWt;
    }

    public void setCobotManualWt(BigDecimal cobotManualWt) {
        this.cobotManualWt = cobotManualWt;
    }

    @Size(min = 0, max = 50)
    @Column(name = "mac_printed_location", length = 50)
    public String getMacPrintedLocation() {
        return macPrintedLocation;
    }

    public void setMacPrintedLocation(String macPrintedLocation) {
        this.macPrintedLocation = macPrintedLocation;
    }

    @Size(min = 0, max = 50)
    @Column(name = "mac_printed_from", length = 50)
    public String getMacPrintedFrom() {
        return macPrintedFrom;
    }

    public void setMacPrintedFrom(String macPrintedFrom) {
        this.macPrintedFrom = macPrintedFrom;
    }

    @Size(min = 0, max = 150)
    @Column(name = "etl_variable_1", length = 150)
    public String getEtlVariable1() {
        return etlVariable1;
    }

    public void setEtlVariable1(String etlVariable1) {
        this.etlVariable1 = etlVariable1;
    }

    @Size(min = 0, max = 150)
    @Column(name = "etl_variable_2", length = 150)
    public String getEtlVariable2() {
        return etlVariable2;
    }

    public void setEtlVariable2(String etlVariable2) {
        this.etlVariable2 = etlVariable2;
    }

    @Size(min = 0, max = 150)
    @Column(name = "etl_variable_3", length = 150)
    public String getEtlVariable3() {
        return etlVariable3;
    }

    public void setEtlVariable3(String etlVariable3) {
        this.etlVariable3 = etlVariable3;
    }

    @Size(min = 0, max = 150)
    @Column(name = "etl_variable_1_aff", length = 150)
    public String getEtlVariable1Aff() {
        return etlVariable1Aff;
    }

    public void setEtlVariable1Aff(String etlVariable1Aff) {
        this.etlVariable1Aff = etlVariable1Aff;
    }

    @Size(min = 0, max = 150)
    @Column(name = "etl_variable_2_aff", length = 150)
    public String getEtlVariable2Aff() {
        return etlVariable2Aff;
    }

    public void setEtlVariable2Aff(String etlVariable2Aff) {
        this.etlVariable2Aff = etlVariable2Aff;
    }

    @Size(min = 0, max = 150)
    @Column(name = "etl_variable_3_aff", length = 150)
    public String getEtlVariable3Aff() {
        return etlVariable3Aff;
    }

    public void setEtlVariable3Aff(String etlVariable3Aff) {
        this.etlVariable3Aff = etlVariable3Aff;
    }

    @NotNull
    @Column(name = "label_yn", nullable = true)
    public Character getLabelYN() {
        return labelYN;
    }

    public void setLabelYN(Character labelYN) {
        this.labelYN = labelYN;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "label_outer_id")
    public OutLabelM4f getLabelOuterId() {
        return labelOuterId;
    }

    public void setLabelOuterId(OutLabelM4f labelOuterId) {
        this.labelOuterId = labelOuterId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "label_carton_id")
    public CartonLabelM4f getLabelCartonId() {
        return labelCartonId;
    }

    public void setLabelCartonId(CartonLabelM4f labelCartonId) {
        this.labelCartonId = labelCartonId;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_outer_custom", length = 150)
    public String getLabelOuterCustom() {
        return labelOuterCustom;
    }

    public void setLabelOuterCustom(String labelOuterCustom) {
        this.labelOuterCustom = labelOuterCustom;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_carton_custom", length = 150)
    public String getLabelCartonCustom() {
        return labelCartonCustom;
    }

    public void setLabelCartonCustom(String labelCartonCustom) {
        this.labelCartonCustom = labelCartonCustom;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_bigCarton", length = 150)
    public String getLabelBigCarton() {
        return labelBigCarton;
    }

    public void setLabelBigCarton(String labelBigCarton) {
        this.labelBigCarton = labelBigCarton;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_2D", length = 150)
    public String getLabel2D() {
        return label2D;
    }

    public void setLabel2D(String label2D) {
        this.label2D = label2D;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_customer_sn", length = 150)
    public String getLabelCustomerSn() {
        return labelCustomerSn;
    }

    public void setLabelCustomerSn(String labelCustomerSn) {
        this.labelCustomerSn = labelCustomerSn;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_sn", length = 150)
    public String getLabelSn() {
        return labelSn;
    }

    public void setLabelSn(String labelSn) {
        this.labelSn = labelSn;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_pn", length = 150)
    public String getLabelPn() {
        return labelPn;
    }

    public void setLabelPn(String labelPn) {
        this.labelPn = labelPn;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_n_model_a", length = 150)
    public String getLabelNmodelA() {
        return labelNmodelA;
    }

    public void setLabelNmodelA(String labelNmodelA) {
        this.labelNmodelA = labelNmodelA;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_n_model_b", length = 150)
    public String getLabelNmodelB() {
        return labelNmodelB;
    }

    public void setLabelNmodelB(String labelNmodelB) {
        this.labelNmodelB = labelNmodelB;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_1", length = 150)
    public String getLabelVariable1() {
        return labelVariable1;
    }

    public void setLabelVariable1(String labelVariable1) {
        this.labelVariable1 = labelVariable1;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_2", length = 150)
    public String getLabelVariable2() {
        return labelVariable2;
    }

    public void setLabelVariable2(String labelVariable2) {
        this.labelVariable2 = labelVariable2;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_3", length = 150)
    public String getLabelVariable3() {
        return labelVariable3;
    }

    public void setLabelVariable3(String labelVariable3) {
        this.labelVariable3 = labelVariable3;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_4", length = 150)
    public String getLabelVariable4() {
        return labelVariable4;
    }

    public void setLabelVariable4(String labelVariable4) {
        this.labelVariable4 = labelVariable4;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_5", length = 150)
    public String getLabelVariable5() {
        return labelVariable5;
    }

    public void setLabelVariable5(String labelVariable5) {
        this.labelVariable5 = labelVariable5;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_6", length = 150)
    public String getLabelVariable6() {
        return labelVariable6;
    }

    public void setLabelVariable6(String labelVariable6) {
        this.labelVariable6 = labelVariable6;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_7", length = 150)
    public String getLabelVariable7() {
        return labelVariable7;
    }

    public void setLabelVariable7(String labelVariable7) {
        this.labelVariable7 = labelVariable7;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_8", length = 150)
    public String getLabelVariable8() {
        return labelVariable8;
    }

    public void setLabelVariable8(String labelVariable8) {
        this.labelVariable8 = labelVariable8;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_9", length = 150)
    public String getLabelVariable9() {
        return labelVariable9;
    }

    public void setLabelVariable9(String labelVariable9) {
        this.labelVariable9 = labelVariable9;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_10", length = 150)
    public String getLabelVariable10() {
        return labelVariable10;
    }

    public void setLabelVariable10(String labelVariable10) {
        this.labelVariable10 = labelVariable10;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_1_aff", length = 150)
    public String getLabelVariable1Aff() {
        return labelVariable1Aff;
    }

    public void setLabelVariable1Aff(String labelVariable1Aff) {
        this.labelVariable1Aff = labelVariable1Aff;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_2_aff", length = 150)
    public String getLabelVariable2Aff() {
        return labelVariable2Aff;
    }

    public void setLabelVariable2Aff(String labelVariable2Aff) {
        this.labelVariable2Aff = labelVariable2Aff;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_3_aff", length = 150)
    public String getLabelVariable3Aff() {
        return labelVariable3Aff;
    }

    public void setLabelVariable3Aff(String labelVariable3Aff) {
        this.labelVariable3Aff = labelVariable3Aff;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_4_aff", length = 150)
    public String getLabelVariable4Aff() {
        return labelVariable4Aff;
    }

    public void setLabelVariable4Aff(String labelVariable4Aff) {
        this.labelVariable4Aff = labelVariable4Aff;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_5_aff", length = 150)
    public String getLabelVariable5Aff() {
        return labelVariable5Aff;
    }

    public void setLabelVariable5Aff(String labelVariable5Aff) {
        this.labelVariable5Aff = labelVariable5Aff;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_6_aff", length = 150)
    public String getLabelVariable6Aff() {
        return labelVariable6Aff;
    }

    public void setLabelVariable6Aff(String labelVariable6Aff) {
        this.labelVariable6Aff = labelVariable6Aff;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_7_aff", length = 150)
    public String getLabelVariable7Aff() {
        return labelVariable7Aff;
    }

    public void setLabelVariable7Aff(String labelVariable7Aff) {
        this.labelVariable7Aff = labelVariable7Aff;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_8_aff", length = 150)
    public String getLabelVariable8Aff() {
        return labelVariable8Aff;
    }

    public void setLabelVariable8Aff(String labelVariable8Aff) {
        this.labelVariable8Aff = labelVariable8Aff;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_9_aff", length = 150)
    public String getLabelVariable9Aff() {
        return labelVariable9Aff;
    }

    public void setLabelVariable9Aff(String labelVariable9Aff) {
        this.labelVariable9Aff = labelVariable9Aff;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_variable_10_aff", length = 150)
    public String getLabelVariable10Aff() {
        return labelVariable10Aff;
    }

    public void setLabelVariable10Aff(String labelVariable10Aff) {
        this.labelVariable10Aff = labelVariable10Aff;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_packing_1", length = 150)
    public String getLabelPacking1() {
        return labelPacking1;
    }

    public void setLabelPacking1(String labelPacking1) {
        this.labelPacking1 = labelPacking1;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_packing_2", length = 150)
    public String getLabelPacking2() {
        return labelPacking2;
    }

    public void setLabelPacking2(String labelPacking2) {
        this.labelPacking2 = labelPacking2;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_packing_3", length = 150)
    public String getLabelPacking3() {
        return labelPacking3;
    }

    public void setLabelPacking3(String labelPacking3) {
        this.labelPacking3 = labelPacking3;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_packing_4", length = 150)
    public String getLabelPacking4() {
        return labelPacking4;
    }

    public void setLabelPacking4(String labelPacking4) {
        this.labelPacking4 = labelPacking4;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_packing_5", length = 150)
    public String getLabelPacking5() {
        return labelPacking5;
    }

    public void setLabelPacking5(String labelPacking5) {
        this.labelPacking5 = labelPacking5;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_packing_6", length = 150)
    public String getLabelPacking6() {
        return labelPacking6;
    }

    public void setLabelPacking6(String labelPacking6) {
        this.labelPacking6 = labelPacking6;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_packing_7", length = 150)
    public String getLabelPacking7() {
        return labelPacking7;
    }

    public void setLabelPacking7(String labelPacking7) {
        this.labelPacking7 = labelPacking7;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_packing_8", length = 150)
    public String getLabelPacking8() {
        return labelPacking8;
    }

    public void setLabelPacking8(String labelPacking8) {
        this.labelPacking8 = labelPacking8;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_packing_9", length = 150)
    public String getLabelPacking9() {
        return labelPacking9;
    }

    public void setLabelPacking9(String labelPacking9) {
        this.labelPacking9 = labelPacking9;
    }

    @Size(min = 0, max = 150)
    @Column(name = "label_packing_10", length = 150)
    public String getLabelPacking10() {
        return labelPacking10;
    }

    public void setLabelPacking10(String labelPacking10) {
        this.labelPacking10 = labelPacking10;
    }

    @Digits(integer = 10 /*precision*/, fraction = 3 /*scale*/)
    @Column(name = "sapWt", precision = 10, scale = 3)
    public BigDecimal getSapWt() {
        return sapWt;
    }

    public void setSapWt(BigDecimal sapWt) {
        this.sapWt = sapWt;
    }

    @NotAudited
    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "wifi", precision = 10, scale = 1)
    public BigDecimal getWifi() {
        return wifi;
    }

    public void setWifi(BigDecimal wifi) {
        this.wifi = autoFixScale(wifi, 1);
    }

    @NotAudited
    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "wwan", precision = 10, scale = 1)
    public BigDecimal getWwan() {
        return wwan;
    }

    public void setWwan(BigDecimal wwan) {
        this.wwan = autoFixScale(wwan, 1);
    }

    @NotAudited
    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "t0", precision = 10, scale = 1)
    public BigDecimal getT0() {
        return t0;
    }

    public void setT0(BigDecimal t0) {
        this.t0 = autoFixScale(t0, 1);
    }

    @NotAudited
    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    @Column(name = "load_Default", precision = 10, scale = 1)
    public BigDecimal getLoadDefault() {
        return loadDefault;
    }

    public void setLoadDefault(BigDecimal loadDefault) {
        this.loadDefault = autoFixScale(loadDefault, 1);
    }

    @Column(name = "t1_statusQty")
    public Integer getT1StatusQty() {
        return t1StatusQty;
    }

    public void setT1StatusQty(Integer t1StatusQty) {
        this.t1StatusQty = t1StatusQty;
    }

    @Column(name = "t1_itemsQty")
    public Integer getT1ItemsQty() {
        return t1ItemsQty;
    }

    public void setT1ItemsQty(Integer t1ItemsQty) {
        this.t1ItemsQty = t1ItemsQty;
    }

    @Column(name = "t2_statusQty")
    public Integer getT2StatusQty() {
        return t2StatusQty;
    }

    public void setT2StatusQty(Integer t2StatusQty) {
        this.t2StatusQty = t2StatusQty;
    }

    @Column(name = "t2_itemsQty")
    public Integer getT2ItemsQty() {
        return t2ItemsQty;
    }

    public void setT2ItemsQty(Integer t2ItemsQty) {
        this.t2ItemsQty = t2ItemsQty;
    }

//    @NotAudited
//    @ManyToMany(fetch = FetchType.LAZY)
    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "Worktime_Cobot_REF", joinColumns = {
        @JoinColumn(name = "worktime_id", nullable = false, insertable = false, updatable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "cobot_id", nullable = false, insertable = false, updatable = false)})
    public Set<CobotM4f> getCobots() {
        return cobots;
    }

    public void setCobots(Set<CobotM4f> cobots) {
        this.cobots = cobots;
    }

//    @LazyCollection(LazyCollectionOption.FALSE)
//    @Fetch(value = FetchMode.SUBSELECT)
//    @NotAudited
//    @OneToMany(fetch=FetchType.EAGER, mappedBy = "worktime", orphanRemoval = true)
//    public Set<WorktimeTestStationInfo> getWorktimeTestStationInfos() {
//        return worktimeTestStationInfos;
//    }
//
//    public void setWorktimeTestStationInfos(Set<WorktimeTestStationInfo> worktimeTestStationInfos) {
//        this.worktimeTestStationInfos = worktimeTestStationInfos;
//    }
//---------------------------------------------------------------------
//  Default formula column caculate
    public void setDefaultProductWt() {
        BigDecimal defaultValue = notEmpty(totalModule).add(notEmpty(cleanPanel))
                .add(notEmpty(assy)).add(notEmpty(t1)).add(notEmpty(t2))
                .add(notEmpty(t3)).add(notEmpty(t4)).add(notEmpty(packing))
                .add(notEmpty(upBiRi)).add(notEmpty(downBiRi)).add(notEmpty(upRi)).add(notEmpty(downRi))
                .add(notEmpty(biCost)).add(notEmpty(vibration)).add(notEmpty(hiPotLeakage))
                .add(notEmpty(coldBoot)).add(notEmpty(warmBoot));

        this.setProductionWt(defaultValue);
    }

    public void setDefaultSetupTime() {
        BigDecimal defaultValue = BigDecimal.ZERO
                .add(notEmpty(totalModule).compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : new BigDecimal(5))
                .add((notEmpty(assy)).compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : new BigDecimal(22))
                .add(notEmpty(t1).compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : new BigDecimal(10))
                .add(notEmpty(t2).compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : new BigDecimal(10))
                .add(notEmpty(t3).compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : new BigDecimal(5))
                .add(notEmpty(t4).compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : new BigDecimal(5))
                .add((notEmpty(packing)).compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : new BigDecimal(7));
        this.setSetupTime(defaultValue);
    }

    public void setDefaultAssyToT1() {
        BigDecimal defaultValue = notEmpty(cleanPanel).add(notEmpty(assy).add(notEmpty(totalModule))).add(notEmpty(t1))
                .add(notEmpty(upBiRi)).add(notEmpty(upRi)).add(notEmpty(vibration))
                .add(notEmpty(hiPotLeakage)).add(notEmpty(coldBoot)).add(notEmpty(warmBoot));
        this.setAssyToT1(defaultValue);
    }

    public void setDefaultT2ToPacking() {
        BigDecimal defaultValue = notEmpty(t2)
                .add(notEmpty(t3)).add(notEmpty(t4)).add(notEmpty(packing).add(notEmpty(packingLeadTime)))
                .add(notEmpty(downBiRi)).add(notEmpty(downRi));
        this.setT2ToPacking(defaultValue);
    }

    public void setDefaultAssyStation() {
        int defaultValue;
        if ((notEmpty(assy).add(notEmpty(totalModule))).compareTo(new BigDecimal(45)) <= 0) {
            defaultValue = 3;
        } else {
            BigDecimal b = (notEmpty(assy).add(notEmpty(totalModule))).divide(new BigDecimal(15), 0, RoundingMode.HALF_UP);
            if (b.compareTo(new BigDecimal(6)) >= 0) {
                defaultValue = 6;
            } else {
                defaultValue = b.intValue();
            }
        }
        this.setAssyStation(defaultValue);
    }

    public void setDefaultPackingStation() {
        int defaultValue;
        if ((notEmpty(packing).add(notEmpty(packingLeadTime))).compareTo(new BigDecimal(10)) <= 0) {
            defaultValue = 2;
        } else {
            BigDecimal b = (notEmpty(packing).add(notEmpty(packingLeadTime))).divide(new BigDecimal(5), 0, RoundingMode.HALF_UP);
            if (b.compareTo(new BigDecimal(6)) >= 0) {
                defaultValue = 6;
            } else {
                defaultValue = b.intValue();
            }
        }
        this.setPackingStation(defaultValue);
    }

    public void setDefaultAssyKanbanTime() {
        BigDecimal defaultValue = notEmpty(assy)
                .divide(new BigDecimal(assyStation), 2, RoundingMode.HALF_UP);
        this.setAssyKanbanTime(defaultValue);

    }

    public void setDefaultPackingKanbanTime() {
        BigDecimal defaultValue = notEmpty(packing)
                .divide(new BigDecimal(packingStation), 2, RoundingMode.HALF_UP);
        this.setPackingKanbanTime(defaultValue);
    }

    public void setDefaultCleanPanelAndAssembly() {
        BigDecimal defaultValue = notEmpty(cleanPanel).add(notEmpty(assy));
        this.setCleanPanelAndAssembly(defaultValue);
    }

    private BigDecimal notEmpty(BigDecimal d) {
        return d == null ? BigDecimal.ZERO : d;
    }

//---------------------------------------------------------------------
//  For Cobots in Worktime_Autoupload_Setting formula
    // save data in memory
    @Digits(integer = 10 /*precision*/, fraction = 1 /*scale*/)
    public BigDecimal initCobotAutoWt() {
        BigDecimal cobotAutoWt = isM2CobotAdam() ? productionWt : new BigDecimal(0);
        setCobotAutoWt(cobotAutoWt);
        return getCobotAutoWt();
    }

    @Transient
    private boolean isM2CobotAdam() {
        return twm2Flag == 1 && !cobots.isEmpty()
                && cobots.stream().anyMatch(c -> c.getName().contains("ADAM"));
    }

//---------------------------------------------------------------------
//  For WorktimeDownloadMes
    public Object getDefaultByType(Class<?> type, String fieldName) {
        if (type.equals(UserM4f.class) || type.equals(IUserM9.class)) {
            switch (fieldName) {
                case "userBySpeOwnerId":
                    return new UserM4f(78);
                case "userByQcOwnerId":
                    return new UserM4f(2);
                default:
                    return new UserM4f(40);
            }
        } else if (type.equals(FloorM4f.class)) {
            return new FloorM4f(6, "4F");
        } else if (type.equals(PendingM4f.class)) {
            return new PendingM4f(3);
        } else if (type.equals(TypeM4f.class)) {
            return new TypeM4f(6, "MP");
        } else if (type.equals(BusinessGroupM4f.class)) {
            return new BusinessGroupM4f(5);
        } else if (type.equals(BigDecimal.class)) {
            return BigDecimal.ZERO;
        } else if (type.equals(String.class)) {
            switch (fieldName) {
                case "biPower":
                    return "<300W";
                default:
                    return "";
            }
        } else if (type.equals(Character.class)) {
            if (fieldName.equals("partLink") || fieldName.equals("labelYN")) {
                return 'Y';
            } else {
                return ' ';
            }
        } else if (type.equals(Integer.class)) {
            return 0;
        } else {
            return null;
        }
    }
//---------------------------------------------------------------------

    //Override hashcode and equals will force audit query eager loading the one to many field
    //Still looking for reason.
}
