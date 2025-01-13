import { render, screen } from '@testing-library/react';
import OrdersCard from '../Components/OrdersCard/index';
import fetchMock from 'jest-fetch-mock';

beforeEach(() => {
  fetchMock.resetMocks();
});

describe('OrdersCard', () => {
  test('renders with default props', () => {
    render(<OrdersCard />);
    expect(screen.getByText('articles')).toBeInTheDocument();
  });

  test('renders with provided props', () => {
    const totalPrice = 50;
    const totalProducts = 3;

    render(<OrdersCard totalPrice={totalPrice} totalProducts={totalProducts} />);
    expect(screen.getByText(`$${totalPrice}`)).toBeInTheDocument();
    expect(screen.getByText(`${totalProducts} articles`)).toBeInTheDocument();
  });
});
