import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import "./LikeAnImage.css";

import axios from "axios";
import actions from "../../actions";
export default function LikeAnImage(props) {
  const likeImage = useSelector(
    (state) => state.HandleLikeImage.handleLikeImage
  );
  const [isActive, setActive] = useState(props.status); //false
  const [count, setCount] = useState(0);
  const dispatch = useDispatch();
  const [likeList, setLikeList] = useState([]);
  const [userid, setUserId] = useState(
    Number.parseInt(localStorage.getItem("id"))
  );

  const headers = {
    "content-type": "application/json",
    Authorization: "Basic " + localStorage.getItem("authenticationToken"),
  };

  async function getLikedImageRequest() {
    try {
      setUserId(Number.parseInt(localStorage.getItem("id")));
      await axios
        .get(
          "https://kalavithi-service-team-01-test.herokuapp.com/api/images/id/" +
           Number.parseInt(localStorage.getItem("id"))
            //"http://localhost:8081/api/images/id/"+ Number.parseInt(localStorage.getItem("id"))
        )
       
        .then((response) => {
          for (let i = 0; i < response.data.length; i++) {
            if (props.kaid === response.data[i].imageid) {
              setActive(response.data[i].status);
            }
          }




          return response.data;
        });
    } catch (err) {}
  }
  async function getLikedImageCount() {
    try {
      setUserId(Number.parseInt(localStorage.getItem("id")));
      await axios

//         // .get(
//         //   "https://kalavithi-service-team-01-dev.herokuapp.com/api/images/count"
//         // )
//         .get("http://localhost:8081/api/images/count")

        .get(
          "https://kalavithi-service-team-01-test.herokuapp.com/api/images/count"
        )
      //  .get("http://localhost:8081/api/images/count")

        .then((response) => {
          for (let i = 0; i < response.data.length; i++) {
            if (props.kaid === response.data[i][1]) {
              setCount(response.data[i][0]);

            }
          }
          return response.data;
        });
    } catch (err) {}
  }
  React.useEffect(() => {
    getLikedImageRequest();
    getLikedImageCount();
    window.addEventListener("storage", () => {
      if (localStorage.getItem("user") === "true") {
        setUserId(Number.parseInt(localStorage.getItem("id")));
        setLikeList(getLikedImageRequest());
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

//         // const data = await axios
//         //   .put(
//         //     "https://kalavithi-service-team-01-dev.herokuapp.com/api/images",
//              const data =await axios.put("http://localhost:8081/api/images",

        const data = await axios
          .put(
           "https://kalavithi-service-team-01-test.herokuapp.com/api/images",
           //  const data =await axios.put("http://localhost:8081/api/images",

            {
              userid: localStorage.getItem("id"),
              imageid: props.kaid,
              status: isActive ? false : true,
            },{headers}
          )
          .then((response) => {});
      } catch (error) {}
      if (isActive) setCount(count - 1);
      else setCount(count + 1);
    }
  };
  return (
    <div data-testid="like-image-div">
      <div className="heart-btn" data-testid="heart-div" title="Click to Like">
        <button
          onClick={handleToggle}
          className={isActive ? "content heart-active " : "content"}
        >
          <span
            style={{ marginLeft: "50%", marginTop: "10%" }}
            className={isActive ? "heart heart-active" : "heart"}
          ></span>
          <span
            style={{ color: "white", marginLeft: "100px", marginTop: "23%" }}
          >
            &nbsp;&nbsp;{count > 0 ? count : ""}
          </span>
        </button>
      </div>
    </div>
  );
}
