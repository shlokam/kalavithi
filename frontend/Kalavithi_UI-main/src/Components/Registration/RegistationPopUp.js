import axios from "axios";
import { useState } from "react";
import React from "react";
import Button from "@mui/material/Button";

import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import LoginPopUp from "../Login/LoginPopUp";
import { useDispatch, useSelector } from "react-redux";
import actions from "../../actions";

import FormHelperText from "@mui/material/FormHelperText";

import Input from "@mui/material/Input";

import InputLabel from "@mui/material/InputLabel";

import InputAdornment from "@mui/material/InputAdornment";
import IconButton from "@mui/material/IconButton";

import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";

export default function RegistationPopUp(props) {
  const [email, setEmail] = useState("");
  const [mobile_number, setMobileNumber] = useState("");
  const [password, setPassword] = useState("");

  const [isRevealPwd, setIsRevealPwd] = useState(false);

  const [errorMessage, setErrorMessage] = useState("");
  const [errorEmailMessage, setErrorEmailMessage] = useState("");
  const [errorMobileNumberMessage, setErrorMobileNumberMessage] = useState("");
  const [errorPasswordMessage, setErrorPasswordMessage] = useState("");
 
  const [alertShow, setAlertShow] = useState(false); //alert
  const [responseEmailExist, setResponseEmailExist] = useState(""); //exist
  const [responseData, setResponseData] = useState(-1);
 
  const showPopUp = useSelector(
    (state) => state.HandleRegisterPopUp.handleRegisterPopUp
  );
  const dispatch = useDispatch();

  

  const handleAlertClose = () => {
    setAlertShow(false);
    if (responseData > 0) {
      dispatch(actions.showLogInPopUp());
    } else if (responseData === "User Already Exists") {
      props.showState(true);
    }
  };
  const emailRegex = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;
  const phoneRegex = /^[6-9]\d{9}$/;
  const passwordRegex =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=]).{8,15}$/;

  const onChangeEmail = (event) => {
    setEmail(event.target.value);
    if (event.target.value.match(emailRegex)) {
      setErrorEmailMessage("");
      setEmail(event.target.value);
    } else {
      setErrorEmailMessage("Invalid Email ");
    }
  };

  const handleAlertShow = () => setAlertShow(true);
  const onChangePhone = (event) => {
    setMobileNumber(event.target.value);
    if (event.target.value.match(phoneRegex)) {
      setErrorMobileNumberMessage("");
      setMobileNumber(event.target.value);
    } else {
      setErrorMobileNumberMessage("Invalid Mobile Number");
    }
  };
  const onChangePassword = (event) => {
    setPassword(event.target.value);
    if (event.target.value.match(passwordRegex)) {
      setErrorPasswordMessage("");
      setPassword(event.target.value);
    } else {
      setErrorPasswordMessage("Invalid Password");
    }
  };

  let isEmpty = () => {
    if (email === "" || mobile_number === "" || password === "") {
      setErrorMessage("Please Enter Required Field");
      return true;
    } else {
      setErrorMessage("");
      return false;
    }
  };

  let handleClick = async (e) => {
    if (isEmpty()) {
      setErrorMessage("Please Enter Required Field");
    } else {
      try {

         await axios
          .post(

//             //"https://kalavithi-service-team-01-dev.herokuapp.com/api/users",
//             "http://localhost:8081/api/users",


            "https://kalavithi-service-team-01-test.herokuapp.com/api/users",
           // "http://localhost:8081/api/users",


            {
              email: email,
              mobile_number: mobile_number,
              password: password,
            }
          )
          .then((response) => {
            if (response.data.id > 0) {
              setResponseData(response.data.id);
              dispatch(actions.hideRegisterPopUp());
              setResponseEmailExist("Registration Successful");
              setEmail("");
              setMobileNumber("");
              setPassword("");
              handleAlertShow();
            }
          });
      } catch (error) {
        setResponseData(error.response.data.message);
        setResponseEmailExist("User Already Registered");
        setEmail("");
        setMobileNumber("");
        setPassword("");
        handleAlertShow();
      }
    }
  };
  return (
    <>
      <div>
        <Dialog open={showPopUp} onClose={props.onClose}>
          <DialogTitle>Register</DialogTitle>
          <DialogContent>
            <div className="input-field">
              <InputLabel className="required" style={{ marginBottom: "2%" }}>
                Email
              </InputLabel>
              <Input
                autoFocus
                autoComplete="off"
                error={errorEmailMessage.length > 0}
                margin="dense"
                name="email"
                value={email}
                label="Email Address"
                type="email"
                fullWidth
                variant="standard"
                onChange={onChangeEmail}
                placeholder="Enter your email."
                required
              />
              <FormHelperText style={{ color: "red" }}>
                {errorEmailMessage}
              </FormHelperText>
            </div>
            <div className="input-field">
              <InputLabel className="required" style={{ marginBottom: "2%" }}>
                Mobile Number
              </InputLabel>
              <Input
                autoComplete="off"
                error={errorMobileNumberMessage.length > 0}
                margin="dense"
                name="mobile_number"
                value={mobile_number}
                label="Mobile Number"
                type="text"
                fullWidth
                variant="standard"
                onChange={onChangePhone}
                placeholder="Enter your mobile number."
                required
              />
              <FormHelperText style={{ color: "red" }}>
                {errorMobileNumberMessage}
              </FormHelperText>
            </div>

            <InputLabel className="required" style={{ marginBottom: "2%" }}>
              Password
            </InputLabel>
            <Input
              type={isRevealPwd ? "text" : "password"}
              value={password}
              autoComplete="off"
              error={errorPasswordMessage.length > 0}
              margin="dense"
              name="password"
              label="Password"
              fullWidth
              variant="standard"
              onChange={onChangePassword}
              placeholder="Enter your password."
              required
              endAdornment={
                <InputAdornment position="end">
                  <IconButton
                    aria-label="toggle password visibility"
                    onClick={() => setIsRevealPwd((prevState) => !prevState)}
                  >
                    {isRevealPwd ? <VisibilityOff /> : <Visibility />}
                  </IconButton>
                </InputAdornment>
              }
            />

            <FormHelperText style={{ color: "red" }}>
              {errorPasswordMessage}
            </FormHelperText>
          </DialogContent>
          <DialogContentText>
            Password must have atleast one Upper case, one Lower case, one
            number and one Special Character. Length should be in range of 8-15
          </DialogContentText>
          <DialogActions>
            <Button onClick={() => dispatch(actions.hideRegisterPopUp())}>
              Close
            </Button>
            <Button
              disabled={
                !(
                  email &&
                  password &&
                  mobile_number &&
                  !errorEmailMessage &&
                  !errorMobileNumberMessage &&
                  !errorPasswordMessage
                )
              }
              onClick={handleClick}
            >
              Sign In
            </Button>
          </DialogActions>
        </Dialog>
      </div>
      <div>
        <Dialog open={alertShow} onClose={handleAlertClose}>
          <DialogTitle>{responseEmailExist}</DialogTitle>

          <DialogActions>
            <Button onClick={handleAlertClose}>Close</Button>
          </DialogActions>
        </Dialog>
        <LoginPopUp></LoginPopUp>
      </div>
    </>
  );
}
