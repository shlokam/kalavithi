import { render, screen } from "@testing-library/react";
import { createStore } from "redux";
import rootReducers from "../reducers";
import React from "react";
import { Provider } from "react-redux";
import Button from "./Button";

describe("Button", () => {
  test("should have button", () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <Button />
      </Provider>
    );
    const linkElement = screen.getByRole("button");
    expect(linkElement).toBeInTheDocument();
  });
});
