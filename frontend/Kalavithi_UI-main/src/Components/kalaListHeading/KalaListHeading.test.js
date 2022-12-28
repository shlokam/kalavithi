import { render, screen } from '@testing-library/react';
import KalaListHeading from './KalaListHeading';

describe('KalaListHeading', () => {
    test('KalaListHeading must have src = "Kalavithi_Logo_Final.png" and alt = "kala"', () => {
      const {container} = render(<KalaListHeading/>);

      window.matchMedia = jest.fn().mockImplementation(query => ({
        matches: query !== '(min-width: 900px)',
        media: '',
        onchange: null,
        addListener: jest.fn(),
        removeListener: jest.fn()
      }));

      const logo = screen.getByAltText("kala");
      expect(logo).toBeInTheDocument();

    });

  
 
    test('KalaListHeading must have src ="Kalavithi_Logo_Final_Design.png" and alt = "kala_design"', () => {
      const {container} = render(<KalaListHeading/>);
  
      window.matchMedia = jest.fn().mockImplementation(query => ({
        matches: query !== '(max-width: 900px)',
        media: '',
        onchange: null,
        addListener: jest.fn(),
        removeListener: jest.fn()
      }));

      const logo = screen.getByAltText('kala_design');
      expect(logo).toBeInTheDocument();

    });
  });