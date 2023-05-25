import React , { useEffect ,useState } from 'react';
import axios from 'axios';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Doughnut } from 'react-chartjs-2';

ChartJS.register(ArcElement, Tooltip, Legend);



export default function Chart() {

    const [ data , setData ] = useState({
      labels: [],
      datasets: [
        {
            label: '# of Votes',
            data: [],
            backgroundColor: [
              'rgba(255, 99, 132, 0.2)',      // 붉은색
              'rgba(54, 162, 235, 0.2)',      // 파란색
              'rgba(255, 206, 86, 0.2)',      // 주황색
              'rgba(75, 192, 192, 0.2)',      // 청록색
              'rgba(153, 102, 255, 0.2)',     // 보라색
              'rgba(255, 159, 64, 0.2)',      // 주황색2
              'rgba(201, 116, 173, 0.2)',     // 분홍색
              'rgba(97, 173, 231, 0.2)',      // 연한 파란색
              'rgba(223, 181, 74, 0.2)',      // 갈색
              'rgba(116, 201, 156, 0.2)'      // 연두색
            ],
            borderColor: [
              'rgba(255, 99, 132, 1)',        // 붉은색
              'rgba(54, 162, 235, 1)',        // 파란색
              'rgba(255, 206, 86, 1)',        // 주황색
              'rgba(75, 192, 192, 1)',        // 청록색
              'rgba(153, 102, 255, 1)',       // 보라색
              'rgba(255, 159, 64, 1)',        // 주황색2
              'rgba(201, 116, 173, 1)',       // 분홍색
              'rgba(97, 173, 231, 1)',        // 연한 파란색
              'rgba(223, 181, 74, 1)',        // 갈색
              'rgba(116, 201, 156, 1)'        // 연두색
            ],
            borderWidth: 1,
        },
      ],
    });
    console.log(data)

    useEffect(() => {
        axios.get("/evaluation/chart").then(r=>{
            console.log(r.data)
            r.data.forEach(r=>{
                data.labels.push(r.eqtitle)
                data.datasets[0].data.push(r.eqscore)
            })
        })

        setData({...data})
    },[])

  return <Doughnut data={data} />;
}
