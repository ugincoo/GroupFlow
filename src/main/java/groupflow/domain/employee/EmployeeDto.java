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

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class EmployeeDto implements UserDetails {
    private int eno;
    private String ename;   //이름
    private long esocialno;  //주민등록번호
    private String eemail;  //이메일
    private String ephone;  //휴대폰번호
    private String ephoto;  //사진
    private String hiredate; //입사일
    private String eenddate; //퇴사일
    // 추가
    private int dno; // 부서번호
    private int pno; // 직급번호

    public EmployeeEntity toEntity() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .eno(this.eno)
                .ename(this.ename)
                .esocialno(this.esocialno)
                .eemail(this.eemail)
                .ephone(this.ephone)
                .ephoto(this.ephoto)
                // LocalDate파싱후 -> LocalDateTime으로 변환
                .hiredate( LocalDate.parse(this.hiredate , formatter).atStartOfDay() )
                .build();
        // 퇴사일 값이 있으면 String -> LocalDate -> LocalDateTime 변환 Entity eenddate필드에 대입
        if ( this.eenddate != null) {
            employeeEntity.setEenddate(LocalDate.parse(this.eenddate, formatter).atStartOfDay());
        }
        return employeeEntity;
    }



    // 시큐리티


    @Override // 인증된 권한반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override // 패스워드 반환
    public String getPassword() {
        return null;
    }

    @Override   // 계정 반환
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
