import React,{useState,useEffect} from 'react';
import axios from 'axios';

//----------------------------------------------------------------

export default function AllEmplyee(props) {
    let [allEmplyee,setAllEmplyee] = useState([]);


        //전직원 출력하기[관리자입장]
        useEffect( ()=>{
          axios
            .get("http://localhost:8080/employee")
            .then(r=>{console.log(r);
           //setAllEmplyee(r.data) <- 왜무한루프에 빠지는가..
            allEmplyee=r.data
            console.log(allEmplyee)
            })
        },[allEmplyee])




    return(<>
        <h3>전직원 출력 창 입니다.</h3>



    </>)





}