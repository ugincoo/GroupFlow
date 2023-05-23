import React,{useState,useEffect} from 'react';
import axios from 'axios';
//---------------------------컨테이너-------------------------------------
import Container from '@mui/material/Container';

export default function NoticePrint(props) {

const[nnotice,setNotice]=useState([]);
const getnotice=()=>{
     axios.get("/notice/noticeget").then((r) => {
        setNotice(r.data);

     })
     }//함수만 정의해놓은것

      useEffect(()=>{getnotice();},[])//페이지가 한번 열릴떄마다 실행
      
let [ login , setLogin ] = useState( JSON.parse(localStorage.getItem("login_token")) )

    return(<>
            {
                nnotice.map(r=>{
                    return( login!==null ?

                    <span style={{color: "black",fontWeight: "bold",backgroundColor: "aliceblue" ,marginRight:"20px"}}>{r.content}</span>
                   :""
                   )

                })

            }
    </>)
}