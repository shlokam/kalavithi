import { render, screen, fireEvent } from "@testing-library/react";

import { Provider } from "react-redux";
import { createStore } from "redux";
import rootReducers from "../../reducers";
import LikeAnImage from "./LikeAnImage";

describe("Add to favourite", () => {
  const store = createStore(rootReducers);

  test("if the heart div tag is present", () => {
    render(
      <Provider store={store}>
        <LikeAnImage />
      </Provider>
    );
    expect(screen.getByTestId("heart-div")).toBeInTheDocument();
  });

  test("if the button is present", () => {
    render(
      <Provider store={store}>
        <LikeAnImage />
      </Provider>
    );
    expect(screen.getByRole("button")).toBeInTheDocument();
  });

  test("if the star like image div is present", () => {
    render(
      <Provider store={store}>
        <LikeAnImage />
      </Provider>
    );
    expect(screen.getByTestId("like-image-div")).toBeInTheDocument();
  });
});
