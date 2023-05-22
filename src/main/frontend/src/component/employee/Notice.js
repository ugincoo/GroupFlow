import React,{useState,useEffect} from 'react';
import axios from 'axios';
//---------------------------컨테이너-------------------------------------
import Container from '@mui/material/Container';

export default function Notice(props) {

const onWriteHandler=()=>{
        let content=document.querySelector('.content').value
        axios.post("/notice/noticepost",{content:content}).then(r=>{
        console.log(r.data);
        })
    }

    return(<>
        <Container>
          공지내용: <input type="text" className="content" />
          <button type="button" onClick={onWriteHandler}>등록 </button>
        </Container>
    </>)
}