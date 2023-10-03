package withplanner.global.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class UserRequestDto {
    private String email;
    private String pw;
    private String name;
    private String nickname;
    private String zipcode; //addr1 - 우편번호
    private String baseAddress; //addr2 - 기본주소
    private String detailedAddress; //addr3 - 상세주소

    public void encodePassword(String encodedPw) {
        this.pw = encodedPw;
    }

    @Override
    public String toString() {
        return "UserRequestDto{" +
                "email='" + email + '\'' +
                ", pw='" + pw + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", baseAddress='" + baseAddress + '\'' +
                ", detailedAddress='" + detailedAddress + '\'' +
                '}';
    }
}
