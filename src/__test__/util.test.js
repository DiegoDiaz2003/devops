import { totalPrice } from "../utils";

describe('totalPrice', () => {
    it('should return 0 when no product is passed', () =>{
        expect(totalPrice([])).toBe(0);
    });
    it('should return the total price of a single product', () =>{
        const products = [{ price: 100 }];
        expect(totalPrice(products)).toBe(100);
    });
    
    it('should return the total price of multiple products', () =>{
        const products = [{ price: 100 },{ price: 200 }];
        expect(totalPrice(products)).toBe(300);
    });


})