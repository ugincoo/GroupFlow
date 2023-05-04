

import React,{useState,useEffect} from 'react';
import axios from 'axios';

//----------------------------------------------------------------

export default function allEmplyee(props) {
   // let [allEmplyee,setAllEmplyee] = useState([]);




        axios
        .get('http://localhost:8080/employee')
        .then( (r)=>{
        console.log(r);
        console.log(r.data);

        })








}