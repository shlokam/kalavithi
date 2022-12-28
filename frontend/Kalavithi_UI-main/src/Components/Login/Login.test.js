import {
  render,
  screen,
  within,
  fireEvent,
  waitFor,
} from "@testing-library/react";
import LoginPopUp from "./LoginPopUp";

import { createStore } from "redux";
import rootReducers from "../../reducers";
import React from "react";
import { Provider } from "react-redux";
import Home from "../../pages/Home";

describe("LoginPopup", () => {
  test("should be able to find the login div by id", () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <LoginPopUp />
      </Provider>
    );
    expect(within(document.body).getByTestId("login_div")).toBeInTheDocument();
  });
});
