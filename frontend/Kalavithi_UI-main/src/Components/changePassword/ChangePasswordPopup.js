import { useState } from "react";
import React from "react";
import Button from "@mui/material/Button";

import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import axios from "axios";
import InputLabel from "@mui/material/InputLabel";
import FormHelperText from "@mui/material/FormHelperText";
import { useDispatch, useSelector } from "react-redux";
import actions from "../../actions";
import { Input } from "@mui/material";

import InputAdornment from "@mui/material/InputAdornment";
import IconButton from "@mui/material/IconButton";

import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";

export default function ChangePasswordPopUp(props) {
  const [isRevealCurrentPwd, setIsRevealCurrentPwd] = useState(false);
  const [isRevealNewPwd, setIsRevealNewPwd] = useState(false);
  const [isRevealConfirmNewPwd, setIsRevealConfirmNewPwd] = useState(false);

  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmNewPassword, setConfirmNewPassword] = useState("");
  const [
    errorIncorrectCurrentPasswordMessage,
    setErrorIncorrectCurrentPasswordMessage,
  ] = useState("");
  const [errorInvalidNewPasswordMessage, setErrorInvalidNewPasswordMessage] =
    useState("");
  const [
    errorIncorrectConfirmNewPasswordMessage,
    setErrorIncorrectConfirmNewPasswordMessage,
  ] = useState("");

  const [alertShow, setAlertShow] = useState(false); //alert
  const [
    responseIncorrectCurrentPassword,
    setResponseIncorrectCurrentPassword,
  ] = useState(""); //exist
  const [responseData, setResponseData] = useState(-1);

  const showPopUp = useSelector(
    (state) => state.HandleChangePasswordPopup.handleChangePasswordPopup
  );
  const dispatch = useDispatch();



  const handleAlertClose = () => {
    setAlertShow(false);
  };

  const passwordRegex =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$%^&+=]).{8,15}$/;

  const handleAlertShow = () => setAlertShow(true);

  const onChangeNewPassword = (event) => {
    setNewPassword(event.target.value);
    if (event.target.value.match(passwordRegex)) {
      setErrorInvalidNewPasswordMessage("");
      setNewPassword(event.target.value);
    } else {
      setErrorInvalidNewPasswordMessage("Invalid New Password");
    }
  };

  const onChangeConfirmNewPassword = (event) => {
    setConfirmNewPassword(event.target.value);
    if (event.target.value.match(newPassword)) {
      if (event.target.value.match(passwordRegex)) {
        setErrorIncorrectConfirmNewPasswordMessage("");
        setConfirmNewPassword(event.target.value);
      }
    } else {
      setErrorIncorrectConfirmNewPasswordMessage(
        "Confirmed New Password doesn't match with New Password "
      );
    }
  };

  let isEmpty = () => {
    if (
      currentPassword === "" ||
      newPassword === "" ||
      confirmNewPassword === ""
    ) {
      return true;
    }
  };

  let handleClick = async (e) => {
    if (isEmpty()) {
    } else {
      const headers = {
        "content-type": "application/json",
        Authorization: "Basic " + localStorage.getItem("authenticationToken"),
      };

      try {
        const data = await axios
          .put(

            "https://kalavithi-service-team-01-test.herokuapp.com/api/users",
           

            {
              id: localStorage.getItem("id"),
              currentPassword: currentPassword,
              newPassword: newPassword,
              confirmNewPassword: confirmNewPassword,
            },

            { headers }
          )
          .then((response) => {
            if (response.data.id > 0) {
              setResponseData(response.data.id);
              dispatch(actions.hideChangePasswordPopUp());
              setResponseIncorrectCurrentPassword(
                "Password Updated Successfully"
              );
              setCurrentPassword("");
              setNewPassword("");
              setConfirmNewPassword("");
              handleAlertShow();
            }
          });
      } catch (error) {
        setResponseData(error.response.data.message);
        setResponseIncorrectCurrentPassword(error.response.data.message);
        setCurrentPassword("");
        setNewPassword("");
        setConfirmNewPassword("");
        handleAlertShow();
      }
    }
  };
  return (
    <>
      <div data-testid="change-password">
        <Dialog open={showPopUp} onClose={props.onClose}>
          <DialogTitle>Change Password</DialogTitle>
          <DialogContent>
            <div
              className="input-field"
              data-testid="input-div-current-password"
            >
              <InputLabel
                date-testid="input-label-current-password"
                className="required"
                style={{ marginBottom: "2%" }}
              >
                Current Password
              </InputLabel>
              <Input
                autoFocus
                autoComplete="off"
                error={errorIncorrectCurrentPasswordMessage.length > 0}
                margin="dense"
                name="currentPassword"
                value={currentPassword}
                label="Current Password"
                type={isRevealCurrentPwd ? "text" : "password"}
                fullWidth
                variant="standard"
                onChange={(e) => setCurrentPassword(e.target.value)}
                placeholder="Enter your current password"
                required
                endAdornment={
                  <InputAdornment position="end">
                    <IconButton
                      aria-label="toggle password visibility"
                      onClick={() =>
                        setIsRevealCurrentPwd((prevState) => !prevState)
                      }
                    >
                      {isRevealCurrentPwd ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                }
              />
              <FormHelperText style={{ color: "red" }}>
                {errorIncorrectCurrentPasswordMessage}
              </FormHelperText>
            </div>
            <div className="input-field">
              <InputLabel className="required" style={{ marginBottom: "2%" }}>
                New Password
              </InputLabel>
              <Input
                autoComplete="off"
                error={errorInvalidNewPasswordMessage.length > 0}
                margin="dense"
                name="newPassword"
                value={newPassword}
                label="New Password"
                type={isRevealNewPwd ? "text" : "password"}
                fullWidth
                variant="standard"
                onChange={onChangeNewPassword}
                placeholder="Enter your new password"
                required
                endAdornment={
                  <InputAdornment position="end">
                    <IconButton
                      aria-label="toggle password visibility"
                      onClick={() =>
                        setIsRevealNewPwd((prevState) => !prevState)
                      }
                    >
                      {isRevealNewPwd ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                }
              />
              <FormHelperText style={{ color: "red" }}>
                {errorInvalidNewPasswordMessage}
              </FormHelperText>
            </div>

            <InputLabel className="required" style={{ marginBottom: "2%" }}>
              Confirm New Password
            </InputLabel>
            <Input
              autoComplete="off"
              error={errorIncorrectConfirmNewPasswordMessage.length > 0}
              margin="dense"
              name="confirmNewPassword"
              value={confirmNewPassword}
              label="Confirm New Password"
              type={isRevealConfirmNewPwd ? "text" : "password"}
              fullWidth
              variant="standard"
              onChange={onChangeConfirmNewPassword}
              placeholder="Enter your confirm new password"
              required
              endAdornment={
                <InputAdornment position="end">
                  <IconButton
                    aria-label="toggle password visibility"
                    onClick={() =>
                      setIsRevealConfirmNewPwd((prevState) => !prevState)
                    }
                  >
                    {isRevealConfirmNewPwd ? <VisibilityOff /> : <Visibility />}
                  </IconButton>
                </InputAdornment>
              }
            />
            <FormHelperText style={{ color: "red" }}>
              {errorIncorrectConfirmNewPasswordMessage}
            </FormHelperText>
          </DialogContent>
          <DialogContentText>
            Password must have atleast one Upper Case, one Lower Case, one
            Number and one Special Character. Length should be in range of 8-15.
          </DialogContentText>
          <DialogActions>
            <Button onClick={() => dispatch(actions.hideChangePasswordPopUp())}>
              Close
            </Button>
            <Button
              disabled={
                !(
                  currentPassword &&
                  newPassword &&
                  confirmNewPassword &&
                  !errorIncorrectCurrentPasswordMessage &&
                  !errorInvalidNewPasswordMessage &&
                  !errorIncorrectConfirmNewPasswordMessage
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
          <DialogTitle>{responseIncorrectCurrentPassword}</DialogTitle>
          <DialogActions>
            <Button onClick={handleAlertClose}>Close</Button>
          </DialogActions>
        </Dialog>
      </div>
    </>
  );
}
