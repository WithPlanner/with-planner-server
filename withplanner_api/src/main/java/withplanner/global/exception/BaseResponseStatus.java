package withplanner.global.exception;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : 민희
     */
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    INVALID_JWT(false, 2001, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2002,"권한이 없는 유저의 접근입니다."),
    DUPLICATE_USER_EMAIL(false, 2003, "중복되는 유저 이메일입니다"),
    NOT_EXISTS_PARTICIPANT(false,2004,"존재하지 않는 멤버입니다"),
    EMPTY_EMAIL(false,2005,"이메일 값을 입력해주세요"),
    EMPTY_PASSWORD(false,2006,"비밀번호 값을 입력해주세요"),
    NOT_EXISTS_EMAIL(false,2007,"존재하지 않는 이메일입니다."),
    NOT_EXISTS_COMMUNITY(false, 2008, "존재하지 않는 커뮤니티입니다."),
    EXCEED_HEAD_COUNT(false,2009,"커뮤니티의 최대 인원을 초과할 수 없습니다."),
    NOT_EXISTS_COMMUNITY_MEMBER(false,2010,"존재하는 채팅방 참여 정보가 없습니다.(communityMember가 존재하지 않습니다.)"),
    EXPIRED_JWT_TOKEN(false, 2011, "jwt가 유효하지 않습니다. 재로그인 바랍니다."),
    NOT_AUTHENTICATE_DAY(false, 2012, "인증 요일이 아닙니다."),
    NOT_EXISTS_MAP_POST(false, 2013, "존재하는 위치 인증 게시물이 없습니다."),
    NOT_EXISTS_POST(false, 2014, "존재하는 글 인증 게시물이 없습니다."),
    NOT_EXISTS_COMMENTS(false, 2015, "존재한 댓글이 없습니다. "),
    AFTER_AUTHENTICATE_TIME(false, 2016, "인증시간이 지났습니다."),
    NOT_EXISTS_PASSWORD(false, 2017, "잘못된 비밀번호 입니다. "),
    EXPIRED_CREDENTIAL_JWT_TOKEN(false,2018, "jwt credential이 만료되었습니다."),
    CANNOT_AUTHENTICATE_LOCATION(false, 2019, "위치인증에 실패 하였습니다. "),
    NOT_JWT_IN_HEADER(false, 2020, "header에 jwt 토큰이 없습니다. "),
    ALREADY_AUTHENTICATE_TODAY(false, 2021, "이미 오늘 인증을 완료 하였습니다. "),

    /**
     * 3000 : 홍서
     */


    INTERNAL_SERVER_ERROR(false,4000,"서버 오류입니다.");


    // 5000 : 필요시 만들어서 쓰세요


    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
