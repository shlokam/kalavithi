import axios from "axios";
import React, { useEffect, useState } from 'react';
import './Comment.css';


export default function Comment(props){

const SECONDS_MILLS=1000;
const MINUTE_MILLS=60*SECONDS_MILLS;
const HOURS_MILLS=60*MINUTE_MILLS;
const DAY_MILLS=24*HOURS_MILLS;

const currentTime=new Date().getTime();
let timeStatus;


  const [comment,setComment] = useState('');

  

  const [showComment,setShowComment] = useState([]);
  const [showUser,setShowUser] = useState([]);

  const [error,setError]=useState('');
  

    const headers = {
        "content-type": "application/json",
        Authorization: "Basic " + localStorage.getItem("authenticationToken"),
      };

      const imageId=props.imageId;
      
      const onChangeComment = (event) => {
      
        setComment(event.target.value);
        
        
      };

      async function getUser(){
        
        try{
          await axios 
          .get("https://kalavithi-service-team-01-test.herokuapp.com/api/users")
         // .get("http://localhost:8081/api/users")
          .then((response)=>{
            for(let i=0;i<response.data.length;i++){
              
              setShowUser(current => [...current,{id:response.data[i].id,email:response.data[i].email}]);
             
            }

          })
        }
        catch(error){}
      }


      const getComment = async(e) =>{
    try {
        await axios
          .get(
            "https://kalavithi-service-team-01-test.herokuapp.com/api/images/comments/"+imageId,
           // "http://localhost:8081/api/images/comments/" + imageId,
            { headers }
          )
          .then((response) => {
           
            getUser()  
            for(let i=response.data.length-1;i>=0;i--){
              var diff = currentTime - response.data[i].commentTime
              if(diff<MINUTE_MILLS){
                timeStatus="just now"
                console.log("just now")
              }
              else if(diff<2*MINUTE_MILLS){
                timeStatus="a minute ago"
                console.log("a minute ago")
              }
              else if(diff<50*MINUTE_MILLS){
                timeStatus=String(Number.parseInt(diff/MINUTE_MILLS))+ " minutes ago";
                console.log(String(Number.parseInt(diff/MINUTE_MILLS))+ " minutes ago")
              }
              else if(diff<90*MINUTE_MILLS){
                timeStatus="a hour ago";
                console.log("a hour ago")
              }
              else if(diff<24*HOURS_MILLS){
                timeStatus=String(Number.parseInt(diff/HOURS_MILLS)) + " hour ago"
                console.log(String(Number.parseInt(diff/HOURS_MILLS)) + " hour ago")
              }
              else{
                timeStatus=String(Number.parseInt(diff/DAY_MILLS)) + " days ago"
                console.log(String(Number.parseInt(diff/DAY_MILLS)) + " days ago")
              }
             
              setShowComment(current => [...current,{id:response.data[i].userId,comment:response.data[i].comment,time:timeStatus}]);
             
            }
          });
      } catch (error) {
        
      }
      
    }


    const handleButtonClick = async(event)=>{
      setError("")
      try {
        await axios
          .post(
            "https://kalavithi-service-team-01-test.herokuapp.com/api/images/comments",
          //"http://localhost:8081/api/images/comments",
            {
              userId: localStorage.getItem("id"),
              imageId: props.imageId,
              comment: comment,
            },
            { headers }
          )
          .then((response) => {           
            setShowUser([])
            setShowComment([])
            getComment()
            event.target.reset();
            
            
          });
      } catch (error) {
        setError(error.response.data.message)
        setComment('')
      } finally{
        setComment("")
      }
    }
    useEffect(() => {
            setShowUser([])
            setShowComment([])
            getComment()   
            setError("")
    }, [props.imageId])


    return(
    
        <div>
          <hr></hr>
          
              <input className = "comment-input" type="text" id="fname" name="firstname" maxlength="50" placeholder="Add a Comment" 
              onChange={event=>setComment(event.target.value)} value={comment}> 
              </input>
              
             
              <svg xmlns="http://www.w3.org/2000/svg" onClick={()=>handleButtonClick()} width="30" height="30" fill="currentColor" class="bi bi-send send-icon" viewBox="0 0 16 16" id="IconChangeColor" transform="rotate(45)"> 
                <path d="M15.854.146a.5.5 0 0 1 .11.54l-5.819 14.547a.75.75 0 0 1-1.329.124l-3.178-4.995L.643 7.184a.75.75 0 0 1 .124-1.33L15.314.037a.5.5 0 0 1 .54.11ZM6.636 10.07l2.761 4.338L14.13 2.576 6.636 10.07Zm6.787-8.201L1.591 6.602l4.339 2.76 7.494-7.493Z" id="mainIconPathAttribute" 
                >
                  </path> 
              </svg>
              <p style={{color:"red"}}>{error}</p>
      
            {showComment.map((elementComment,index)=>{
                
                return (<div key = {index}>
                 
                  {showUser.map((elementUser,indexUser)=>{
                    
                    if(elementComment.id===elementUser.id && String(elementUser.id)===localStorage.getItem("id")){
                    
                      return(<div>
                        <p className="display-username"><b>{elementUser.email}</b> {elementComment.comment} </p>
                        <p className="display-time">{elementComment.time}</p>
                      </div>)                    
                      
                    }
                    
                  })}
                  </div>)
                
              })}

              
              {showComment.map((elementComment,index)=>{
                
                return (<div key = {index}>
                 
                  {showUser.map((elementUser,indexUser)=>{
                    
                    if(elementComment.id===elementUser.id && String(elementUser.id)!=localStorage.getItem("id") ){
                    
                      return(<div>
                        <p className="display-username"><b>{elementUser.email}</b> {elementComment.comment}</p>
                        <p className="display-time">{elementComment.time}</p>
                      </div>)                    

                      
                    }
                    
                  })}
                  </div>)
                
              })}

             

             
              


        </div>
    )
}



