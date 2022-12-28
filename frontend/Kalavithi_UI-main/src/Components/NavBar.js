import React, { useState } from "react";
import { useDispatch } from "react-redux";
import actions from "../actions";
import SearchBox from "../Components/SearchBox";
import Button from "./Button";
import KalaListHeading from "./kalaListHeading/KalaListHeading";
import LoginDisplay from "./LoginDisplay";
import RegistationPopUp from "./Registration/RegistationPopUp";
import './NavBar.css';
import Dropdown from 'react-bootstrap/Dropdown';

function NavBar() {
  const [state, setstate] = useState(localStorage.getItem("user"));

  const dispatch = useDispatch();

  let stateHamburger = 'hidden';

  React.useEffect(() => {
    window.addEventListener("storage", () => {
      const theme = localStorage.getItem("user");
      setstate(theme);
    });
  }, []);


  
const handleMenu = () =>{
  console.log("inside click")
  
  if (stateHamburger === 'active') {
    console.log("oncide if")
          document.querySelector('.hamburger-icon-content').style.display =
            'none';
          stateHamburger = 'hidden';
        } else {
          document.querySelector('.hamburger-icon-content').style.display =
            'flex';
          stateHamburger = 'active';
        }

}


  return (
    <>
    <div className= "navbar" data-testid="navbar">
      <div>
        <KalaListHeading></KalaListHeading>


      </div>
      <div>
        <SearchBox ></SearchBox>
      </div>
      <div className="nav-component" data-testid="button-div">
          {state === "false" ? <Button className="navbar-button" btnText="Login" handleClick={() => dispatch(actions.showLogInPopUp())}></Button> : null}
          {state === "false" ? <Button className="navbar-button" btnText="Register" handleClick={() => dispatch(actions.showRegisterPopUp())}></Button> : null}
          {state === "true" ? <LoginDisplay /> : null}
          {state === "false" ? <div className="hamburger-icon"  onClick={()=>handleMenu()}>
          <Dropdown>
          <Dropdown.Toggle style={{ background: "black" }} variant="dark" bg="dark" >
            <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 20 20"
            strokeWidth="1.5"
            stroke="currentColor"
            className="w-6 h-6"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5"
            />
          </svg>
          </Dropdown.Toggle>
          <Dropdown.Menu>
          <Dropdown.Item as="button" onClick={()=>{dispatch(actions.showLogInPopUp())}}>Login</Dropdown.Item>
          <Dropdown.Item as="button" onClick={()=>dispatch(actions.showRegisterPopUp())}>Register</Dropdown.Item>
        </Dropdown.Menu>
          </Dropdown>
        </div>
         : null}
     </div>
    </div>
   
      <RegistationPopUp></RegistationPopUp>
    </>
  );
}

export default NavBar;
