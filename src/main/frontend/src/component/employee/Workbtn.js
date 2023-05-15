import React,{useState,useEffect} from 'react';
import axios from 'axios';
export default function Workbtn(props) {


const [ gokDisabled, setGokDisabled] = useState(false);
console.log("gokDisabled")
console.log(gokDisabled)

    const gowork=()=>{

    axios.post("/employee/gowork").then((r)=>{
            console.log(r.data)
            if(r.data==true){
                alert("출근등록 되었습니다.")
                //document.querySelector('.goworkbtn').disabled=true;
                setGokDisabled(true);
            }
         })
       }
const outwork=()=>{
        axios.put("/employee/outwork").then((r)=>{
            console.log(r.data);
            if(r.data==true){alert("퇴근등록 되었습니다.");
            setGokDisabled(false);
            //document.querySelector('.outworkbtn').disabled=true;

            }

        })

    }
    return(<>
            <button
                type="button"
                className="goworkbtn"
               disabled={gokDisabled ? true : false}
                onClick={gowork}

                 > 출근 </button>

            <button
                type="button"  className="outworkbtn"
                disabled={gokDisabled ? true : false}
                onClick={outwork}  > 퇴근 </button>
    </>)
 }
