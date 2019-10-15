package MongoOplogToKafka;

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

}