package withplanner.withplanner_api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Address {
    private String zipcode; //addr1 - 우편번호
    private String baseAddress; //addr2 - 기본주소
    private String detailedAddress; //addr3 - 상세주소

    public Address(String zipcode, String baseAddress, String detailedAddress) {
        this.zipcode = zipcode;
        this.baseAddress = baseAddress;
        this.detailedAddress = detailedAddress;
    }
}
