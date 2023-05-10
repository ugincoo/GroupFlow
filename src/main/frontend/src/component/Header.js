import React from 'react';

export default function Header(props) {




    return (<div>
        헤더입니다

        <a href="/registration">사원등록</a>
        <a href="/allemployee">직원전체출력[관리자입장]</a>
        <a href="/dayoff">연차 모달</a>
        <a href="/mypage">마이페이지</a>
        <a href="http://localhost:8080/employee/logout">로그아웃</a>
    </div>)
    }