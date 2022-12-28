import React from "react";
import KalaListHeading from "../Components/kalaListHeading/KalaListHeading";

function NoPage() {

  return (
    <div>
      <style>{'body { background-color: black; }'}</style>
      <div style={{ paddingLeft: "5%", paddingTop: "1%" }}>
        <KalaListHeading></KalaListHeading>
      </div>


      <img
        src="nopage.png"
        alt="error"
        height="30%"
        width="30%"
        style={{
          margin: "0 auto",
          marginTop: "5%",
          display: "block"
        }}

      ></img>

      <h1 style={{ color: 'white', textAlign: "center", font: "Monaco" }}>404, Page Not Found</h1>


    </div >
  );

}

export default NoPage;
