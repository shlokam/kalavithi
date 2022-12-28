import { render, screen } from '@testing-library/react';
import { Provider } from "react-redux";
import NavBar from './NavBar';
import { createStore } from "redux";
import rootReducers from "../reducers";
import React from 'react';

const store = createStore(rootReducers);


describe('NavBar', () => {

    test('Navbar contains logo', () => {
      const {container} = render(<Provider store={store}><NavBar/></Provider>);

      const logo = screen.getByAltText('kala');
      expect(logo).toBeInTheDocument();

    });

    test('Navbar contains searchbox', () => {
        const {container} = render(<Provider store={store}><NavBar/></Provider>);
  
        const SearchBox = screen.getByPlaceholderText('Type to Search');
        expect(SearchBox).toBeInTheDocument();
  
      });

      
      

  });
