import { render, screen } from "@testing-library/react";
import ChangePasswordPopUp from "./ChangePasswordPopup";
import { Provider } from "react-redux";
import { createStore } from "redux";
import rootReducers from "../../reducers";

describe("ChangePasswordPopUp", () => {
  const store = createStore(rootReducers);

  test("if the div tag is present", () => {
    render(
      <Provider store={store}>
        <ChangePasswordPopUp />
      </Provider>
    );
    expect(screen.getByTestId("change-password")).toBeInTheDocument();
  });
});
