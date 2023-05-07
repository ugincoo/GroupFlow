import React from 'react';
import {BrowserRouter,Routes,Route} from 'react-router-dom';
import Main from './Main';
import Header from './Header';
import Footer from './Footer';
import Employees from './employee/Employees';

export default function Index(props) {
    return (<>
        <BrowserRouter>
            <Header/>
            <Routes>
                <Route path="/" element={ <Main/> }/>
                <Route path="/employees" element={ <Employees/> }/>

            </Routes>
            <Footer/>
        </BrowserRouter>


    </>)
}