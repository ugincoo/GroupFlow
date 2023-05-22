import React, { useEffect } from 'react';
import axios from 'axios';


export default function Logout(props) {

    useEffect(()=>{
        localStorage.clear();
        window.location.href="/employee/logout";

    },[])

}