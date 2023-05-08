import React from 'react';
import {BrowserRouter,Routes,Route} from 'react-router-dom';
import Main from './Main';
import Header from './Header';
import Footer from './Footer';
import Login from './Login';
import Registration from './employee/Registration';

import AllEmplyee from './employee/AllEmplyee';
import LeaveRequest from './employee/LeaveRequest';


export default function Index(props) {
    return (<>
        <BrowserRouter>
            <Header/>
            <Routes>
                <Route path="/" element={ <Main/> }/>
                <Route path="/login" element={ <Login/> }/>
                <Route path="/registration" element={ <Registration/> }/>
                <Route path="/AllEmplyee" element={ <AllEmplyee/> }/>
                <Route path="/dayoff" element={ <LeaveRequest/> }/>

            </Routes>
            <Footer/>
        </BrowserRouter>


    </>)
}