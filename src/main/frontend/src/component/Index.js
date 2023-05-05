import React from 'react';
import {BrowserRouter,Routes,Route} from 'react-router-dom';
import Main from './Main';
import Header from './Header';
import Footer from './Footer';
import Login from './Login';

export default function Index(props) {
    return (<>
        <BrowserRouter>
            <Header/>
            <Routes>
                <Route path="/" element={ <Main/> }/>
                <Route path="/login" element={ <Login/> }/>
            </Routes>
            <Footer/>
        </BrowserRouter>

    </>)
}