import {compareDate} from '../date'

describe('compareDate', () => {
    it('should return true if the second date is greater than the first date', () =>{
       const date1 = new Date(2020, 1, 1);
       const date2 = new Date(2020, 1, 2);
       expect(compareDate(date1, date2)).toBe(true);
    });



})