import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'prendaGroup'
})
export class PrendaGroupPipe implements PipeTransform {
  transform(value: any[], groupSize: number = 4): any[][] {
    if (!value || !groupSize) {
      return [];
    }
    const groups: any[][] = [];
    for (let i = 0; i < value.length; i += groupSize) {
      groups.push(value.slice(i, i + groupSize));
    }
    return groups;
  }
}
