import React, { useState } from 'react';
import { Nav, Navbar } from "react-bootstrap";
import { Link } from "react-router-dom";
import AddFavourite from './Components/AddFavourites';
import KalaList from './Components/KalaList';
import KalaListHeading from './Components/kalaListHeading/KalaListHeading';
import SearchBox from './Components/SearchBox';



function Header() {
  const [kalas, setKalas] = useState(
    []
  );
  return (
    <Navbar bg="black" variant="dark">
      <Navbar.Brand href="#home"></Navbar.Brand>
      <Nav className=" mr-auto nav">
        <div className='container-fluid kala-app'>
          <div className='row d-flex align-items-center mt-4 mb-4'>
            <KalaListHeading heading='Kalavithi' />
            {<SearchBox />}
          </div>
          <div className='d-flex flex-wrap'>
            <KalaList kalas={kalas} favouriteComponent={AddFavourite} />
          </div>
        </div>
        <Link to="/login">Login</Link>
      </Nav>
    </Navbar>
  );
}

export default Header;
