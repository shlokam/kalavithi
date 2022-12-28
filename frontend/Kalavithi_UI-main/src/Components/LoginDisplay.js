import Dropdown from "react-bootstrap/Dropdown";
import { useDispatch } from "react-redux";
import actions from "../actions";
import ChangePasswordPopUp from "./changePassword/ChangePasswordPopup";
import "./LoginDisplay.css";
import LogoutPopup from "./LogoutPopup";

import React from 'react';

function LoginDisplay(){
  const dispatch = useDispatch();


  const handleChangePassword = () => {
    dispatch(actions.showChangePasswordPopUp());
  };

  return (

    <div
      style={{
       position:"relative",
        color: "white",
        marginRight: "15%",
      }}
    >
      <Dropdown style={{ width: "60px" }} data-testid="dropdown-list">
        <Dropdown.Toggle
          data-testid="dropdown-toggle"
          style={{ background: "black" }}
          variant="dark"
          bg="dark"
        >

          <img
            src="profile_icon.png"
            alt="profile_icon"
            height="40vh"
            width="40vw"
          ></img>
        </Dropdown.Toggle>

        <Dropdown.Menu data-testid="dropdown-menu">
          <Dropdown.Item
            as="button"
            onClick={() => handleChangePassword()}
            data-testid="change-password-button"
          >
            Change Password
          </Dropdown.Item>
          <Dropdown.Item
            as="button"
            onClick={() => {
              dispatch(actions.showLogoutPopUp());
            }}
            data-testid="logout-button"
          >
            Logout
          </Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
      <ChangePasswordPopUp></ChangePasswordPopUp>
      <LogoutPopup></LogoutPopup>
    </div>
  );
}
export default LoginDisplay;
