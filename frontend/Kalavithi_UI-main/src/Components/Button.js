import React from 'react';
import "./Button.css";

function Button({ handleClick, btnText }) {
    return (

        <button className="navbar-button" onClick={handleClick} >{btnText} </button>
    )
}

export default Button;

