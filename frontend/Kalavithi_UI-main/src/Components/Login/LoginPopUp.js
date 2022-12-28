import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import {
  default as DialogContent,
  default as DialogContentText,
} from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";

import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import actions from "../../actions";

import InputLabel from "@mui/material/InputLabel";

import InputAdornment from "@mui/material/InputAdornment";
import IconButton from "@mui/material/IconButton";

import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";

import "./Login.css";

import FormHelperText from "@mui/material/FormHelperText";
import Input from "@mui/material/Input";

export default function LoginPopUp(props) {
  const [username, setusername] = useState("");
  const [password, setPassword] = useState("");
  const [authenticationToken, setAuthenticationToken] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [errorusernameMessage, setErrorusernameMessage] = useState("");
  const [isRevealPwd, setIsRevealPwd] = useState(false);
  const [errorPasswordMessage, setErrorPasswordMessage] = useState("");

  const [alertShow, setAlertShow] = useState(false); //alert
  const [responseusernameExist, setResponseusernameExist] = useState(""); //exist
  const [responseData, setResponseData] = useState(-1);

  const showLoginPopUp = useSelector(
    (state) => state.HandleLoginInPopUp.handleLoginInPopUp
  );

  let base64 = require("base-64");
  const dispatch = useDispatch();

  const handleClose = () => props.showState(false);

  const handleAlertClose = () => {
    setAlertShow(false);
  };

  const usernameRegex = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;
  const phoneRegex = /^[6-9]\d{9}$/;
  const passwordRegex =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=]).{8,15}$/;
  const validToken = base64.encode(username + ":" + password);

  const onChangeusername = (event) => {
    setusername(event.target.value);
    if (event.target.value.match(usernameRegex)) {
      setErrorusernameMessage("");
      setusername(event.target.value);
    } else {
      setErrorusernameMessage("Invalid Email");
    }
  };

  const handleAlertShow = () => setAlertShow(true);

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
    if (username === "" || password === "") {
      setErrorMessage("Please Enter Required Field");
      return true;
    } else {
      setErrorMessage("");
      return false;
    }
  };

  let handleClick = async (e) => {
    e.preventDefault();

    try {
       //let res = await fetch(`http://localhost:8081/api/users/login`,
       let res = await fetch(
         `https://kalavithi-service-team-01-test.herokuapp.com/api/users/login`,


        {
          method: "GET",
          headers: new Headers({
            Authorization: "Basic " + base64.encode(username + ":" + password),
            "Content-Type": "application/json",
            Accept: "application/json",
          }),


          params: {
            username: username,
            password: password,
          },
        }
      );
      let resJson = await res.json();

      if (res.status === 200) {
        setusername(username);

        localStorage.setItem("username", resJson.username);
        localStorage.setItem("id", resJson.id);
        localStorage.setItem("user", "true");
        localStorage.setItem("authenticationToken", validToken);
        window.dispatchEvent(new Event("storage"));

        dispatch(actions.hideLogInPopUp());
      } else {
        setAlertShow(true);
        setPassword("");
        setusername("");
      }
    } catch (err) {}
  };
  return (
    <>
      <div className="pop-up" data-testid="login_div">
        <Dialog
          data-testid="login_dialog"
          open={showLoginPopUp}
          sx={{
            "& .MuiDialog-container": {
              "& .MuiPaper-root": {
                width: "100%",
                maxWidth: "500px", 
              },
            },
          }}
        >
          <DialogTitle>Login</DialogTitle>
          <DialogContent>
            <div className="input-field">
              <InputLabel className="required" style={{ marginBottom: "2%" }}>
                Email
              </InputLabel>
              <Input
                autoFocus
                autoComplete="off"
                error={errorusernameMessage.length > 0}
                margin="dense"
                name="username"
                value={username}
                label="Email"
                type="username"
                fullWidth
                variant="standard"
                onChange={onChangeusername}
                placeholder="Enter your email."
                required
              />
              <FormHelperText style={{ color: "red" }}>
                {errorusernameMessage}
              </FormHelperText>
            </div>

            <InputLabel className="required" style={{ marginBottom: "2%" }}>
              Password
            </InputLabel>
            <Input
              id="standard-adornment-password"
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
              inputProps={{
                "data-testid": "password_icon",
              }}
              endAdornment={
                <InputAdornment position="end">
                  <IconButton
                    data-testid="password_icon"
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

          <DialogActions>
            <Button onClick={() => dispatch(actions.hideLogInPopUp())}>
              Close
            </Button>
            <Button
              disabled={
                !(
                  username &&
                  password &&
                  !errorusernameMessage &&
                  !errorPasswordMessage
                )
              }
              onClick={handleClick}
            >
              Submit
            </Button>
          </DialogActions>
        </Dialog>
      </div>
      <div>
        <Dialog open={alertShow} onClose={handleAlertClose}>
          <DialogContentText>Invalid Credentials</DialogContentText>
          <DialogActions>
            <Button onClick={handleAlertClose}>Close</Button>
          </DialogActions>
        </Dialog>
      </div>
    </>
  );
}
