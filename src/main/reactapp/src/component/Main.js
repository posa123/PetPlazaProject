import axios from 'axios'; // axios
import { useState , useEffect } from 'react'; // react
export default function Main( props ){
    let [ newsData , setNewsData ] = useState([]);
    console.log(newsData)
    const getCrawling = (e) => {
        axios.get('/crawling/news').then( r => {
        console.log( r.data ); setNewsData(r.data);
        })
    }

    useEffect(()=>{
        getCrawling();
    },[])

    return (<>

        <div className="webcontainer">
            <div className="crawlingPage">
            <h3>최신뉴스</h3>
            <table className="unitTable" style={{ width: '100%' }}>
                <tr className="trText">
                    <th style={{ width:'35%'}}></th>
                    <th style={{ width:'35%'}}></th>
                  </tr>
             {
              newsData.map( (row) => (
                <tr>
                    <td style={{ width:'35%'}} ><a href={row.curl}><img src={row.cimg}/></a></td>
                    <td style={{ width:'35%'}} ><a href={row.curl}>{row.ctitle}</a></td>
                </tr>

                ))
             }
             </table>
             </div>
        </div>
    </>)

}