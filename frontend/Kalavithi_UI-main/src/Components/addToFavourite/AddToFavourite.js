import React, { Component } from "react";
import "./AddToFavourite.css";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import actions from "../../actions";
import axios from "axios";
import { AiOutlineStar, AiFillStar } from "react-icons/ai";

export default function AddToFavourite(props) {
  const [isActiveStar, setIsActiveStar] = useState(false);

  const favouriteImage = useSelector(
    (state) => state.HandleFavouriteImage.handleFavouriteImage
  );
  const [isActive, setActive] = useState(props.favouritestatus); //false
  const [count, setCount] = useState(0);
  const dispatch = useDispatch();
  const [favouriteList, setFavouriteList] = useState([]);
  const [userid, setUserId] = useState(
    Number.parseInt(localStorage.getItem("id"))
  );
  async function getFavouriteImageRequest() {
    try {
      setUserId(Number.parseInt(localStorage.getItem("id")));
      await axios
       .get(
         "https://kalavithi-service-team-01-test.herokuapp.com/api/images/favourite-image/id/" +
           Number.parseInt(localStorage.getItem("id"))
        )
   
        .then((response) => {
          for (let i = 0; i < response.data.length; i++) {
            if (props.kaid === response.data[i].imageid) {
              setActive(response.data[i].favouritestatus);
            }
          }
          return response.data;
        });
    } catch (err) {}
  }
  React.useEffect(() => {
    getFavouriteImageRequest();

    window.addEventListener("storage", () => {
      if (localStorage.getItem("user") === "true") {
        setUserId(Number.parseInt(localStorage.getItem("id")));
        setFavouriteList(getFavouriteImageRequest());
      } else {
        setActive(false);
      }
    });
  }, []);
  const handleToggle = async (e) => {
    dispatch(actions.showModal());
    if (localStorage.getItem("user") === "true") {
      setActive(!isActive);
      const headers = {
        "content-type": "application/json",
        Authorization: "Basic " + localStorage.getItem("authenticationToken"),
      };
      try {
        const data = await axios
          .put(

           "https://kalavithi-service-team-01-test.herokuapp.com/api/images/favourite-image",
        
            {
              userid: localStorage.getItem("id"),
              imageid: props.kaid,
              favouritestatus: isActive ? false : true,
            },
            { headers }
          )
          .then((response) => {});
      } catch (error) {}
    }
  };

  return (
    <div>
      <div className="favourite-icons" data-testid="favourite-div">
        <div className="cursor-pointer select-none" title="Add to Favorites">
          <div
            className={isActive ? AiFillStar : AiOutlineStar}
            data-testid="active-status"
          ></div>
          {isActive ? (
            <AiFillStar
              style={{ "font-size": "1.5em", marginRight: "20px" }}
              onClick={handleToggle}
            />
          ) : (
            <AiOutlineStar
              style={{ "font-size": "1.5em", marginRight: "20px" }}
              onClick={handleToggle}
            />
          )}
        </div>
      </div>
    </div>
  );
}
