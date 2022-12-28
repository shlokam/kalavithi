import { render, screen } from '@testing-library/react';
import SearchBox from './Components/SearchBox';
describe('SearchBox ', () => {
    test('Search box is diplay on screen', () => {
    render(<SearchBox />);
    const input = screen.getByPlaceholderText('Type to Search');
    expect(input).toBeInTheDocument();

    });
  });