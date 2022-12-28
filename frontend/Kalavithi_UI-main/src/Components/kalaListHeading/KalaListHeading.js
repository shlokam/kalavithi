import React from "react";

const KalaListHeading = () => {
  return (
    <div >
      <img 
        className="logo-design"
        src="Kalavithi_Logo_Final_Design.png"
        alt="kala_design"
        height="50px"
        width="50px"
      ></img>
      
      <img className="logo"
        src="Kalavithi_Logo_Final.png"
        alt="kala"
        height="50px"
        width="220px"
        data-testid='kala'
      ></img>
    </div>
  );
};

export default KalaListHeading;
