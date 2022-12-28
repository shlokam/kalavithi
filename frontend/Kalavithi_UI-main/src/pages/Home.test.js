import {
  render,
  within,
  fireEvent,
  screen,
  waitFor,
} from "@testing-library/react";

import { createStore } from "redux";
import rootReducers from "../reducers";

import { Provider } from "react-redux";
import Home from "./Home";

import axios from "axios";


describe("App", () => {
 
  test("should hit image api", async () => {
const axios = require('axios');
jest.mock('axios')
 
const imagedata = [
  {
    "id": 1,
    "name": "3.jpeg",
    "url": "https://kalavithi-service-team-01-dev.herokuapp.com/api/images/3.jpeg"
}
]
 
axios.get.mockResolvedValue(imagedata)
 

await axios.get("https://kalavithi-service-team-01-dev.herokuapp.com/api/images")
 
expect(axios.get).toHaveBeenCalledTimes(1)

 
  })
  
  test("should get response when api is hit", async () => {
    const axios = require('axios');
    jest.mock('axios')
   
    const imagedata = [
      {
        "id": 1,
        "name": "3.jpeg",
        "url": "https://kalavithi-service-team-01-dev.herokuapp.com/api/images/3.jpeg"
    }
    ]
   
    axios.get.mockResolvedValue(imagedata)
   
    const data = await axios.get("https://kalavithi-service-team-01-dev.herokuapp.com/api/images")
   
    expect(axios.get).toHaveBeenCalledTimes(1)
    expect(data).toEqual(imagedata);
   
      })
});




describe("Home", () => {
  test("should be able to find the Dialog div by id", () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <Home />
      </Provider>
    );
    expect(within(document.body).getByTestId("navbar")).toBeInTheDocument();
  });

  test("should be able to find the button div by id", () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <Home />
      </Provider>
    );
    expect(within(document.body).getByTestId("button-div")).toBeInTheDocument();
  });

  test("should be able to find the popup modal div by id", () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <Home />
      </Provider>
    );
    expect(
      within(document.body).getByTestId("pop-up-modal-div")
    ).toBeInTheDocument();
  });

  test("should be able to find the box by id", () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <Home />
      </Provider>
    );
    expect(within(document.body).getByTestId("box-id")).toBeInTheDocument();
  });

  test("should be able to find the image list by id", () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <Home />
      </Provider>
    );
    expect(
      within(document.body).getByTestId("image-list-id")
    ).toBeInTheDocument();
  });

  test("should be able to find the image", async () => {
    render(
      <Provider store={createStore(rootReducers)}>
        <Home />
      </Provider>
    );
    expect(within(document.body).getByAltText("kala")).toBeInTheDocument();
  });

  it("response time for this API call should be less than three seconds", () => {
    const startTime = Date.now();
    axios.get("https://kalavithi-service-team-01-dev.herokuapp.com/api/images");
    const endTime = Date.now();

    const totalTime = (endTime - startTime) / 1000;

    const maxTime = 3;
    expect(totalTime).toBeLessThan(maxTime);
  });

 
});

it("response time for this API call should be less than three seconds", () => {

  const startTime = Date.now();
  axios.get("https://kalavithi-service-team-01-dev.herokuapp.com/api/images")
  const endTime = Date.now();

  const totalTime = ((endTime) - (startTime)) / 1000;

  const maxTime = 3;
  expect(totalTime).toBeLessThan(maxTime);
});


