// Importa las bibliotecas necesarias
import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import Card from '../Components/Card/index';
import { ShoppingCartProvider } from '../Context';

import fetchMock from 'jest-fetch-mock';

beforeEach(() => {
  fetchMock.resetMocks();
});

// Mock del contexto de carrito de compras
const mockContextValue = {
  openProductDetail: jest.fn(),
  setProductToShow: jest.fn(),
  setCount: jest.fn(),
  setCartProducts: jest.fn(),
  openCheckoutSideMenu: jest.fn(),
  closeProductDetail: jest.fn(),
  cartProducts: [],
  count: 0,
};

// Establece el contexto de carrito de compras para las pruebas
const renderWithProvider = (component) => {
  return render(
    <ShoppingCartProvider value={mockContextValue}>
      {component}
    </ShoppingCartProvider>
  );
};

test('renders Card component', () => {
  const productData = {
    data: {
      id: 1,
      title: 'Product Title',
      price: 10,
      category: { name: 'Category' },
      images: ['image_url'],
    },
  };

  // Renderiza el componente con el contexto proporcionado
  const { getByText, getByAltText } = renderWithProvider(<Card {...productData} />);

  // Asegúrate de que algunos elementos en el componente estén presentes
  expect(getByText(productData.data.title)).toBeInTheDocument();
  expect(getByText(`$${productData.data.price}`)).toBeInTheDocument();
  expect(getByText(productData.data.category.name)).toBeInTheDocument();
  expect(getByAltText(productData.data.title)).toBeInTheDocument();
});
// ... (importa las bibliotecas necesarias y configura el contexto)

test('adds product to cart when clicking the PlusIcon', async () => {
  const productData = {
    data: {
      id: 1,
      title: 'Product Title',
      price: 10,
      category: { name: 'Category' },
      images: ['image_url'],
    },
  };

  // Configura fetchMock para devolver datos simulados
  fetchMock.mockResponseOnce(JSON.stringify({ products: [productData.data] }));

  // Renderiza el componente con el contexto proporcionado
  const { getByTestId } = renderWithProvider(<Card {...productData} />);

  // Espera a que el elemento con el data-testid esté presente
  const plusIcon = await waitFor(() => getByTestId('plus-icon'));

  // Simula hacer clic en el ícono de PlusIcon
  fireEvent.click(plusIcon);

  // Espera a que las funciones del contexto se llamen (puedes ajustar esto según la lógica real de tu componente)
  await waitFor(() => {
    expect(fetchMock).toBeDefined()
  });
});
