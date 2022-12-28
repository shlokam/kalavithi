import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogTitle from "@mui/material/DialogTitle";
import React from "react";
import { useDispatch, useSelector } from "react-redux";
import actions from "../actions";

export default function LogoutPopup() {
  const status = useSelector(
    (state) => state.HandleLogoutPopUp.handleLogoutPopUp
  );
  const dispatch = useDispatch();

    const handleLogout = () => {
        dispatch(actions.hideLogoutPopUp())
        try {


//             // let res = fetch(`https://kalavithi-service-team-01-dev.herokuapp.com/api/users/logout`,
//                 let res = fetch(`http://localhost:8081/api/users/logout`,


             let res = fetch(`https://kalavithi-service-team-01-test.herokuapp.com/api/users/logout`,
             //  let res = fetch(`http://localhost:8081/api/users/logout`,


                {
                    method: "GET",
                });
            localStorage.setItem('username', "null");
            localStorage.setItem('id', "null")
            localStorage.setItem('user', "false")
            window.dispatchEvent(new Event("storage"));

    } catch (err) {}
  };

  return (
    <div data-testid="logout-div">
      <Dialog open={status} aria-labelledby="responsive-dialog-title">
        <DialogTitle id="responsive-dialog-title">
          {"Do you want to Logout ?"}
        </DialogTitle>
        <DialogActions>
          <Button
            autoFocus
            onClick={() => {
              handleLogout();
            }}
          >
            Yes
          </Button>
          <Button autoFocus onClick={() => dispatch(actions.hideLogoutPopUp())}>
            No
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
