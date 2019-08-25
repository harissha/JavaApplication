package Oplog;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@ToString
public class EdsAuditLog {
    private String shipToSiteName;
    private String warrantyBillToId;
    private String contractPartyId;
    private String warrantyStartDate;
    private String itemFamilyDesc;
    private String attribute13;
    private String endCustomerSiteAddress2;
    private String warrantyLineStatus;
    private String attribute7;
    private String attribute15;

    public String getShipToSiteName() {
        return shipToSiteName == null ? "" : shipToSiteName;
    }

    public String getWarrantyBillToId() {
        return warrantyBillToId == null ? "" : warrantyBillToId;
    }

    public String getContractPartyId() {
        return contractPartyId == null ? "" : contractPartyId;
    }

    public String getWarrantyStartDate() {
        return warrantyStartDate == null ? "" : warrantyStartDate;
    }

    public String getItemFamilyDesc() {
        return itemFamilyDesc == null ? "" : itemFamilyDesc;
    }

    public String getAttribute13() {
        return attribute13 == null ? "" : attribute13;
    }

    public String getEndCustomerSiteAddress2() {
        return endCustomerSiteAddress2;
    }

    public String getWarrantyLineStatus() {
        return warrantyLineStatus;
    }

    public String getAttribute7() {
        return attribute7;
    }

    public String getAttribute15() {
        return attribute15;
    }

}
