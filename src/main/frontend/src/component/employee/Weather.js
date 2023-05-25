import React,{useState,useEffect,useRef}from 'react';
import axios from 'axios';
export default function Weather(props) {
    console.log(props);
    console.log(props.tem);
    console.log(props.humidity);
    return (<>
       <div style={{fontFamily:"Roboto, Helvetica, Arial, sans-serif",color:"#dbdfeb",fontSize:"21px",fontWeight:"700px"}}
        > {props.tem+"°C"}</div>
       <div style={{fontFamily:"Roboto, Helvetica, Arial, sans-serif",color:"#dbdfeb",fontSize:"21px",fontWeight:"700px"}}
        > {props.humidity+"%"}</div>
    </>)
}