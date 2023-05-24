import React , {useState} from 'react';
import {BrowserRouter,Routes,Route,Link} from 'react-router-dom';
import Main from './Main';
import Header from './Header';
import Footer from './Footer';
import Login from './Login';
import Logout from './Logout';
import Mypage from './employee/Mypage';
import Registration from './employee/Registration';
import AllEmployee from './employee/AllEmplyee';
import LeaveRequest from './employee/LeaveRequest';
import LeaveRequestList from './employee/LeaveRequestList';
import AdminLeaveList from './employee/AdminLeaveList';
import AttendanceStatus from './employee/AttendanceStatus';
import PLeaveList from './employee/PLeaveList';
import ManagerEmployeeListView from './employee/ManagerEmployeeListView';
import Notice from './employee/Notice';
import Chatting from './employee/Chatting';
import NoticePrint from './employee/NoticePrint';
import Marquee from "react-fast-marquee";
import Chart from "./employee/Chart";




export default function Index(props) {
    let [ login , setLogin ] = useState( JSON.parse(localStorage.getItem("login_token")) )
    console.log( login === null )

    return (<>
         <Marquee><NoticePrint /></Marquee>
        <BrowserRouter>
            { login!==null ? <Header/> : "" }
            <Routes>
                <Route path="/" element={ <Main/> }/>
                <Route path="/login" element={ <Login/> }/>
                <Route path="/logout" element={ <Logout/> }/>
                <Route path="/registration" element={ <Registration/> }/>
                <Route path="/off" element={ <LeaveRequest/> }/>
                <Route path="/offlist" element={ <LeaveRequestList/> }/>
                <Route path="/pofflist" element={ <PLeaveList/> }/>
                <Route path="/adminofflist" element={ <AdminLeaveList/> }/>
                <Route path="/allemployee" element={ <AllEmployee/> }/>
                <Route path="/mypage" element={ <Mypage/> }/>
                <Route path="/attendancestatus" element={ <AttendanceStatus/> }/>
                <Route path="/manageremployeelistview" element={ <ManagerEmployeeListView/> }/>
                <Route path="/chatting" element={ <Chatting/> }/>
                <Route path="/notice" element={ <Notice/> }/>
                <Route path="/chart" element={ <Chart/> }/>


            </Routes>
             { login!==null ? <Footer/> : "" }
        </BrowserRouter>
    </>)
}