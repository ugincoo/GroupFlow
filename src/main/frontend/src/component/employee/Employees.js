import React,{useState,useEffect} from 'react';
import axios from 'axios';

//----------------------------------------------------------------

export default function AllEmplyee(props) {
   // let [allEmplyee,setAllEmplyee] = useState([]);

        axios
        .get("http://localhost:8080/employee")
        .then(r=>{
        console.log(r);
        console.log(r.data);

        })


    return(<>
        <h3>전직원 출력 창 입니다.</h3>

    </>)





}