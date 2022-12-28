import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { createStore } from "redux";
import rootReducers from "../reducers";
import React from "react";
import { Provider } from "react-redux";
import LoginDisplay from "./LoginDisplay";

describe("LoginDisplay", () => {
  test("should be able to find dropdown via id.", () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <LoginDisplay />
      </Provider>
    );
    const linkElement = screen.getByTestId("dropdown-list");
    expect(linkElement).toBeInTheDocument();
  });

  test("should be able to find img tag", () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <LoginDisplay />
      </Provider>
    );

    const logo = screen.getByRole("img");
    expect(logo).toHaveAttribute("src", "profile_icon.png");
    expect(logo).toHaveAttribute("alt", "profile_icon");
  });

  test("should be able to find Change Password Pop Up via id.", () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <LoginDisplay />
      </Provider>
    );
    const linkElement = screen.getByTestId("change-password");
    expect(linkElement).toBeInTheDocument();
  });

  test("should be able to find Logout Pop Up via id.", () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <LoginDisplay />
      </Provider>
    );
    const linkElement = screen.getByTestId("logout-div");
    expect(linkElement).toBeInTheDocument();
  });

  test("dropdown works", async () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <LoginDisplay />
      </Provider>
    );
    const linkElement = screen.getByTestId("dropdown-toggle");
    expect(linkElement).toBeInTheDocument();
  });

  test("dropdown works and can see the change password", async () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <LoginDisplay />
      </Provider>
    );
    const linkElement = screen.getByTestId("dropdown-toggle");
    fireEvent.click(linkElement);
    await waitFor(() => {
      const buttonElement = screen.getByTestId("change-password-button");
      expect(buttonElement).toBeInTheDocument();
    });
  });

  test("dropdown works and can see the Logout", async () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <LoginDisplay />
      </Provider>
    );
    const linkElement = screen.getByTestId("dropdown-toggle");
    fireEvent.click(linkElement);
    await waitFor(() => {
      const buttonElement = screen.getByTestId("logout-button");
      expect(buttonElement).toBeInTheDocument();
    });
  });

  test("dropdown works and can see the Change password pop up", async () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <LoginDisplay />
      </Provider>
    );
    const linkElement = screen.getByTestId("dropdown-toggle");
    fireEvent.click(linkElement);
    await waitFor(() => {
      const buttonElement = screen.getByTestId("change-password-button");
    });
    const changePasswordButton = screen.getByTestId("change-password-button");
    fireEvent.click(changePasswordButton);
    await waitFor(() => {
      const buttonElement = screen.getByTestId("change-password");
      expect(buttonElement).toBeInTheDocument();
    });
  });

  test("dropdown works and can see the Logout pop up", async () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <LoginDisplay />
      </Provider>
    );
    const linkElement = screen.getByTestId("dropdown-toggle");
    fireEvent.click(linkElement);
    await waitFor(() => {
      const buttonElement = screen.getByTestId("logout-button");
    });
    const logoutButton = screen.getByTestId("logout-button");
    fireEvent.click(logoutButton);
    await waitFor(() => {
      const buttonElement = screen.getByTestId("logout-div");
      expect(buttonElement).toBeInTheDocument();
    });
  });

  test("dropdown works and can see the Change password Text", async () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <LoginDisplay />
      </Provider>
    );
    const linkElement = screen.getByTestId("dropdown-toggle");

    fireEvent.click(linkElement);
    await waitFor(() => {
      const buttonElement = screen.getByTestId("change-password-button");
    });
    const changePasswordButton = screen.getByText("Change Password");
    fireEvent.click(changePasswordButton);
    await waitFor(() => {
      const buttonElement = screen.getByTestId("change-password");
      expect(buttonElement).toBeInTheDocument();
    });
  });

  test("dropdown works and can see the Logout Text", async () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <LoginDisplay />
      </Provider>
    );
    const linkElement = screen.getByTestId("dropdown-toggle");

    fireEvent.click(linkElement);
    await waitFor(() => {
      const buttonElement = screen.getByTestId("logout-button");
    });
    const logoutButton = screen.getByTestId("logout-button");
    fireEvent.click(logoutButton);
    await waitFor(() => {
      const buttonElement = screen.getByText("Logout");
      expect(buttonElement).toBeInTheDocument();
    });
  });
});
