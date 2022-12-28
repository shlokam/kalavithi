import { render, screen } from "@testing-library/react";

import { Provider } from "react-redux";
import { createStore } from "redux";
import rootReducers from "../../reducers";
import AddToFavourite from "./AddToFavourite";

describe("Add to favourite", () => {
  const store = createStore(rootReducers);

  test("if the favourite div tag is present", () => {
    render(
      <Provider store={store}>
        <AddToFavourite />
      </Provider>
    );
    expect(screen.getByTestId("favourite-div")).toBeInTheDocument();
  });

  test("if the star icon title is present", () => {
    render(
      <Provider store={store}>
        <AddToFavourite />
      </Provider>
    );
    expect(screen.getByTitle("Add to Favorites")).toBeInTheDocument();
  });

  test("if the star status div is present", () => {
    render(
      <Provider store={store}>
        <AddToFavourite />
      </Provider>
    );
    expect(screen.getByTestId("active-status")).toBeInTheDocument();
  });
});
