import React from 'react';
import {BrowserRouter,Routes,Route} from 'react-router-dom';
import Main from './Main';
import Header from './Header';
import Footer from './Footer';
export default function Index(props) {
    return (<>
        <BrowserRouter>
            <Header/>
            <Routes>
                <Route path="/" element={ <Main/> }/>

            </Routes>
            <Footer/>
        </BrowserRouter>

    </>)
}