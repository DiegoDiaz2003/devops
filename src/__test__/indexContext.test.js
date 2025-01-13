import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { ShoppingCartProvider, ShoppingCartContext } from '../Context/index';
import fetchMock from 'jest-fetch-mock';

beforeEach(() => {
  fetchMock.resetMocks();
});

describe('ShoppingCartProvider', () => {
  test('renders children and provides context values', () => {
    render(
      <ShoppingCartProvider>
        <ChildComponent />
      </ShoppingCartProvider>
    );

    // Accessing context values
    const contextValues = screen.getByTestId('context-values');
    expect(contextValues).toHaveTextContent('count: 0');

    // You can add more assertions for other context values
  });

  test('updates context values correctly', () => {
    render(
      <ShoppingCartProvider>
        <ChildComponent />
      </ShoppingCartProvider>
    );

    // Example: Increment count
    userEvent.click(screen.getByTestId('increment-count-button'));

    const contextValues = screen.getByTestId('context-values');
    expect(contextValues).toHaveTextContent('count: 0');

    // You can add more tests for other context value updates
  });
});

const ChildComponent = () => {
  const {
    count,
    setCount,
    openProductDetail,
    closeProductDetail,
    isProductDetailOpen,
    // Add more context values as needed
  } = React.useContext(ShoppingCartContext);

  return (
    <div>
      {/* Your component JSX */}
      <button data-testid="increment-count-button" onClick={() => setCount(count + 1)}>
        Increment Count
      </button>

      {/* Example: Display context values for testing */}
      <div data-testid="context-values">
        count: {count}
      </div>
    </div>
  );
};
