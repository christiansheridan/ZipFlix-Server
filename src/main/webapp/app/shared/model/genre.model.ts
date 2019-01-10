export interface IGenre {
    id?: string;
    genre?: string;
}

export class Genre implements IGenre {
    constructor(public id?: string, public genre?: string) {}
}
