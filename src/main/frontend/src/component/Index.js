import React from 'react';
import {BrowserRouter,Routes,Route} from 'react-router-dom';
import Main from './Main';
import Header from './Header';
import Footer from './Footer';
import Login from './Login';
import Mypage from './employee/Mypage';
import Registration from './employee/Registration';
import AllEmployee from './employee/AllEmplyee';
import LeaveRequest from './employee/LeaveRequest';
import LeaveRequestList from './employee/LeaveRequestList';
import AttendanceStatus from './employee/AttendanceStatus';


export default function Index(props) {
    return (<>
        <BrowserRouter>
            <Header/>
            <Routes>
                <Route path="/" element={ <Main/> }/>
                <Route path="/login" element={ <Login/> }/>
                <Route path="/registration" element={ <Registration/> }/>
                <Route path="/off" element={ <LeaveRequest/> }/>
                <Route path="/offlist" element={ <LeaveRequestList/> }/>
                <Route path="/allemployee" element={ <AllEmployee/> }/>
                <Route path="/mypage" element={ <Mypage/> }/>
                <Route path="/attendancestatus" element={ <AttendanceStatus/> }/>

            </Routes>
            <Footer/>
        </BrowserRouter>


    </>)
}