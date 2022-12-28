import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogTitle from "@mui/material/DialogTitle";
import React from "react";
import { useDispatch, useSelector } from "react-redux";
import actions from "../actions";

export default function PopUpModal(props) {
  const status = useSelector(
    (state) => state.HandleImagePopup.handleImagePopup
  );
  const dispatch = useDispatch();

  return (
    <div data-testid="pop-up-modal-div">
      <Dialog open={status} aria-labelledby="responsive-dialog-title">
        <DialogTitle id="responsive-dialog-title">
          {"Please login/register"}
        </DialogTitle>
        <DialogActions>
          <Button
            autoFocus
            onClick={() => {
              dispatch(actions.hideModal());
              dispatch(actions.showRegisterPopUp());
            }}
          >
            Register
          </Button>
          <Button
            autoFocus
            onClick={() => {
              dispatch(actions.hideModal());
              dispatch(actions.showLogInPopUp());
            }}
          >
            Login
          </Button>
          <Button autoFocus onClick={() => dispatch(actions.hideModal())}>
            Close
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
