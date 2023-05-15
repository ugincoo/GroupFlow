package groupflow.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class EmployeeDto implements UserDetails {
    private int eno;
    private String ename;   //이름
    private long esocialno;  //주민등록번호
    private String eemail;  //이메일
    private String ephone;  //휴대폰번호
    private String ephoto;  //사진 파일명
    private String hiredate; //입사일
    private String eenddate; //퇴사일
    // 추가
    private int dno; // 부서번호
    private int pno; // 직급번호

    //[장민정 추가]
    private String dname;   //부서명
    private String pname;   //직급명
    private int id; //데이타테이블 식별용

    // 이미지 첨부파일용
    private MultipartFile ephotoData;

    // 로그인시 권한 인증용
    private Set<GrantedAuthority> securityPermissionList; // securityPermissionList : 권한목록





    public EmployeeEntity toEntity() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .eno(this.eno)
                .ename(this.ename)
                .esocialno(this.esocialno)
                .eemail(this.eemail)
                .ephone(this.ephone)
                .ephoto(this.ephoto)
                .build();

        if ( this.hiredate != null) {
            employeeEntity.setHiredate( LocalDate.parse(this.hiredate) );
        }
        // 퇴사일 값이 있으면 String -> LocalDate -> LocalDateTime 변환 Entity eenddate필드에 대입
        if ( this.eenddate != null) {
            employeeEntity.setEenddate( LocalDate.parse(this.eenddate) );
        }
        return employeeEntity;
    }


    // 시큐리티


    @Override // 인증된 권한반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.securityPermissionList;
    }

    @Override // 패스워드 반환
    public String getPassword() {
        return ename;
    }

    @Override   // 계정 반환
    public String getUsername() {
        return eno+"";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
